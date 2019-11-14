/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.dao;

import haanh.utils.DBUtils;
import haanh.utils.UrlConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HaAnh
 */
public class RoleDAO {
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    
    public Map<String, String> getAllNonAdminRoles() throws SQLException, ClassNotFoundException  {
        Map<String, String> map = new HashMap<>();
        try {
            String sql = "select Id, Name from Role";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (!rs.getString("Id").equals(DBUtils.ROLE_ADMIN)) {
                    map.put(rs.getString("Id"), rs.getString("Name"));
                }
            }
        } finally {
            closeConnection();
        }
        return map;
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
