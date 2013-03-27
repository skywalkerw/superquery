package wjm.common.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import wjm.common.util.SpringUtil;
import wjm.query.loader.DictionaryLoader;
import wjm.query.loader.QueryconfLoader;

public class InitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2407451170420010776L;
	private static Logger log = Logger.getLogger(InitServlet.class);

	@Override
	public void init(ServletConfig cfg) throws ServletException {
		log.info(" Init ...");
		SpringUtil.setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(cfg.getServletContext()));
		log.info(" ApplicationContext load completed.");
	}

}
