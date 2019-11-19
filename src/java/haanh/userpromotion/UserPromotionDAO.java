/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.userpromotion;

import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author HaAnh
 */
public class UserPromotionDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public UserPromotionDTO getUserPromo(int promoId, String userId) throws NamingException, SQLException {
        UserPromotionDTO dto = null;
        try {
            con = DBUtils.getConnection();
            String sql = "select Id from UserPromotion where PromoId=? and UserId=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, promoId);
            stm.setString(2, userId);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new UserPromotionDTO();
                dto.setId(rs.getInt("Id"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean insertUserPromo(String userId, int promoId, int rank) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into UserPromotion(UserId, PromoId, Rank, Active) "
                    + "values (?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setInt(2, promoId);
            stm.setInt(3, rank);
            stm.setBoolean(4, true);
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateUserPromoStatus(int userPromoId, boolean active) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update UserPromotion set Active=? where Id=?";
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, active);
            stm.setInt(2, userPromoId);
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
