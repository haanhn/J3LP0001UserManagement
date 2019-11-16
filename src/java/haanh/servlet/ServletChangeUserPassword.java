/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.UserDAO;
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
public class ServletChangeUserPassword extends HttpServlet {

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
                boolean activeAdmin = ServletCenter.checkSessionAdmin(request);
                if (activeAdmin) {
                    url = UrlConstants.PAGE_CHANGE_USER_PASSWORD;
                    processAdminRequest(request);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
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

    private void processAdminRequest(HttpServletRequest request) throws SQLException, ClassNotFoundException {

        String userId = request.getParameter("userId");
        String newPassword = request.getParameter("newPassword");
        String confirm = request.getParameter("confirm");

        String msg = null;

        UserError error = DataValidationUtils.validatePassword(newPassword, confirm);
        if (error == null) {
            UserDAO dao = new UserDAO();
            int code = dao.updatePassword(userId, newPassword);
            if (code == DBUtils.CODE_SUCCESS) {
                msg = "Update Password successfully!";
            } else if (code == DBUtils.CODE_FAILED) {
                msg = "Update Password failed!";
            }
        }

        request.setAttribute(UrlConstants.ATTR_ERROR, error);
        request.setAttribute(UrlConstants.ATTR_MESSAGE, msg);
    }

}
