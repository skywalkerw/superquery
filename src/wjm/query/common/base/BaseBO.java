package wjm.query.common.base;

import java.io.Serializable;

public class BaseBO implements Serializable {
	private static final long serialVersionUID = -1146115497069368425L;
	private boolean mgred;

	public boolean isMgred() {
		return mgred;
	}

	public void setMgred(boolean mgred) {
		this.mgred = mgred;
	}
}
