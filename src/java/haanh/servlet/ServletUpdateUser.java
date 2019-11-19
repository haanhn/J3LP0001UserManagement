/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.user.UserDAO;
import haanh.user.UserDTO;
import haanh.user.UserError;
import haanh.utils.DataValidationUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
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
public class ServletUpdateUser extends HttpServlet {

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

        String url = UrlConstants.SERVLET_GET_USER_DETAIL;
        
        try {
            processRequest(request);
        } catch (SQLException | NamingException ex) {
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
        ServletUpdateUser.this.processRequest(request, response);
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
        ServletUpdateUser.this.processRequest(request, response);
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

    private boolean processRequest(HttpServletRequest request) throws SQLException, NamingException {
        boolean result = false;
        //Get parameters & set url
        String userId = request.getParameter("userId").trim();
        String fullname = request.getParameter("fullname").trim();
        String email = request.getParameter("email").trim().toLowerCase();
        String phone = request.getParameter("phone").trim();
        String role = request.getParameter("role");

        //Validate Input
        UserError error = validateUserData(userId, fullname, email, phone);
        if (error == null) {
            UserDTO dto = new UserDTO(userId, null, fullname, email, phone, null, null, role);
            UserDAO dao = new UserDAO();
            result = dao.updateUser(dto);
            
            UserDTO currentUser = ServletCenter.getCurrentUser(request);
            
            //check if update current user profile
            if (userId.equals(currentUser.getUserId())) {
                currentUser.setFullname(fullname);
                currentUser.setEmail(email);
                currentUser.setPhone(phone);
            }
        } else {
            request.setAttribute(UrlConstants.ATTR_ERROR, error);
        }

        if (result) {
            request.setAttribute(UrlConstants.ATTR_MESSAGE, "Update User successfully!");
        } else {
            request.setAttribute(UrlConstants.ATTR_MESSAGE, "Update User failed!");
        }
        return result;
    }

    private UserError validateUserData(String userId, String fullname, String email, String phone)
            throws NamingException, SQLException {
        UserError error = new UserError();
        boolean err = false;
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
            if (dao.checkEmailExistForUpdate(userId, email)) {
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
            if (dao.checkPhoneExistForUpdate(userId, phone)) {
                error.setPhoneErr("Phone existed, please choose another");
                err = true;
            }
        }
        if (!err) {
            error = null;
        }
        return error;
    }

}
