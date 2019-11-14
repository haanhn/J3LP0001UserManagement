/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.UserDAO;
import haanh.dto.UserDTO;
import haanh.utils.DBUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaAnh
 */
public class ServletUpdatePassword extends HttpServlet {

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
                UserDTO user = ServletCenter.getCurrentUser(request);
                UserDAO dao = new UserDAO();
                
                if (user.getRoleId().equals(DBUtils.ROLE_ADMIN)) {
                    
                } else {
                    String oldPassword = request.getParameter("oldPassword");
                    String newPassword = request.getParameter("newPassword");
                    String confirm = request.getParameter("confirm");
                    
//                    if (correctPassword) {
                        
//                    } else {
                        
//                    }
                }
                
            }
        } catch (Exception e) {
        }
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

    private static int checkPasswordChanging(String accountId, String oldPassword, String newPassword, String confirm) throws SQLException, ClassNotFoundException {
        int result = 0;
        UserDAO dao = new UserDAO();
        boolean correctPassword = dao.checkAccountPassword(accountId, oldPassword);
        if (correctPassword) {
            
        } else {
            result = UrlConstants.ERR_WRONG_OLD_PWD;
        }
        return result;
    }
}
