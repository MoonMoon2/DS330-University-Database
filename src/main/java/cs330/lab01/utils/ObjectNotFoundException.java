package cs330.lab01.utils;

public class ObjectNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException() {
		super();
	}
	
	public ObjectNotFoundException(String response) {
		super(response);
	}

}
