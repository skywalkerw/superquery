package wjm.common.exception;



public class SuperQueryException extends Exception {
	
	private int errcode ;
	public SuperQueryException(String message, Exception e) {
		super(message,e);
	}
	public SuperQueryException(int errcode,String message, Exception e) {
		super(message,e);
		this.errcode = errcode;
	}
	public SuperQueryException(int errcode,String message) {
		super(message);
		this.errcode = errcode;
	}

	public SuperQueryException(String message) {
		super(message);
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}


}
