package entity.infoField;

/**
 * ͷ��
 * @author Administrator
 *
 */
public class EmptyImg extends EmptyInfoField implements MultimediaInfoField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2847455469662657743L;

	@Override
	public String getName() {
		return Messages.getString("Image"); //$NON-NLS-1$
	}

	@Override
	public int getMaxLength() {
		return Img.maxLength;
	}
}
