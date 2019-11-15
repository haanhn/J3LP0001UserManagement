/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dto.UserDTO;
import haanh.utils.DBUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaAnh
 */
public class ServletCenter extends HttpServlet {

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
        String action = request.getParameter("action");
        boolean activeSession = checkSession(request);

        if (activeSession) {
            url = UrlConstants.SERVLET_HOME;

            if (action != null) {
                if (action.equals("Search")) {
                    url = UrlConstants.SERVLET_SEARCH_ACCOUNT;
                } else if (action.equals("Delete")) {
                    url = UrlConstants.SERVLET_DELETE_ACCOUNT;
                } else if (action.equals("PageInsert")) {
                    url = UrlConstants.SERVLET_GET_PAGE_INSERT;
                } else if (action.equals("Insert")) {
                    url = UrlConstants.SERVLET_INSERT_USER;
                } else if (action.equals("View User Detail")) {
                    url = UrlConstants.SERVLET_GET_USER_DETAIL;
                } else if (action.equals("Update User")) {
                    url = UrlConstants.SERVLET_GET_USER_DETAIL;
                } else if (action.equals("Log Out")) {
                    url = UrlConstants.SERVLET_LOG_OUT;
                } else if (action.equals("GetUsersByRole")) {
                    url = UrlConstants.SERVLET_GET_USERS_BY_ROLE;
                }
            }
        } else {
            if (action != null && action.equals("Login")) {
                url = UrlConstants.SERVLET_LOGIN;
            } 
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

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        UserDTO dto = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute(UrlConstants.ATTR_CURRENT_USER) != null) {
                dto = (UserDTO) session.getAttribute(UrlConstants.ATTR_CURRENT_USER);
            }
        }
        return dto;
    }
    
    public static boolean checkSession(HttpServletRequest request) {
        boolean activeSession = false;
        if (getCurrentUser(request) != null) {
            activeSession = true;
        }
        return activeSession;
    }
    
    public static boolean checkSessionAdmin(HttpServletRequest request) {
        boolean activeAdmin = false;
        UserDTO  dto = getCurrentUser(request);
        if (dto != null) {
            if (dto.getRoleId().equals(DBUtils.ROLE_ADMIN)) {
                activeAdmin = true;
            }
        }
        return activeAdmin;
    }
    
    public static String getUserRoleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDTO dto = (UserDTO) session.getAttribute(UrlConstants.ATTR_CURRENT_USER);
        String role = dto.getRoleId();
        return role;
    }
}
