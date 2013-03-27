package wjm.query.action;

import javax.servlet.http.HttpServletRequest;

import wjm.common.exception.SuperQueryException;

public interface IActionFactory {
	public String mapping(HttpServletRequest request)  throws SuperQueryException;
}
