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
    public static final String SERVLET_LOGIN = "ServletLogin";
    public static final String SERVLET_LOG_OUT = "ServletLogOut";
    public static final String SERVLET_SEARCH_ACCOUNT = "ServletSearchAccount";
    public static final String SERVLET_DELETE_ACCOUNT = "ServletDeleteAccount";
    public static final String SERVLET_CHANGE_PASSWORD = "ServletChangePassword";
    public static final String SERVLET_CHANGE_USER_PASSWORD = "ServletChangeUserPassword";
    public static final String SERVLET_CHANGE_USER_PHOTO = "ServletChangeUserPhoto";
    public static final String SERVLET_GET_PAGE_INSERT = "ServletGetPageInsert";
    public static final String SERVLET_INSERT_USER = "ServletInsertUser";
    public static final String SERVLET_GET_USER_DETAIL = "ServletGetUserDetail";
    public static final String SERVLET_UPDATE_USER = "ServletUpdateUser";
    public static final String SERVLET_GET_USERS_BY_ROLE = "ServletGetUsersByRole";
    public static final String SERVLET_VIEW_CURRENT_USER_PROFILE = "ServletViewCurrentUserProfile";
    public static final String SERVLET_VIEW_PROMOTIONS = "ServletViewPromotions";
    public static final String SERVLET_INSERT_PROMO = "ServletInsertPromotion";
    public static final String SERVLET_DELETE_PROMO = "ServletDeletePromotion";
    public static final String SERVLET_UPDATE_PROMO = "ServletUpdatePromotion";
    public static final String SERVLET_GET_LIST_USERS_NOT_IN_PROMO = "ServletGetListUsersNotInPromo";
    public static final String SERVLET_GET_LIST_USERS_IN_PROMO = "ServletGetUsersInPromo";
    public static final String SERVLET_ADD_USER_TO_PROMO = "ServletAddUserToPromo";
    public static final String SERVLET_REMOVE_USER_FROM_PROMO = "ServletRemoveUserFromPromo";
    
    //Urls
    public static final String PAGE_LOGIN = "login.jsp";
    public static final String PAGE_HOME = "index.jsp";
    public static final String PAGE_BACKGROUND = "background.jsp";
    public static final String PAGE_PROFILE = "profile.jsp";
    public static final String PAGE_INSERT_USER = "insert-user.jsp";
    public static final String PAGE_USER_DETAIL = "user-detail.jsp";
    public static final String PAGE_CHANGE_PASSWORD = "change-password.jsp";
    public static final String PAGE_CHANGE_USER_PASSWORD = "change-user-password.jsp";
    public static final String PAGE_LIST_PROMOTIONS = "list-promotions.jsp";
    public static final String PAGE_INSERT_PROMO = "insert-promo.jsp";
    public static final String PAGE_PROMO_DETAIL = "promo-detail.jsp";
    public static final String PAGE_LIST_USER_NOT_IN_PROMO = "list-user-not-in-promo.jsp";
    public static final String PAGE_LIST_USER_IN_PROMO = "list-user-in-promo.jsp";
    public static final String PAGE_404 = "page-404.jsp";
    public static final String PAGE_ERROR = "error.html";
    
    //Paths
    public static final String PATH_IMAGE = "";
    
    //Attributes
    public static final String ATTR_INCLUDED_PAGE = "includedPage";
    public static final String ATTR_CURRENT_USER = "currentUser";
    public static final String ATTR_USER = "user";
    public static final String ATTR_USERS = "users";
    public static final String ATTR_ROLES = "roles";
    public static final String ATTR_PARAMS = "params";
    public static final String ATTR_PHOTO_ITEM = "photoItem";
    public static final String ATTR_PROMOS = "promos";
    public static final String ATTR_ERROR = "error";
    public static final String ATTR_MESSAGE = "message";
    public static final String ATTR_MESSAGE_USER = "messageUser";
    public static final String ATTR_MESSAGE_PHOTO = "messagePhoto";
    
    //Errors
//    public static final int ERR_WRONG_OLD_PWD = -3;
    public static final int ERR_USER_ID_EXISTED = -2;
    
    //Code for validation
    public static final int DATA_VALID = 1;
    public static final int DATA_INVALID = -1;
    
}
