/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.UserDAO;
import haanh.dto.UserDTO;
import haanh.error.UserError;
import haanh.utils.DBUtils;
import haanh.utils.DataValidationUtils;
import haanh.utils.DtoUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        int result = DBUtils.CODE_FAILED;
        UserError error;
        
        System.out.println("insert  sssssev");
        
        try {
            if (activeSession) {
                activeSession = ServletCenter.checkSessionAdmin(request);
                if (activeSession) {
                    //Get parameters & set url
                    url = UrlConstants.SERVLET_GET_PAGE_INSERT;
                    String userId = request.getParameter("userId").trim();
                    String password = request.getParameter("password").trim();
                    String confirm = request.getParameter("confirm").trim();
                    String fullname = request.getParameter("fullname").trim();
                    String email = request.getParameter("email").trim().toLowerCase();
                    String phone = request.getParameter("phone").trim();
                    String photo = request.getParameter("photo").trim();
                    String activeStr = request.getParameter("active");
                    String role = request.getParameter("role");
                    boolean active = true;
                    if (activeStr == null) {
                        active = false;
                    }
                    System.out.println("Active " + activeStr);

                    //Validate Input
                    error = validateInsertUserData(userId, password, confirm, fullname, email, phone);
                    if (error == null) {
                        UserDTO dto = DtoUtils.getUser(userId, password, fullname, email, phone, photo, active, role);
                        UserDAO dao = new UserDAO();
                        result = dao.insertUser(dto);
                    } else {
                        request.setAttribute(UrlConstants.ATTR_ERROR, error);
                    }
                } else { //Active non Admin
                    url = UrlConstants.PAGE_404;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            result = getSQLMessage(ex);
            log(ex.getMessage(), ex);
        } catch (Exception ex) {
            result = getSQLMessage(ex);
            log(ex.getMessage(), ex);
        }
        
        //Return result to Browser
        if (result == DBUtils.CODE_SUCCESS) {
            String msg = "Insert User successfully!";
            request.setAttribute(UrlConstants.ATTR_MESSAGE, msg);
        } else if (result == DBUtils.CODE_FAILED) {
            String msg = "Insert User failed!";
            request.setAttribute(UrlConstants.ATTR_MESSAGE, msg);
        } else if (result < DBUtils.CODE_FAILED) {
            error = DtoUtils.getUserErrorByCode(result);
            request.setAttribute(UrlConstants.ATTR_ERROR, error);
        }
        
        System.out.println("url =  " + url);
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

    private static UserError validateInsertUserData(String userId, String password, String confirm,
            String fullname, String email, String phone) {
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
        code = DataValidationUtils.validateEmail(email);
        if (code == UrlConstants.DATA_INVALID) {
            error.setEmailErr("Email format abc@xy.xy[.xy]");
            err = true;
        }
        //validate phone
        code = DataValidationUtils.validatePhone(phone);
        if (code == UrlConstants.DATA_INVALID) {
            error.setPhoneErr("Phone allows digits length 8-15");
            err = true;
        }
        if (!err) {
            error = null;
        }
        return error;
    }

    private static int getSQLMessage(Throwable ex) {
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
