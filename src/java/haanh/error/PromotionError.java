/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.error;

import java.sql.Date;

/**
 *
 * @author HaAnh
 */
public class PromotionError {
    
    private Integer idErr;
    private String nameErr;
    private String descriptionErr;
    private String fromDateErr;
    private String toDateErr;

    public PromotionError() {
    }

    public Integer getIdErr() {
        return idErr;
    }

    public void setIdErr(Integer idErr) {
        this.idErr = idErr;
    }

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getDescriptionErr() {
        return descriptionErr;
    }

    public void setDescriptionErr(String descriptionErr) {
        this.descriptionErr = descriptionErr;
    }

    public String getFromDateErr() {
        return fromDateErr;
    }

    public void setFromDateErr(String fromDateErr) {
        this.fromDateErr = fromDateErr;
    }

    public String getToDateErr() {
        return toDateErr;
    }

    public void setToDateErr(String toDateErr) {
        this.toDateErr = toDateErr;
    }
}
