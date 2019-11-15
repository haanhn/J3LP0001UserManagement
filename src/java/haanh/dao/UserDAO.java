/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import haanh.dto.UserDTO;
import haanh.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HaAnh
 */
public class UserDAO {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    //Login: only active user can login
    public UserDTO login(String userId, String password) throws ClassNotFoundException, SQLException {
        UserDTO dto = null;
        try {
            con = DBUtils.getConnection();
            String sql = "select Fullname, Email, Phone, Photo, RoleId from [User] "
                    + "where UserId=? and Password=? and Active=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, password);
            stm.setBoolean(3, true);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new UserDTO();
                dto.setUserId(userId);
                dto.setFullname(rs.getString("Fullname"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setPhoto(rs.getString("Photo"));
                dto.setRoleId(rs.getString("RoleId"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    //Find user except userId
    public List<UserDTO> findUserByFullname(String searchValue, String userId) throws ClassNotFoundException, SQLException {
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "select UserId, Fullname, Photo, Active, RoleId from [User] "
                    + "where Fullname like ?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("UserId");
                System.out.println("User Id = " + id);
                if (!id.equals(userId)) {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(id);
                    dto.setFullname(rs.getString("Fullname"));
                    dto.setPhoto(rs.getString("Photo"));
                    dto.setRoleId(rs.getString("RoleId"));
                    dto.setActive(rs.getBoolean("Active"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    //Find user except userId
    public List<UserDTO> findUserByFullnameAndRoleId(String searchValue, String roleId, String userId) throws ClassNotFoundException, SQLException {
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "select UserId, Fullname, Photo, Active, RoleId from [User] "
                    + "where Fullname like ? and roleId=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            stm.setString(2, roleId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("UserId");
                if (!id.equals(userId)) {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(id);
                    dto.setFullname(rs.getString("Fullname"));
                    dto.setPhoto(rs.getString("Photo"));
                    dto.setActive(rs.getBoolean("Active"));
                    dto.setRoleId(rs.getString("RoleId"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public List<UserDTO> findUserByRoleId(String roleId, String userId) throws ClassNotFoundException, SQLException {
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "select UserId, Fullname, Photo, Active, RoleId from [User] "
                    + "where roleId=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, roleId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("UserId");
                if (!id.equals(userId)) {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(id);
                    dto.setFullname(rs.getString("Fullname"));
                    dto.setPhoto(rs.getString("Photo"));
                    dto.setActive(rs.getBoolean("Active"));
                    dto.setRoleId(rs.getString("RoleId"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    //Find all except userId
    public List<UserDTO> findAllUsers(String userId) throws ClassNotFoundException, SQLException {
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "select UserId, Fullname, Photo, Active, RoleId from [User]";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("UserId");
                if (!id.equals(userId)) {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(id);
                    dto.setFullname(rs.getString("Fullname"));
                    dto.setPhoto(rs.getString("Photo"));
                    dto.setActive(rs.getBoolean("Active"));
                    dto.setRoleId(rs.getString("RoleId"));
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean checkUserIdExist(String userId) throws ClassNotFoundException, SQLException {
        boolean existed = false;
        try {
            String sql = "select UserId from [User] where UserId=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            rs = stm.executeQuery();
            if (rs.next()) {
                existed = true;
            }
        } finally {
            closeConnection();
        }
        return existed;
    }

    public UserDTO getUserByUserId(String userId) throws ClassNotFoundException, SQLException {
        UserDTO dto = null;
        try {
            String sql = "select UserId, Fullname, Email, Phone, Photo, Active, RoleId from [User] where UserId=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new UserDTO();
                dto.setUserId(userId);
                dto.setFullname(rs.getString("Fullname"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setPhoto(rs.getString("Photo"));
                dto.setActive(rs.getBoolean("Active"));
                dto.setRoleId(rs.getString("RoleId"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public int insertUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        int result = DBUtils.CODE_FAILED;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into [User](UserId, Password, Fullname, Email, Phone, Photo, Active, RoleId) "
                    + "values (?,?,?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getUserId());
            stm.setString(2, dto.getPassword());
            stm.setString(3, dto.getFullname());
            stm.setString(4, dto.getEmail());
            stm.setString(5, dto.getPhone());
            stm.setString(6, dto.getPhoto());
            stm.setBoolean(7, dto.getActive());
            stm.setString(8, dto.getRoleId());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = DBUtils.CODE_SUCCESS;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        int result = DBUtils.CODE_FAILED;
        try {
            con = DBUtils.getConnection();
            String sql = "update [User] set Fullname=?, Email=?, Phone=?, Photo=?, RoleId=? "
                    + "where UserId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getFullname());
            stm.setString(2, dto.getEmail());
            stm.setString(3, dto.getPhone());
            stm.setString(4, dto.getPhoto());
            stm.setString(5, dto.getRoleId());
            stm.setString(6, dto.getUserId());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = DBUtils.CODE_SUCCESS;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    //Set User Active = false
    public boolean deleteUserById(String userId) throws SQLException, ClassNotFoundException {
        try {
            String sql = "update [User] set Active=? where UserId=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, false);
            stm.setString(2, userId);
            int row = stm.executeUpdate();
            return (row > 0);
        } finally {
            closeConnection();
        }
    }

    public boolean checkAccountPassword(String accountId, String password) throws ClassNotFoundException, SQLException {
        boolean check = false;
        try {
            con = DBUtils.getConnection();
            String sql = "select UserId from [User] "
                    + "where UserId=? and Password=? and Active=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, accountId);
            stm.setString(2, password);
            stm.setBoolean(3, true);
            rs = stm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } finally {
            closeConnection();
        }
        return check;
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

    private int getSQLMessage(Throwable ex) {
        int result = DBUtils.CODE_FAILED;
        String eMsg = ex.getMessage();
        if (eMsg.contains(DBUtils.ERR_MSG_DUPLICATE)) {
            result = DBUtils.CODE_DUPLICATE_UNIQUE_VALUE;
            if (eMsg.contains(DBUtils.ERR_MSG_USER_EMAIL_UNIQUE)) {
                result = DBUtils.CODE_DUPLICATE_USER_EMAIL;
            } else if (eMsg.contains(DBUtils.ERR_MSG_USER_PHONE_UNIQUE)) {
                result = DBUtils.CODE_DUPLICATE_USER_PHONE;
            }
        }
        return result;
    }
}
