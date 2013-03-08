package com.socialmarketing.core;

/**
 * This class defines constants for organizing System, Object, ServletContext,
 * Request, Session, and Cookie values; it should have no dependencies
 */
public class Constants { 

	public static final int UNDEFINED = -1;
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	public final static int PAGE_SIZE = 20;
	public static final String LF = System.getProperty("line.separator");

	// FileLibrary LinkModuleId Constants
	public static final int PROJECTS_FILES = 1;
	public static final int PROJECT_REVIEW_FILES = 2009021823;
	public static final int IMAGELIBRARY_FILES = 2;
	public static final int BLOG_POST_FILES = 2004102113;
	public static final int BLOG_POST_COMMENT_FILES = 2009022513;
	public static final int DISCUSSION_FILES_TOPIC = 2005020616;
	public static final int DISCUSSION_FILES_REPLY = 20050201;
	public static final int PROJECT_BLOG_FILES = 2008080809;
	public static final int PROJECT_WIKI_FILES = 20060220;
	public static final int PROJECT_WIKI_COMMENT_FILES = 2009022514;
	public static final int PROJECT_TICKET_FILES = 2007030620;
	public static final int PROJECT_REQUIREMENT_FILES = 2008031611;
	public static final int PROJECT_IMAGE_FILES = 2008090514;
	public static final int TEMP_FILES = 2007032415;
	public static final int BADGE_FILES = 2008051215;
	public static final int BADGE_CATEGORY_FILES = 2008051315;
	public static final int AD_CATEGORY_FILES = 2008051514;
	public static final int PROJECT_CATEGORY_FILES = 2008051912;
	public static final int CLASSIFIED_CATEGORY_FILES = 2008052115;
	public static final int PROJECT_AD_FILES = 2008071715;
	public static final int PROJECT_CLASSIFIEDS_FILES = 2008071716;
	public static final int PROJECT_STYLE_FILES = 2008101612;
	public static final int PROJECT_MESSAGES_FILES = 2008122922;
	public static final int PROJECTS_CALENDAR_EVENT_FILES = 2009021619;
	public static final int SITE_LOGO_FILES = 2009030915;
	public static final int PROJECT_WEBCAST_FILES = 2010050414;

	// Report profile as inappropriate linkModuleId Constant
	public static final int PROFILE_LINK = 2010051114;

	// Task category linkModuleId Constants
	public final static int TASK_CATEGORY_PROJECTS = 4;

	// System cache names
	public static final String SYSTEM_LOOKUP_LIST_CACHE = "200804092342";
	public static final String SYSTEM_USER_CACHE = "200808061454";
	public static final String SYSTEM_PROJECT_NAME_CACHE = "200401202226";
	public static final String SYSTEM_PROJECT_UNIQUE_ID_CACHE = "200806130953";
	public static final String SYSTEM_PROJECT_CACHE = "200804221400";
	public static final String SYSTEM_NEWS_ARTICLE_CACHE = "200805021506";
	public static final String SYSTEM_WIKI_CACHE = "200805291118";
	public static final String SYSTEM_ZIP_CODE_CACHE = "200805291119";
	public static final String SYSTEM_BADGE_LIST_CACHE = "200806301421";
	public static final String SYSTEM_PROJECT_TICKET_ID_CACHE = "200807070922";
	public static final String SYSTEM_PROJECT_CATEGORY_LIST_CACHE = "200807251039";
	public static final String SESSION_AUTHENTICATION_TOKEN_CACHE = "200807012310";
	public static final String SYSTEM_DASHBOARD_PAGE_CACHE = "200810030928";
	public static final String SYSTEM_DASHBOARD_PORTLET_CACHE = "200901161510";
	public static final String SYSTEM_RSS_FEED_CACHE = "200904031620";
	public static final String SYSTEM_INSTANCE_CACHE = "200907061615";
	public static final String SYSTEM_KEY_CACHE = "200907011550";

	// Constants for use in the IndexerService Interface
	public static final int INDEXER_FULL = 1;
	public static final int INDEXER_DIRECTORY = 2;

	// Preference sharing
	public static final int WITH_ANYONE = 1;
	public static final int WITH_FRIENDS = 2;
	public static final int WITH_NO_ONE = 3;

