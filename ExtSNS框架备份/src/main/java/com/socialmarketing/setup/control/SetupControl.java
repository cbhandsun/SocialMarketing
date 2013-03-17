package com.socialmarketing.setup.control;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.socialmarketing.commons.control.GenericControl;
import com.socialmarketing.commons.text.StringUtils;
import com.socialmarketing.config.ApplicationPrefs;
import com.socialmarketing.setup.utils.SetupUtils;

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/setup")
public class SetupControl extends GenericControl{
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public String setupRequest() {
		return "welcome";
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/ConfigureLibrary")
	public String executeCommandConfigureLibrary(HttpServletRequest req) {
		ServletContext context = req.getSession().getServletContext();
		if (SetupUtils.isConfigured(getApplicationPrefs(context))) {
			return "AlreadySetupOK";
		}
		// populate from request
		String fileLibrary = req.getParameter("directory");
		// populate from prefs
		if (fileLibrary == null) {
			fileLibrary = getPref(context, ApplicationPrefs.FILE_LIBRARY_PATH);
		}
		// create a default depending on os
		if (fileLibrary == null || "".equals(fileLibrary.trim())) {
			String contextPath = req.getContextPath();
			if (!StringUtils.hasText(contextPath)) {
				contextPath = "connect";
			}
			contextPath = StringUtils.replace(contextPath, "/", "");
			String os = System.getProperty("os.name");
			if (os.startsWith("Windows")) {
				// Windows
				fileLibrary = "c:\\Concursive\\" + contextPath
						+ "\\fileLibrary";
			} else if (os.startsWith("Mac")) {
				// Mac OSX
				fileLibrary = "/Library/Application Support/Concursive/"
						+ contextPath + "/fileLibrary";
			} else {
				File testDirectory = new File("/opt");
				if (testDirectory.exists()) {
					// Linux, Solaris, SunOS, OS/2, HP-UX, AIX, FreeBSD, etc
					fileLibrary = "/opt/concursive/" + contextPath
							+ "/fileLibrary";
				} else {
					// Linux, Solaris, SunOS, OS/2, HP-UX, AIX, FreeBSD, etc
					fileLibrary = "/var/lib/concursive/" + contextPath
							+ "/fileLibrary";
				}
			}
		}
		req.setAttribute("directory", fileLibrary);
		return "SetupConfigureLibraryOK";
	}



}
