/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.UserPromotionDAO;
import haanh.dto.UserPromotionDTO;
import haanh.utils.DBUtils;
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
public class ServletAddUserToPromo extends HttpServlet {

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
        
        String url = UrlConstants.SERVLET_GET_LIST_USERS_NOT_IN_PROMO;
        int promoId = Integer.parseInt(request.getParameter("promoId"));
        String userId = request.getParameter("userId");
        
        try {
            UserPromotionDAO dao = new UserPromotionDAO();
            boolean result;
            UserPromotionDTO userPromo = dao.getUserPromo(promoId, userId);
            if (userPromo == null) {
                result = dao.insertUserPromo(userId, promoId, DBUtils.USER_PROMO_DEFAULT_RANK);
            } else {
                result = dao.updateUserPromoToActive(userPromo.getId());
            }
            
            if (result) {
                request.setAttribute(UrlConstants.ATTR_MESSAGE, "Add User " + userId + " successfully!");
            } else {
                request.setAttribute(UrlConstants.ATTR_MESSAGE, "Add User " + userId + " failed!");
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            log(ex.getMessage(), ex);
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

}
