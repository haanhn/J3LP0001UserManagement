/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import haanh.dto.PromotionDTO;
import haanh.dto.UserDTO;
import java.sql.Date;

/**
 *
 * @author HaAnh
 */
public class DtoUtils {

    public static UserDTO getUser(String userId, String password, String fullname,
            String email, String phone, String photo,
            Boolean active, String roleId) {
        UserDTO dto = new UserDTO();
        dto.setUserId(userId);
        dto.setPassword(password);
        dto.setFullname(fullname);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setPhoto(photo);
        dto.setActive(active);
        dto.setRoleId(roleId);
        return dto;
    }
    
    public static PromotionDTO getPromotion(Integer id, String name, String description,
            String fromDateStr, String toDateStr, Boolean active) {
        PromotionDTO dto = new PromotionDTO();
        Date fromDate = DataValidationUtils.getDateFromString(fromDateStr);
        Date toDate = DataValidationUtils.getDateFromString(toDateStr);
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setFromDate(fromDate);
        dto.setToDate(toDate);
        dto.setActive(active);
        return dto;
    }

//    public static UserError getUserErrorByCode(int code) {
//        if (code >= 0) {
//            return null;
//        }
//        
//        
//        UserError error = new UserError();
//        switch (code) {
//            case DBUtils.CODE_DUPLICATE_UNIQUE_VALUE:
//                error.setUserIdErr("User Id existed, please choose another");
//                break;
//            case DBUtils.CODE_DUPLICATE_USER_EMAIL:
//                error.setEmailErr("Email existed, please choose another");
//                break;
//            case DBUtils.CODE_DUPLICATE_USER_PHONE:
//                error.setPhoneErr("Phone existed, please choose another");
//                break;
//        }
//        return error;
//    }
}
