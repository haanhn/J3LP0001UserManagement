/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

/**
 *
 * @author HaAnh
 */
public class UrlConstants {
    
    //Servlet
    public static final String SERVLET_CENTER = "ServletCenter";
    public static final String SERVLET_HOME = "ServletHome";
    public static final String SERVLET_LOGIN = "ServletLogin";
    public static final String SERVLET_LOG_OUT = "ServletLogOut";
    public static final String SERVLET_SEARCH_ACCOUNT = "ServletSearchAccount";
    public static final String SERVLET_DELETE_ACCOUNT = "ServletDeleteAccount";
    public static final String SERVLET_UPDATE_PASSWORD = "ServletUpdatePassword";
    public static final String SERVLET_GET_PAGE_INSERT = "ServletGetPageInsert";
    public static final String SERVLET_INSERT_USER = "ServletInsertUser";
    public static final String SERVLET_GET_USER_DETAIL = "ServletGetUserDetail";
    public static final String SERVLET_UPDATE_USER = "ServletUpdateUser";
    
    //Urls
    public static final String PAGE_LOGIN = "login.jsp";
    public static final String PAGE_HOME = "index.jsp";
    public static final String PAGE_INSERT_USER = "insert-user.jsp";
    public static final String PAGE_USER_DETAIL = "user-detail.jsp";
    public static final String PAGE_404 = "page-404.jsp";
    
    //Attributes
    public static final String ATTR_CURRENT_USER = "currentUser";
    public static final String ATTR_USER = "user";
    public static final String ATTR_ROLES = "roles";
    public static final String ATTR_ERROR = "error";
    public static final String ATTR_MESSAGE = "message";
    
    //Errors
    public static final int ERR_WRONG_OLD_PWD = -3;
    public static final int ERR_USER_ID_EXISTED = -2;
    
    //Code for validation
    public static final int DATA_VALID = 1;
    public static final int DATA_INVALID = -1;
    
}
