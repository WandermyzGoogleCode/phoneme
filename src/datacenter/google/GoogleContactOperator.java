package datacenter.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.awt.SunToolkit.InfiniteLoop;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Category;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.Birthday;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.Nickname;
import com.google.gdata.data.contacts.Occupation;
import com.google.gdata.data.contacts.Relation;
import com.google.gdata.data.contacts.UserDefinedField;
import com.google.gdata.data.contacts.Website;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.OrgName;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.PostalAddress;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import entity.GoogleSynSource;
import entity.UserInfo;
import entity.infoField.Address;
import entity.infoField.Cellphone;
import entity.infoField.Company;
import entity.infoField.EmailAddr;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
import entity.infoField.NickName;
import entity.infoField.Phone;
import entity.infoField.Position;
import entity.infoField.Web;

public class GoogleContactOperator {
	public static final int DefaultMaxResults = 1000;
	ContactsService myService;
	GoogleSynSource source;
	Map<String, ContactEntry> entryMap;
	List<ContactEntry> myEntries = null;
	
	public GoogleContactOperator(GoogleSynSource source) throws AuthenticationException {
		myService = new ContactsService("PhoneMe");
		this.source = source;
		myService.setUserCredentials(source.getUsername(), source.getPwd());
	}
	
	List<ContactEntry> getContactEntries() throws IOException, ServiceException{
		if (myEntries == null){
			Query query = new Query(new URL(source.getPostUrl()));
			query.setMaxResults(DefaultMaxResults);
			ContactFeed resultFeed = myService.query(query, ContactFeed.class);
			myEntries = resultFeed.getEntries();
			entryMap = new HashMap<String, ContactEntry>();
			for(ContactEntry entry: myEntries)
				entryMap.put(entry.getId(), entry);
		}
		return myEntries;
	}
	
	public List<UserInfo> getAllContacts()
			throws AuthenticationException, MalformedURLException,
			ServiceException, IOException {
		List<ContactEntry> entries = getContactEntries();
		List<UserInfo> res = new ArrayList<UserInfo>();
		for (int i = 0; i < entries.size(); i++) {
			ContactEntry entry = entries.get(i);
			res.add(entryToUserInfo(entry));
		}
		
		return res;
	}

	public enum ExtendInfoName{
		Company, Image, QQNumber, Remarks;
	}
	
	public enum ExtensionName{
		GoogleID, Operation;
	}
	
	public enum OperationValue{
		Update, Remove, Add;
	}
	
