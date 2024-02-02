package hostel.model;

public class HostelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HostelException() {  // constructor without parameter
		super("error in Hostel application"); // default error info
	}

	public HostelException(String message) { // constructor with parameter
		super(message); // user defined message can be provided
	}

}