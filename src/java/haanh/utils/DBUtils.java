/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author HaAnh
 */
public class DBUtils {
    
    //DB constants
    public static final String ROLE_ADMIN = "AD001";
    public static final int USER_PROMO_DEFAULT_RANK = 5;
    
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_FAILED = -1;
    public static final int CODE_DUPLICATE_UNIQUE_VALUE = -2;
    public static final int CODE_DUPLICATE_USER_EMAIL = -3;
    public static final int CODE_DUPLICATE_USER_PHONE = -4;
    
    public static final String ERR_MSG_DUPLICATE = "duplicate";
    public static final String ERR_MSG_PRIMARY_KEY = "PRIMARY KEY";
    public static final String ERR_MSG_USER_EMAIL_UNIQUE = "U_Email";
    public static final String ERR_MSG_USER_PHONE_UNIQUE = "U_Phone";
    
    public static Connection getConnection() throws SQLException, NamingException {
        Context context = new InitialContext();
        Context env = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) env.lookup("J3LP0001UserManagementDS");
        Connection con = ds.getConnection();
        return con;
    }
}
