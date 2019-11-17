/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.dao;

import haanh.dto.PromotionDTO;
import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HaAnh
 */
public class PromotionDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public boolean insertPromo(PromotionDTO dto) throws SQLException, ClassNotFoundException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into Promotion(Id, Name, Description, FromDate, ToDate) "
                    + "values (?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, dto.getId());
            stm.setString(2, dto.getName());
            stm.setString(3, dto.getDescription());
            stm.setDate(4, dto.getFromDate());
            stm.setDate(5, dto.getToDate());
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
