/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.UserDAO;
import haanh.dto.UserDTO;
import haanh.error.UserError;
import haanh.utils.DataValidationUtils;
import haanh.utils.DtoUtils;
import haanh.utils.UrlConstants;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author HaAnh
 */
public class ServletInsertUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = UrlConstants.PAGE_LOGIN;
        boolean activeSession = ServletCenter.checkSession(request);

        System.out.println("insert  sssssev");
        System.out.println("active SEsion" + activeSession);

        try {
            if (activeSession) {
                boolean activeAdmin = ServletCenter.checkSessionAdmin(request);
                if (activeAdmin) {
                    url = UrlConstants.SERVLET_GET_PAGE_INSERT;
                    processAdminRequest(request);
                } else { //Active non Admin
                    url = UrlConstants.PAGE_404;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
        } catch (Exception ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
        }

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processAdminRequest(HttpServletRequest request) throws SQLException, ClassNotFoundException, Exception {
        Map<String, String> params = (Map<String, String>) request.getAttribute(UrlConstants.ATTR_PARAMS);
        FileItem photoItem = (FileItem) request.getAttribute(UrlConstants.ATTR_PHOTO_ITEM);
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if (isMultipart) {
            boolean resultUser = insertUser(request, params);

            //Insert Photo
            if (resultUser) {
                request.setAttribute(UrlConstants.ATTR_MESSAGE_USER, "Insert User successfully!");

                if (photoItem != null) {
                    String userId = params.get("userId").trim().toLowerCase();
                    String photoPath = getServletContext().getRealPath("/");
                    boolean resultPhoto = insertPhoto(photoItem, userId, photoPath);
                    if (resultPhoto) {
                        boolean updatePhoto = updateUserPhotoData(photoItem, userId);
                        if  (updatePhoto) {
                            request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Insert Photo successfully!");
                        }
                    } else {
                        request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Insert Photo failed!");
                    }
                }
            } else {
                request.setAttribute(UrlConstants.ATTR_MESSAGE_USER, "Insert User failed!");
            }

        }
    }

    //Insert User
    private boolean insertUser(HttpServletRequest request,
            Map<String, String> params) throws SQLException, ClassNotFoundException {
        UserError error;
        boolean result = false;

        //Get parameters        
        String userId = params.get("userId").toLowerCase().trim();
        String password = params.get("password").trim();
        String confirm = params.get("confirm").trim();
        String fullname = params.get("fullname").trim();
        String email = params.get("email").trim().toLowerCase();
        String phone = params.get("phone").trim();
//        String photo = request.getParameter("photo").trim();
        String activeStr = params.get("active");
        String role = params.get("role");
        boolean active = true;
        if (activeStr == null) {
            active = false;
        }

        //Validate Input
        error = validateInsertUserData(userId, password, confirm, fullname, email, phone);

        if (error == null) {
            UserDTO dto = DtoUtils.getUser(userId, password, fullname, email, phone, null, active, role);
            UserDAO dao = new UserDAO();
            result = dao.insertUser(dto);
        } else {
            request.setAttribute(UrlConstants.ATTR_ERROR, error);
        }

        return result;
    }

    //Insert an existing photo
    public static boolean insertPhoto(FileItem item, String userId, String folderPath) throws Exception {
        boolean result = false;

        String filename = item.getName();
        int index = filename.lastIndexOf("\\");
        filename = filename.substring(index + 1);

        boolean validPhoto = DataValidationUtils.validatePhotoExtension(filename);

        if (validPhoto) {
            String extension = filename.substring(filename.lastIndexOf("."));
            filename = userId + extension;
            File file = new File(folderPath + filename);
            item.write(file);
            result = true;
        }

        return result;
    }

    private UserError validateInsertUserData(String userId, String password, String confirm,
            String fullname, String email, String phone)
            throws ClassNotFoundException, SQLException {
        UserError error = new UserError();
        boolean err = false;
        int code;
        //validate User Id
        code = DataValidationUtils.validateUserId(userId);
        if (code == UrlConstants.DATA_INVALID) {
            error.setUserIdErr("User Id length 5-15");
            err = true;
        } else if (code == UrlConstants.ERR_USER_ID_EXISTED) {
            error.setUserIdErr("User Id existed, please choose another");
            err = true;
        }
        //validate password
        code = DataValidationUtils.validatePassword(password);
        if (code == UrlConstants.DATA_INVALID) {
            error.setPasswordErr("Passwrod length 5-30");
            err = true;
        }
        //validate confirm
        if (error.getPasswordErr() == null) {
            if (!confirm.equals(password)) {
                error.setConfirmErr("Confirm must match password");
                err = true;
            }
        }
        //validate fullname
        if (fullname.length() == 0) {
            error.setFullnameErr("Fullname required");
            err = true;
        }
        //validate email
        if (!DataValidationUtils.validateEmailFormat(email)) {
            error.setEmailErr("Email format abc@xy.xy[.xy]");
            err = true;
        } else {
            UserDAO dao = new UserDAO();
            if (dao.checkEmailExist(email)) {
                error.setEmailErr("Email existed, please choose another");
                err = true;
            }
        }
        //validate phone
        if (!DataValidationUtils.validatePhoneFormat(phone)) {
            error.setPhoneErr("Phone allows digits, length: 8-15");
            err = true;
        } else {
            UserDAO dao = new UserDAO();
            if (dao.checkPhoneExist(phone)) {
                error.setPhoneErr("Phone existed, please choose another");
                err = true;
            }
        }
        if (!err) {
            error = null;
        }
        return error;
    }

//    private static int getSQLMessage(Throwable ex) {
//        int result = DBUtils.CODE_FAILED;
//        String eMsg = ex.getMessage();
//        if (eMsg.contains(DBUtils.ERR_MSG_DUPLICATE)) {
//            result = DBUtils.CODE_DUPLICATE_UNIQUE_VALUE;
//            if (eMsg.contains(DBUtils.ERR_MSG_USER_EMAIL_UNIQUE)) {
//                result = DBUtils.CODE_DUPLICATE_USER_EMAIL;
//            } else if (eMsg.contains(DBUtils.ERR_MSG_USER_PHONE_UNIQUE)) {
//                result = DBUtils.CODE_DUPLICATE_USER_PHONE;
//            }
//        }
//        return result;
//    }

    public static boolean updateUserPhotoData(FileItem photoItem, String userId) throws SQLException, ClassNotFoundException {
        UserDAO dao = new UserDAO();
        
        String filename = photoItem.getName();
        int index = filename.lastIndexOf("\\");
        filename = filename.substring(index + 1);
        String extension = filename.substring(filename.lastIndexOf("."));
        filename = userId + extension;
        
        boolean result = dao.updatePhoto(userId, filename);

        return result;
    }
}
