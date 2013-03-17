/*
 * ConcourseConnect
 * Copyright 2009 Concursive Corporation
 * http://www.concursive.com
 *
 * This file is part of ConcourseConnect, an open source social business
 * software and community platform.
 *
 * Concursive ConcourseConnect is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3 of the License.
 *
 * Under the terms of the GNU Affero General Public License you must release the
 * complete source code for any application that uses any part of ConcourseConnect
 * (system header files and libraries used by the operating system are excluded).
 * These terms must be included in any work that has ConcourseConnect components.
 * If you are developing and distributing open source applications under the
 * GNU Affero General Public License, then you are free to use ConcourseConnect
 * under the GNU Affero General Public License.
 *
 * If you are deploying a web site in which users interact with any portion of
 * ConcourseConnect over a network, the complete source code changes must be made
 * available.  For example, include a link to the source archive directly from
 * your web site.
 *
 * For OEMs, ISVs, SIs and VARs who distribute ConcourseConnect with their
 * products, and do not license and distribute their source code under the GNU
 * Affero General Public License, Concursive provides a flexible commercial
 * license.
 *
 * To anyone in doubt, we recommend the commercial license. Our commercial license
 * is competitively priced and will eliminate any confusion about how
 * ConcourseConnect can be used and distributed.
 *
 * ConcourseConnect is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with ConcourseConnect.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Attribution Notice: ConcourseConnect is an Original Work of software created
 * by Concursive Corporation
 */
package com.socialmarketing.setup.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.xml.registry.infomodel.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.socialmarketing.config.ApplicationPrefs;

/**
 * Utilities for setting up and validating a ConcourseConnect installation
 *
 * @author matt rajkowski
 * @version $Id: SetupUtils.java 370 2009-11-09 15:50:40Z matt $
 * @created Nov 29, 2005
 */
public class SetupUtils {

  private static Log LOG = LogFactory.getLog(SetupUtils.class);
  public final static String fs = System.getProperty("file.separator");

  /**
   * This method is used by the ant task to install any default data
   *
   * @param args database connection settings: driver, url, user, password
   */
  public static void main(String[] args) {
    // Connection values
   
    System.exit(0);
  }

  /**
   * Determines if the application is fully configured
   *
   * @param prefs
   * @return
   */
  public static boolean isConfigured(ApplicationPrefs prefs) {
    return prefs.isConfigured();
  }

  /**
   * Determines if the services component of installation is fully configured
   *
   * @param prefs
   * @return
   */
  public static boolean isServicesConfigured(ApplicationPrefs prefs) {
    return prefs.has(ApplicationPrefs.CONCURSIVE_SERVICES_ID);
  }

  /**
   * Determines if the database schema has been created
   *
   * @param db
   * @return
   */
  public static boolean isDatabaseInstalled(Connection db) {
    int count = -1;
    try {
      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT count(*) AS recordcount " +
          "FROM database_version ");
      rs.next();
      count = rs.getInt("recordcount");
      rs.close();
      st.close();
    } catch (Exception e) {
    }
    return count > 0;
  }

  /**
   * Determines if there is an administrative user configured in the database
   *
   * @param db
   * @return
   */
  public static boolean isAdminInstalled(Connection db) {
    int count = -1;
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS recordcount " +
          "FROM users " +
          "WHERE access_admin = ? ");
      pst.setBoolean(1, true);
      ResultSet rs = pst.executeQuery();
      rs.next();
      count = rs.getInt("recordcount");
      rs.close();
      pst.close();
    } catch (Exception e) {
    }
    return count > 0;
  }

  /**
   * Determines if a default project has been installed
   *
   * @param db
   * @return
   */
  public static boolean isDefaultProjectInstalled(Connection db) {
    int count = -1;
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS recordcount " +
          "FROM projects " +
          "WHERE system_default = ? ");
      pst.setBoolean(1, true);
      ResultSet rs = pst.executeQuery();
      rs.next();
      count = rs.getInt("recordcount");
      rs.close();
      pst.close();
    } catch (Exception e) {
    }
    return count > 0;
  }

  /**
   * Inserts the default database data
   *
   * @param db
   * @param context
   * @param setupPath
   * @throws Exception
   */
  public static void insertDefaultData(Connection db, ServletContext context, String setupPath) throws Exception {
    // Prepare the BSH interpreter
  
  }

  public static void insertDefaultAdmin(Connection db, User thisUser, String ip, ApplicationPrefs prefs) throws Exception {
 
  }


}
