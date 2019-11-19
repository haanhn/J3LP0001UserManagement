/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.promotion;

import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class PromotionDAO {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public List<PromotionDTO> getAllPromotions() throws SQLException, NamingException {
        List<PromotionDTO> list = new ArrayList<>();
        try {
            con = DBUtils.getConnection();
            String sql = "select Id, Name, Description, FromDate, ToDate, Active from Promotion ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                Date fromDate = rs.getDate("FromDate");
                Date toDate = rs.getDate("FromDate");
                Boolean active = rs.getBoolean("Active");

                PromotionDTO dto = new PromotionDTO(id, name, description, fromDate, toDate, active);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean insertPromo(PromotionDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into Promotion(Name, Description, FromDate, ToDate, Active) "
                    + "values (?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getName());
            stm.setString(2, dto.getDescription());
            stm.setDate(3, dto.getFromDate());
            stm.setDate(4, dto.getToDate());
            stm.setBoolean(5, dto.getActive());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updatePromo(PromotionDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update Promotion set Name=?, Description=?, FromDate=?, ToDate=? "
                    + "where Id=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getName());
            stm.setString(2, dto.getDescription());
            stm.setDate(3, dto.getFromDate());
            stm.setDate(4, dto.getToDate());
            stm.setInt(5, dto.getId());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    //Update Promotion: Active = false
    public boolean deletePromoById(int id) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update Promotion set Active=? where Id=?";
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, false);
            stm.setInt(2, id);
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

}
