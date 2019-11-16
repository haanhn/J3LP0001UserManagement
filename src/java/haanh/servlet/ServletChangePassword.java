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
public class ServletChangePassword extends HttpServlet {

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

        try {
            if (activeSession) {
                url = UrlConstants.PAGE_CHANGE_PASSWORD;
                processRequest(request);
            }
        } catch (ClassNotFoundException | SQLException e) {
            log(e.getMessage(), e);
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

    private void processRequest(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        UserDTO user = ServletCenter.getCurrentUser(request);

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirm = request.getParameter("confirm");

        UserError error = checkPasswordChanging(user.getUserId(), oldPassword);
        String msg = null;
        
        if (error == null) {
            error = validatePassword(newPassword, confirm);
            if (error == null) {
                UserDAO dao = new UserDAO();
                int code = dao.updatePassword(user.getUserId(), newPassword);
                if (code == DBUtils.CODE_SUCCESS) {
                    msg = "Update Password successfully!";
                } else if (code == DBUtils.CODE_FAILED) {
                    msg = "Update Password failed!";
                }
             }
        }
        
        request.setAttribute(UrlConstants.ATTR_ERROR, error);
        request.setAttribute(UrlConstants.ATTR_MESSAGE, msg);
    }
    
    private static UserError checkPasswordChanging(String accountId, String oldPassword) throws SQLException, ClassNotFoundException {
        UserError error = null;
        UserDAO dao = new UserDAO();
        boolean correctPassword = dao.checkAccountPassword(accountId, oldPassword);
        if (!correctPassword) {
            error = new UserError();
            error.setOldPasswordErr("Password incorrect");
        }
        return error;
    }

    private UserError validatePassword(String password, String confirm) {
        UserError error = new UserError();
        boolean err = false;
        int code;
        //validate password
        code = DataValidationUtils.validatePassword(password);
        if (code == UrlConstants.DATA_INVALID) {
            error.setNewPasswordErr("Passwrod length 5-30");
            err = true;
        }
        //validate confirm
        if (error.getPasswordErr() == null) {
            if (!confirm.equals(password)) {
                error.setConfirmErr("Confirm must match password");
                err = true;
            }
        }

        if (!err) {
            error = null;
        }
        return error;
    }
}
