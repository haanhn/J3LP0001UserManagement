/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.user;

import haanh.utils.DBUtils;
import haanh.utils.DataValidationUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
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
public class UserDAO {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    //Login: only active user can login
    public UserDTO login(String userId, String password) throws NamingException, SQLException, NoSuchAlgorithmException {
        UserDTO dto = null;
        try {
            con = DBUtils.getConnection();
            String sql = "select Fullname, Email, Phone, Photo, Active, RoleId from [User] "
                    + "where UserId=? and Password=? and Active=?";
            String hashedPassword = DataValidationUtils.getSHA256Hashed(password);
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, password);
            stm.setString(2, hashedPassword);
            stm.setBoolean(3, true);
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

    //Find user except userId
    public List<UserDTO> findUserByFullname(String searchValue, String userId) throws NamingException, SQLException {
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
    public List<UserDTO> findUserByFullnameAndRoleId(String searchValue, String roleId, String userId) throws NamingException, SQLException {
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

    public List<UserDTO> findUserByRoleId(String roleId, String userId) throws NamingException, SQLException {
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
    public List<UserDTO> findAllUsers(String userId) throws NamingException, SQLException {
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

    public boolean checkUserIdExist(String userId) throws NamingException, SQLException {
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

    public boolean checkEmailExist(String email) throws NamingException, SQLException {
        boolean existed = false;
        try {
            String sql = "select UserId from [User] where Email=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                existed = true;
            }
        } finally {
            closeConnection();
        }
        return existed;
    }

    public boolean checkEmailExistForUpdate(String userId, String email) throws NamingException, SQLException {
        boolean existed = false;
        try {
            String sql = "select UserId from [User] where UserId!=? and Email=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                existed = true;
            }
        } finally {
            closeConnection();
        }
        return existed;
    }

    public boolean checkPhoneExist(String phone) throws NamingException, SQLException {
        boolean existed = false;
        try {
            String sql = "select UserId from [User] where Phone=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                existed = true;
            }
        } finally {
            closeConnection();
        }
        return existed;
    }

    public boolean checkPhoneExistForUpdate(String userId, String phone) throws NamingException, SQLException {
        boolean existed = false;
        try {
            String sql = "select UserId from [User] where UserId!=? and Phone=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userId);
            stm.setString(2, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                existed = true;
            }
        } finally {
            closeConnection();
        }
        return existed;
    }

    public UserDTO getUserByUserId(String userId) throws NamingException, SQLException {
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

    //Get List all Users : not in a Promo (and include inactive: userPromo.active = false) and not role = AD001
    public List<UserDTO> getListUsersNotInPromoAndNotAdmin(int promoId) throws SQLException, NamingException {
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "select UserId, Fullname, Photo, Active from [User] "
                    + "where [User].UserId not in "
                    + "(select UserId from UserPromotion where PromoId=? and Active=?) "
                    + "and [User].RoleId != ? "
                    + "and [User].Active=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, promoId);
            stm.setBoolean(2, true);
            stm.setString(3, DBUtils.ROLE_ADMIN);
            stm.setBoolean(4, true);
            rs = stm.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getString("UserId"));
                dto.setFullname(rs.getString("Fullname"));
                dto.setPhoto(rs.getString("Photo"));
                dto.setActive(rs.getBoolean("Active"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    //Get List all Users : in a Promo
    public List<UserDTO> getListUsersInPromo(int promoId) throws SQLException, NamingException {
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "select UserId, Fullname, Photo from [User] "
                    + "where [User].UserId in "
                    + "(select UserId from UserPromotion where PromoId=? and Active=?) "
                    + "and [User].RoleId != ? "
                    + "and [User].Active=?";
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, promoId);
            stm.setBoolean(2, true);
            stm.setString(3, DBUtils.ROLE_ADMIN);
            stm.setBoolean(4, true);
            rs = stm.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getString("UserId"));
                dto.setFullname(rs.getString("Fullname"));
                dto.setPhoto(rs.getString("Photo"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public boolean insertUser(UserDTO dto) throws SQLException, NamingException, NoSuchAlgorithmException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into [User](UserId, Password, Fullname, Email, Phone, Photo, Active, RoleId) "
                    + "values (?,?,?,?,?,?,?,?)";
            String hashedPassword = DataValidationUtils.getSHA256Hashed(dto.getPassword());
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getUserId());
            stm.setString(2, hashedPassword);
            stm.setString(3, dto.getFullname());
            stm.setString(4, dto.getEmail());
            stm.setString(5, dto.getPhone());
            stm.setString(6, dto.getPhoto());
            stm.setBoolean(7, dto.getActive());
            stm.setString(8, dto.getRoleId());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateUser(UserDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update [User] set Fullname=?, Email=?, Phone=?, RoleId=? "
                    + "where UserId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getFullname());
            stm.setString(2, dto.getEmail());
            stm.setString(3, dto.getPhone());
            stm.setString(4, dto.getRoleId());
            stm.setString(5, dto.getUserId());
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int updatePassword(String userId, String password) throws SQLException, NamingException, NoSuchAlgorithmException {
        int result = DBUtils.CODE_FAILED;
        try {
            con = DBUtils.getConnection();
            String sql = "update [User] set Password=? "
                    + "where UserId=?";
            String hashedPassword = DataValidationUtils.getSHA256Hashed(password);
            stm = con.prepareStatement(sql);
            stm.setString(1, hashedPassword);
            stm.setString(2, userId);
            int row = stm.executeUpdate();
            if (row > 0) {
                result = DBUtils.CODE_SUCCESS;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updatePhoto(String userId, String photo) throws SQLException, NamingException {
        boolean result = false;
        try {
            con = DBUtils.getConnection();
            String sql = "update [User] set Photo=? "
                    + "where UserId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, photo);
            stm.setString(2, userId);
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    //Set User Active = false
    public boolean deleteUserById(String userId) throws SQLException, NamingException {
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

    public boolean checkAccountPassword(String accountId, String password) throws NamingException, SQLException, NoSuchAlgorithmException {
        boolean check = false;
        try {
            con = DBUtils.getConnection();
            String sql = "select UserId from [User] "
                    + "where UserId=? and Password=? and Active=?";
            String hashedPassword = DataValidationUtils.getSHA256Hashed(password);
            stm = con.prepareStatement(sql);
            stm.setString(1, accountId);
            stm.setString(2, hashedPassword);
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

}
