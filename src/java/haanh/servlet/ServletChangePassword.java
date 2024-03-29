/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.user.UserDAO;
import haanh.user.UserDTO;
import haanh.user.UserError;
import haanh.utils.DBUtils;
import haanh.utils.DataValidationUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
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

        String url = UrlConstants.PAGE_CHANGE_PASSWORD;
        try {
            if (processRequest(request)) {
                url = UrlConstants.PAGE_LOGIN;
            }
        } catch (NamingException | SQLException | NoSuchAlgorithmException e) {
            log(e.getMessage(), e);
            url = UrlConstants.PAGE_ERROR;
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

    private boolean processRequest(HttpServletRequest request) throws SQLException, NamingException, NoSuchAlgorithmException {
        boolean result = false;
        UserDTO user = ServletCenter.getCurrentUser(request);

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirm = request.getParameter("confirm");

        UserError error = checkPasswordChanging(user.getUserId(), oldPassword);
        String msg = null;
        
        if (error == null) {
            error = DataValidationUtils.validatePassword(newPassword, confirm);
            if (error == null) {
                UserDAO dao = new UserDAO();
                int code = dao.updatePassword(user.getUserId(), newPassword);
                if (code == DBUtils.CODE_SUCCESS) {
                    msg = "Update Password successfully!";
                    result = true;
                } else if (code == DBUtils.CODE_FAILED) {
                    msg = "Update Password failed!";
                }
             }
        }
        
        request.setAttribute(UrlConstants.ATTR_ERROR, error);
        request.setAttribute(UrlConstants.ATTR_MESSAGE, msg);
        return result;
    }
    
    private static UserError checkPasswordChanging(String accountId, String oldPassword) throws SQLException, NamingException, NoSuchAlgorithmException {
        UserError error = null;
        UserDAO dao = new UserDAO();
        boolean correctPassword = dao.checkAccountPassword(accountId, oldPassword);
        if (!correctPassword) {
            error = new UserError();
            error.setOldPasswordErr("Password incorrect");
        }
        return error;
    }

}
