package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import algorithm.Checker;

import logiccenter.LogicCenter;

import entity.BoolInfo;

public class ExportFileResult extends OneTimeVirtualResult {
	private String fileName;
	static private Checker fileNameChecker;//TODO ��ǰ��û��ʵ����صļ����

	public ExportFileResult(String fileName, LogicCenter center) {
		super(center);
		this.fileName = fileName;
		
		//TODO ���ļ������м�飺if (!fileNameChecker.check(fileName))...
		
		thread.start();
	}

	@Override
	protected BoolInfo getResult(){
		center.getDataCenter().exportFile(fileName);
		//TODO ��ǰû�д���dataCenter�Ĵ���
		return new BoolInfo();
	}

}
