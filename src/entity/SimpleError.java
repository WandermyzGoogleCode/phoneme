package entity;

public class SimpleError implements MyError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 251707021807645918L;
	private String info;

	public SimpleError(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return info;
	}
}