	private static UserInfo entryToUserInfo(ContactEntry entry) {
		//TODO 以后可以考虑更好的转换
		UserInfo res = new UserInfo();
		InfoFieldFactory factory = InfoFieldFactory.getFactory();

		//设置goolge的ID，以便一会更新的时候可以辨认
		entity.infoField.Extension myExtension = (entity.infoField.Extension)res.getInfoField(InfoFieldName.Extension);
		myExtension.setValue(ExtensionName.GoogleID.name(), entry.getId());
		
		//NAME	注意，还有title
//		Name name = entry.getName();
//		if (name != null)
//			res.setInfoField(factory.makeInfoField(InfoFieldName.Name, name.getFullName().getValue()));
		String name = entry.getTitle().getPlainText();
		res.setInfoField(factory.makeInfoField(InfoFieldName.Name, name));
		
		//ADDRESS
		List<PostalAddress> addressList = entry.getPostalAddresses();
		if (addressList.size() > 0)
			res.setInfoField(factory.makeInfoField(InfoFieldName.Address, entry.getPostalAddresses().get(0).getValue()));
		
		//BIRTHDAY
		Birthday birthday = entry.getBirthday();
		if (birthday != null)
			res.setInfoField(factory.makeInfoField(InfoFieldName.Birthday, birthday.getValue()));
		
		//TODO CATEGORY <-> GROUP 暂时没时间搞了，这个比较麻烦
		
		//COMPANY
		List<Organization> orgs = entry.getOrganizations();
		if (orgs.size() > 0)
			res.setInfoField(factory.makeInfoField(InfoFieldName.Company, orgs.get(0).getLabel()));
		
		//CELLPHONE & PHONE
		List<PhoneNumber> phones = entry.getPhoneNumbers();
		for(int i=0; i<phones.size(); i++)
			if (Cellphone.check(phones.get(i).getPhoneNumber()))
				res.setInfoField(factory.makeInfoField(InfoFieldName.Cellphone, phones.get(i).getPhoneNumber()));
		for(int i=0; i<phones.size(); i++)
			if (!Cellphone.check(phones.get(i).getPhoneNumber()))
				res.setInfoField(factory.makeInfoField(InfoFieldName.Phone, phones.get(i).getPhoneNumber()));
		
		//EMAIL
		List<Email> emails = entry.getEmailAddresses();
		if (emails.size() > 0)
			res.setInfoField(factory.makeInfoField(InfoFieldName.EmailAddress, emails.get(0).getAddress()));
		
		//NICKNAME
		Nickname nickname = entry.getNickname();
		if (nickname != null)
			res.setInfoField(factory.makeInfoField(InfoFieldName.NickName, nickname.getValue()));
		
		//WEBSITE
		List<Website> websites = entry.getWebsites();
		if (websites.size() > 0)
			res.setInfoField(factory.makeInfoField(InfoFieldName.Website, websites.get(0).getHref()));
		
		//POSITION
		Occupation occupation = entry.getOccupation();
		if (occupation != null)
			res.setInfoField(factory.makeInfoField(InfoFieldName.Position, occupation.getValue()));
		
		//扩展部分
		List<UserDefinedField> exProps = entry.getUserDefinedFields();
		Map<String, String> epMap = new HashMap<String, String>();
		for(UserDefinedField ep: exProps)
			if (ep.hasValue())
				epMap.put(ep.getKey(), ep.getValue());
		for(ExtendInfoName key: ExtendInfoName.values())
			if (epMap.containsKey(key.name()))
				res.setInfoField(factory.makeInfoField(key.name(), epMap.get(key.name())));
		return res;
	}
	
	public void updateAllContacts(List<UserInfo> users) throws IOException, ServiceException{
		List<ContactEntry> entries = getContactEntries();
		for(UserInfo user: users){
			entity.infoField.Extension myExtension = (entity.infoField.Extension)user.getInfoField(InfoFieldName.Extension);
			String oprName = myExtension.getValue(ExtensionName.Operation.name());
			if (oprName.equals(OperationValue.Add.name())){
				addContactEntry(user);
				continue;
			}
			String googleID = myExtension.getValue(ExtensionName.GoogleID.name());
			ContactEntry thisEntry = entryMap.get(googleID);
			if (oprName.equals(OperationValue.Remove.name()))
				removeContactEntry(thisEntry);
			else if (oprName.equals(OperationValue.Update.name()))
				updateContactEntry(thisEntry, user);
		}
	}
	
	private void removeContactEntry(ContactEntry thisEntry) throws IOException, ServiceException {
		thisEntry.delete();
	}

	private void updateContactEntry(ContactEntry thisEntry, UserInfo user) throws IOException, ServiceException {
		if (!setContactEntry(thisEntry, user))//无改变，不用更新
			return;
		URL editUrl = new URL(thisEntry.getEditLink().getHref());
		myService.update(editUrl, thisEntry);
	}

