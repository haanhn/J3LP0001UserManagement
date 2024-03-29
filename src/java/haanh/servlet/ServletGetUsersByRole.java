/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.role.RoleDAO;
import haanh.user.UserDAO;
import haanh.user.UserDTO;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class ServletGetUsersByRole extends HttpServlet {

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
                url = UrlConstants.PAGE_BACKGROUND;
                boolean activeAdmin = ServletCenter.checkSessionAdmin(request);

                if (activeAdmin) {
                    processAdminRequest(request);
                } else {
                    //Non Admin Search
                    processNonAdminRequest(request);
                }
                request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_HOME);

            }
        } catch (NamingException | SQLException ex) {
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

    private void processAdminRequest(HttpServletRequest request) throws SQLException, NamingException {
        UserDAO dao = new UserDAO();
        RoleDAO roleDao = new RoleDAO();
        
        UserDTO user = ServletCenter.getCurrentUser(request);
        String roleId = request.getParameter("roleSearched");
        
        List<UserDTO> users;
        if (roleId != null && roleId.length() > 0) {
            users = dao.findUserByRoleId(roleId, user.getUserId());
        } else {
            users = dao.findAllUsers(user.getUserId());
        }
        
        Map<String, String> roles = roleDao.getAllNonAdminRoles();

        request.setAttribute(UrlConstants.ATTR_USERS, users);
        request.setAttribute(UrlConstants.ATTR_ROLES, roles);
    }
    
    private void processNonAdminRequest(HttpServletRequest request) throws NamingException, SQLException {
        UserDTO dto = ServletCenter.getCurrentUser(request);
        List<UserDTO> list = new ArrayList<>();
        list.add(dto);
        
        RoleDAO roleDao = new RoleDAO();
        Map<String, String> roles = roleDao.getAllNonAdminRoles();
        
        request.setAttribute(UrlConstants.ATTR_USERS, list);
        request.setAttribute(UrlConstants.ATTR_ROLES, roles);
    }
}
