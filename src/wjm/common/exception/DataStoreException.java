package wjm.common.exception;



public class DataStoreException extends Exception {

	public DataStoreException(String message, Exception e) {
		super(message,e);
	}

	public DataStoreException(String message) {
		super(message);
	}


}