	// Servlet Context attribute names
	public final static String APPLICATION_PREFS = "applicationPrefs";
	public final static String SYSTEM_SETTINGS = "SystemSettings";
	public final static String CONNECTION_POOL = "ConnectionPool";
	public final static String CONNECTION_POOL_RSS = "ConnectionPoolRSS";
	public final static String CONNECTION_POOL_API = "ConnectionPoolAPI";
	public final static String WORKFLOW_MANAGER = "WorkflowManager";
	public final static String OBJECT_HOOK_MANAGER = "ObjectHookManager";
	public final static String SCHEDULER = "Scheduler";
	public final static String WEBDAV_MANAGER = "WebdavManager";
	public final static String FREEMARKER_CONFIGURATION = "FreemarkerConfiguration";
	public final static String USER_SESSION_TRACKER = "Tracker";
	public final static String PORTLET_CONTAINER = "PortletContainer";
	public static final String FULL_INDEX = "index";
	public static final String DIRECTORY_INDEX = "projectIndex";
	public static final String DIRECTORY_INDEX_INITIALIZED = "directoryIndexOK";
	public static final String TEMPLATE_LAYOUT = "Template";
	public static final String TEMPLATE_THEME = "templateTheme";
	public static final String TEMPLATE_COLOR_SCHEME = "templateColorScheme";

	public static final String TEMPLATE_CSS = "CSS";

	// Session attribute names
	public static final String SESSION_USER = "User";
	public static final String SESSION_CONNECTION_ELEMENT = "ConnectionElement";
	public static final String SESSION_SEARCH_BEAN = "searchBean";
	public static final String SESSION_CLIENT_TYPE = "clientType";

	// Cookie attribute names
	public static final String COOKIE_USER_GUID = "USER_GUID";
	public static final String COOKIE_USER_SEARCH_LOCATION = "USER_SEARCH_LOCATION";

	// Global request attribute names
	public static final String REQUEST_INSTANCE = "requestInstance";
	public static final String REQUEST_GLOBAL_ALERTS = "requestGlobalAlerts";
	public static final String REQUEST_MY_PROJECT_COUNT = "requestMyProjectCount";
	public static final String REQUEST_INVITATION_COUNT = "requestInvitationCount";
	public static final String REQUEST_WHATS_NEW_COUNT = "requestWhatsNewCount";
	public static final String REQUEST_WHATS_ASSIGNED_COUNT = "requestWhatsAssignedCount";
	public static final String REQUEST_PRIVATE_MESSAGE_COUNT = "requestPrivateMessageCount";
	public static final String REQUEST_MENU_CATEGORY_LIST = "menuCategoryList";
	public static final String REQUEST_TAB_CATEGORY_LIST = "tabCategoryList";
	public static final String REQUEST_USER = "user";
	public static final String REQUEST_CLIENT_TYPE = "clientType";
	public static final String REQUEST_PROJECT = "project";
	public static final String REQUEST_PROJECT_BADGE_LIST = "requestProjectBadgeList";
	public static final String REQUEST_MAIN_PROFILE = "requestMainProfile";
	public static final String REQUEST_GENERATED_TITLE = "requestGeneratedTitle";
	public static final String REQUEST_GENERATED_CATEGORY = "requestGeneratedCategory";
	public static final String REQUEST_LOGIN_USER_ID = "requestLoginUserId";
	public static final String REQUEST_CURRENT_TEAM_MEMBER = "currentMember";
	public static final String REQUEST_CURRENT_USER = "requestCurrentUser";
	public static final String REQUEST_PAGE_LAYOUT = "PageLayout";
	public static final String REQUEST_PAGE_TITLE_INCLUDE = "requestPageTitleInclude";
	public static final String REQUEST_PAGE_DESCRIPTION_INCLUDE = "requestPageDescriptionInclude";
	public static final String REQUEST_TEMPLATE = "Template";
	public static final String REQUEST_COOKIE_BEAN_ARRAY = "requestCookieBeanArray";

	// Global request parameter names
	public static final String REQUEST_PARAM_POPUP = "popup";
	public static final String REQUEST_PARAM_STYLE = "style";
	public static final String REQUEST_PARAM_INLINE = "inline";
	public static final String REQUEST_PARAM_APP = "app";
}