	/**
	 * 返回true如果有字段被改动，false反之
	 * @param thisEntry
	 * @param user
	 * @return
	 */
	private static boolean setContactEntry(ContactEntry thisEntry, UserInfo user) {
		boolean res = false;
		
		//NAME	注意，还有title
		entity.infoField.Name name = (entity.infoField.Name)user.getInfoField(InfoFieldName.Name);
		if (!name.isEmpty() && !thisEntry.getTitle().getPlainText().equals(name.getStringValue())){
			//FullName fullName = new FullName(name.getStringValue(), null);
			//thisEntry.getName().setFullName(fullName);
			res = true;
			PlainTextConstruct text = new PlainTextConstruct(name.getStringValue());
			thisEntry.setTitle(text);
			Name tn = new Name();
			tn.setFullName(new FullName(name.getStringValue(), null));
			thisEntry.setName(tn);
		}
		
		//ADDRESS
		Address address = (Address)user.getInfoField(InfoFieldName.Address);
		if (!address.isEmpty()){
			List<PostalAddress> addressList = thisEntry.getPostalAddresses();
			if (addressList.size() > 0){
				if (!addressList.get(0).getValue().equals(address.getStringValue())){
					res = true;
					addressList.get(0).setValue(address.getStringValue());
				}
			}
			else{
				res = true;
				PostalAddress pAddress = new PostalAddress();
				pAddress.setValue(address.getStringValue());
				pAddress.setRel(PostalAddress.Rel.GENERAL);
				thisEntry.addPostalAddress(pAddress);
			}
		}
		
		//BIRTHDAY
		entity.infoField.Birthday birthday = (entity.infoField.Birthday)user.getInfoField(InfoFieldName.Birthday);
		if (!birthday.isEmpty() && (thisEntry.getBirthday() == null || !thisEntry.getBirthday().getValue().equals(birthday.getStringValue()))){
			res = true;
			Birthday b = new Birthday(birthday.getStringValue());
			thisEntry.setBirthday(b);
		}
		
		//TODO CATEGORY <-> GROUP 暂时没时间搞了，这个比较麻烦
		
		//COMPANY
		Company company = (Company) user.getInfoField(InfoFieldName.Company);
		if (!company.isEmpty()){
			List<Organization> orgs = thisEntry.getOrganizations();
			if (orgs.size() > 0){
				if (!orgs.get(0).getLabel().equals(company.getStringValue())){
					orgs.get(0).setLabel(company.getStringValue());
					res = true;
				}
			}
			else{
				res = true;
				thisEntry.addOrganization(new Organization(company.getStringValue(), true, Organization.Rel.WORK));
			}
		}
		
		//CELLPHONE & PHONE
		Cellphone cellphone = (Cellphone) user.getInfoField(InfoFieldName.Cellphone);
		List<PhoneNumber> newPhones = new ArrayList<PhoneNumber>();
		if (!cellphone.isEmpty()){
			PhoneNumber pN = new PhoneNumber();
			pN.setPhoneNumber(cellphone.getStringValue());
			pN.setRel(PhoneNumber.Rel.MOBILE);
			newPhones.add(pN);	
		}
		Phone phone = (Phone) user.getInfoField(InfoFieldName.Phone);
		if (!phone.isEmpty()){
			PhoneNumber pN = new PhoneNumber();
			pN.setPhoneNumber(phone.getStringValue());
			pN.setRel(PhoneNumber.Rel.OTHER);
			newPhones.add(pN);			
		}
		for(int i=0; i<newPhones.size(); i++)
			if (thisEntry.getPhoneNumbers().size() <= i){
				thisEntry.addPhoneNumber(newPhones.get(i));
				res = true;
			}
			else{
				if (!thisEntry.getPhoneNumbers().get(i).getPhoneNumber().equals(newPhones.get(i).getPhoneNumber())){
					thisEntry.getPhoneNumbers().get(i).setPhoneNumber(newPhones.get(i).getPhoneNumber());
					res = true;
				}
			}
		
		//EMAIL
		EmailAddr email = (EmailAddr) user.getInfoField(InfoFieldName.EmailAddress);
		if (!email.isEmpty()){
			List<Email> emails = thisEntry.getEmailAddresses(); 
			if (emails.size() > 0){
				if (!emails.get(0).getAddress().equals(email.getStringValue())){
					emails.get(0).setAddress(email.getStringValue());
					res = true;
				}
			}
			else{
				res = true;
				Email newEmail = new Email();
				newEmail.setAddress(email.getStringValue());
				newEmail.setRel(Email.Rel.OTHER);
				thisEntry.addEmailAddress(newEmail);
			}
		}
		
		//NICKNAME
		NickName nickname = (NickName) user.getInfoField(InfoFieldName.NickName);
		if (!nickname.isEmpty() && (thisEntry.getNickname() == null || !thisEntry.getNickname().getValue().equals(nickname.getStringValue()))){
			thisEntry.setNickname(new Nickname(nickname.getStringValue()));
		}
		
		//WEBSITE
		Web web = (Web) user.getInfoField(InfoFieldName.Website);
		if (!web.isEmpty()){
			List<Website> websites = thisEntry.getWebsites();
			if (websites.size() == 0){
				thisEntry.addWebsite(new Website());
				res = true;
			}
			if (!thisEntry.getWebsites().get(0).getHref().equals(web.getStringValue())){
				thisEntry.getWebsites().get(0).setHref(web.getStringValue());
				thisEntry.getWebsites().get(0).setRel(Website.Rel.OTHER);
				res = true;
			}
		}
		
		//POSITION
		Position position = (Position) user.getInfoField(InfoFieldName.Position);
		if (!position.isEmpty() && (thisEntry.getOccupation() == null || !thisEntry.getOccupation().getValue().equals(position.getStringValue()))){
			thisEntry.setOccupation(new Occupation(position.getStringValue()));
			res = true;
		}
		
		//扩展部分
		Map<String, Integer> posMap = new HashMap<String, Integer>();
		List<UserDefinedField> exProps = thisEntry.getUserDefinedFields();
		for(int i=0; i<exProps.size(); i++){
			UserDefinedField ep = exProps.get(i);
			if (ep.hasValue())
				posMap.put(ep.getKey(), i);
		}
		for(ExtendInfoName key: ExtendInfoName.values()){
			InfoField infoField = user.getInfoField(InfoFieldName.valueOf(key.name()));
			if (infoField.isEmpty())
				continue;
			if (posMap.containsKey(key.name())){
				int pos = posMap.get(key.name());
				if (!exProps.get(pos).getValue().equals(infoField.getStringValue())){
					exProps.get(pos).setValue(infoField.getStringValue());
					res = true;
				}
			}
			else{
				thisEntry.addUserDefinedField(new UserDefinedField(key.name(), infoField.getStringValue()));
				res = true;
			}
		}
		return res;
	}

