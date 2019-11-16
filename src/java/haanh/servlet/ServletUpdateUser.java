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

        String url = UrlConstants.PAGE_LOGIN;
        boolean activeSession = ServletCenter.checkSession(request);

        int result = DBUtils.CODE_FAILED;
        UserError error;

        System.out.println("update  sssssev");

        try {
            if (activeSession) {

                //Get parameters & set url
//                url = UrlConstants.SERVLET_;
                String userId = request.getParameter("userId").trim();
                String fullname = request.getParameter("fullname").trim();
                String email = request.getParameter("email").trim().toLowerCase();
                String phone = request.getParameter("phone").trim();
                String photo = request.getParameter("photo").trim();
                String role = request.getParameter("role");
                
                //Validate Input
                error = validateUserData(fullname, email, phone);
                if (error == null) {
                    UserDTO dto = DtoUtils.getUser(userId, null, fullname, email, phone, photo, null, role);
                    UserDAO dao = new UserDAO();
                    result = dao.updateUser(dto);
                } else {
                    request.setAttribute(UrlConstants.ATTR_ERROR, error);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
//            result = getSQLMessage(ex);
            log(ex.getMessage(), ex);
        } catch (Exception ex) {
//            result = getSQLMessage(ex);
            log(ex.getMessage(), ex);
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

    private static UserError validateUserData(String fullname, String email, String phone) {
        UserError error = new UserError();
        boolean err = false;
        int code;
        //validate fullname
        if (fullname.length() == 0) {
            error.setFullnameErr("Fullname required");
            err = true;
        }
        //validate email
//        code = DataValidationUtils.validateEmailFormat(email);
//        if (code == UrlConstants.DATA_INVALID) {
//            error.setEmailErr("Email format abc@xy.xy[.xy]");
//            err = true;
//        }
        //validate phone
//        code = DataValidationUtils.validatePhoneFormat(phone);
//        if (code == UrlConstants.DATA_INVALID) {
//            error.setPhoneErr("Phone allows digits length 8-15");
//            err = true;
//        }
        if (!err) {
            error = null;
        }
        return error;
    }

}
