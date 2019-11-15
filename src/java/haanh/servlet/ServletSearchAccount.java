/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.UserDAO;
import haanh.dao.RoleDAO;
import haanh.dto.UserDTO;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaAnh
 */
public class ServletSearchAccount extends HttpServlet {

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
                url = UrlConstants.PAGE_HOME;
                
                if (activeAdmin) {
                    processAdminRequest(request, url);
                } else {
                    //Non Admin Search
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
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

    private void processAdminRequest(HttpServletRequest request, String url) throws ClassNotFoundException, SQLException {
        String searchValue = request.getParameter("searchValue");
        String roleId = request.getParameter("roleSearched");

        UserDAO userDao = new UserDAO();
        RoleDAO roleDao = new RoleDAO();
        List<UserDTO> list = null;
        Map<String, String> map;
        String msg = null;
        
        UserDTO user = ServletCenter.getCurrentUser(request);
        searchValue = searchValue.trim();
        
        if (searchValue.length() > 0) {
            if (roleId != null && roleId.length() > 0) {
                list = userDao.findUserByFullnameAndRoleId(searchValue, roleId, user.getUserId());
            } else {
                list = userDao.findUserByFullname(searchValue, user.getUserId());
            }
        } else {
            msg = "Your search did not match any result";
        }
        map = roleDao.getAllNonAdminRoles();

        request.setAttribute("users", list);
        request.setAttribute("roles", map);
        request.setAttribute(UrlConstants.ATTR_MESSAGE, msg);
    }
}
