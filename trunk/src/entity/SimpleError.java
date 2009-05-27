package entity;

public class SimpleError implements MyError {
	private String info;

	public SimpleError(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return info;
	}
}
