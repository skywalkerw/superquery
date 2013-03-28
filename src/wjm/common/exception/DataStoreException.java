package wjm.common.exception;



public class DataStoreException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1107164479774099051L;

	public DataStoreException(String message, Exception e) {
		super(message,e);
	}

	public DataStoreException(String message) {
		super(message);
	}


}
