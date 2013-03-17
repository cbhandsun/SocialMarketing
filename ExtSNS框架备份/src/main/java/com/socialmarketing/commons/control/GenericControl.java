package com.socialmarketing.commons.control;

import javax.servlet.ServletContext;

import com.socialmarketing.config.ApplicationPrefs;

public class GenericControl  {
	/**
	 * Gets the applicationPrefs attribute of the GenericAction object
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return The applicationPrefs value
	 */
	protected static ApplicationPrefs getApplicationPrefs(ServletContext context) {
		return ApplicationPrefs
				.getApplicationPrefs(context);
	}
	  /**
	   * Gets the pref attribute of the GenericAction object
	   *
	   * @param context Description of the Parameter
	   * @param name    Description of the Parameter
	   * @return The pref value
	   */
	  protected String getPref(ServletContext context, String name) {
	    return getApplicationPrefs(context).get(name);
	  }
}
