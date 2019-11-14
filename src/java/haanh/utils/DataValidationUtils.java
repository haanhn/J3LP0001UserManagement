/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import haanh.dao.UserDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HaAnh
 */
public class DataValidationUtils {

    public static int validateUserId(String userId) {
        int code = UrlConstants.DATA_VALID;
        if (userId.length() < 5 || userId.length() > 15) {
            code = UrlConstants.DATA_INVALID;
        } else {
            UserDAO dao = new UserDAO();
            boolean existed;
            try {
                existed = dao.checkUserIdExist(userId);
                if (existed) {
                    code = UrlConstants.ERR_USER_ID_EXISTED;
                }
            } catch (ClassNotFoundException | SQLException ex) {
                code = UrlConstants.DATA_INVALID;
                Logger.getLogger(DataValidationUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return code;
    }

    public static int validatePassword(String password) {
        int code = UrlConstants.DATA_VALID;
        if (password.length() < 5 || password.length() > 30) {
            code = UrlConstants.DATA_INVALID;
        }
        return code;
    }

    public static int validateEmail(String email) {
        int code = UrlConstants.DATA_VALID;
        String regex = "[a-zA-Z0-9]{5,30}@[a-zA-Z0-9]{1,20}(\\.[a-zA-Z0-9]{1,20}){1,2}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            code = UrlConstants.DATA_INVALID;
        }
        return code;
    }

    public static int validatePhone(String phone) {
        int code = UrlConstants.DATA_VALID;
        String regex = "[0-9]{8,15}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            code = UrlConstants.DATA_INVALID;
        }
        return code;
    }
}