	private void addContactEntry(UserInfo user) throws IOException, ServiceException {
		ContactEntry contact = new ContactEntry();
		setContactEntry(contact, user);
		URL postUrl = new URL(source.getPostUrl());
		myService.insert(postUrl, contact);
	}

//	static public void main(String args[]){
////		GoogleSynSource source= new GoogleSynSource("liyuqian79", "");
////		try {
////			System.out.println("Loading...");
////			GoogleContactOperator operator = new GoogleContactOperator(source);
////			List<UserInfo> res = operator.getAllContacts();
////			for(UserInfo info: res)
////				System.out.println(info.getStringValue());
////			
////			ContactEntry entry = operator.getContactEntries().get(0);
////			InfoFieldFactory factory = InfoFieldFactory.getFactory();
////			UserInfo user = new UserInfo();
////			user.setInfoField(factory.makeInfoField(InfoFieldName.Address, "testAddress"));
////			setContactEntry(entry, user);
////			user.setInfoField(factory.makeInfoField(InfoFieldName.Address, "testAddress2"));
////			setContactEntry(entry, user);
////			user.setInfoField(factory.makeInfoField(InfoFieldName.Birthday, "1989-7-9"));
////			setContactEntry(entry, user);
////			for(PostalAddress pa: entry.getPostalAddresses())
////				System.out.println(pa.getValue());
////			System.out.println(entry.getBirthday().getValue());
////			user.setInfoField(factory.makeInfoField(InfoFieldName.Cellphone, "13263255515"));
////			setContactEntry(entry, user);
////			user.setInfoField(factory.makeInfoField(InfoFieldName.Cellphone, "13263255515"));
////			setContactEntry(entry, user);
////			for(PhoneNumber pN: entry.getPhoneNumbers())
////				System.out.println(pN.getPhoneNumber());
////			user.setInfoField(factory.makeInfoField(InfoFieldName.QQNumber, "147995029"));
////			setContactEntry(entry, user);
////			for(UserDefinedField udf: entry.getUserDefinedFields())
////				System.out.println(String.format("%s: %s", udf.getKey(), udf.getValue()));
//			Birthday birthday = new Birthday("1989-7-9");
//			System.out.println(birthday.getWhen());
////		} catch (AuthenticationException e) {
////			e.printStackTrace();
////		} catch (MalformedURLException e) {
////			e.printStackTrace();
////		} catch (ServiceException e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
}
