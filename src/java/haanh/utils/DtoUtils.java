/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import haanh.promotion.PromotionDTO;
import java.sql.Date;

/**
 *
 * @author HaAnh
 */
public class DtoUtils {
    
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
}
