/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import haanh.dao.UserDAO;
import haanh.error.UserError;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

//    public static int validateEmail(String email) {
//        boolean valid = validateEmailFormat(email);
//        if (valid) {
//            
//        } else {
//            
//        }
//        UserDAO dao = new UserDAO();
//        boolean existed = dao.checkEmailExist(email);
//        
//        return code;
//    }
    
    public static boolean validateEmailFormat(String email) {
        boolean valid = false;
        String regex = "[a-zA-Z0-9]{5,30}@[a-zA-Z0-9]{1,20}(\\.[a-zA-Z0-9]{1,20}){1,2}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            valid = true;
        }
        return valid;
    }

    public static boolean validatePhoneFormat(String phone) {
        String regex = "[0-9]{8,15}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
    public static boolean validatePhotoExtension(String str) {
        boolean valid = false;
        if (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg")){
            valid = true;
        }
        return valid;
    }

    public static UserError validatePassword(String password, String confirm) {
        UserError error = new UserError();
        boolean err = false;
        int code;
        //validate password
        code = DataValidationUtils.validatePassword(password);
        if (code == UrlConstants.DATA_INVALID) {
            error.setNewPasswordErr("Passwrod length 5-30");
            err = true;
        }
        //validate confirm
        if (error.getPasswordErr() == null) {
            if (!confirm.equals(password)) {
                error.setConfirmErr("Confirm must match password");
                err = true;
            }
        }
        if (!err) {
            error = null;
        }
        return error;
    }
    
    public static boolean validateDate(String dateStr) {
        boolean valid = true;
        if (dateStr == null || dateStr.length() == 0) {
            return false;
        }
        
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            valid = false;
        }
        return valid;
    }
    
    public static Date getDateFromString(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        
        try {
            java.util.Date dateUtil = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dateUtil.getTime());
            Date date = new Date(cal.getTimeInMillis());
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}
