/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.dao.PromotionDAO;
import haanh.dto.PromotionDTO;
import haanh.error.PromotionError;
import haanh.utils.DtoUtils;
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
public class ServletUpdatePromotion extends HttpServlet {

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

        String url = UrlConstants.PAGE_BACKGROUND;
        int id = Integer.parseInt(request.getParameter("id").trim());
        String name = request.getParameter("name").trim();
        String description = request.getParameter("description").trim();
        String fromDate = request.getParameter("fromDate").trim();
        String toDate = request.getParameter("toDate").trim();

        PromotionError error = ServletInsertPromotion.validatePromoData(name, description, fromDate, toDate);

        try {
            if (error == null) {
                PromotionDAO dao = new PromotionDAO();
                PromotionDTO dto = DtoUtils.getPromotion(id, name, description, fromDate, toDate, null);
                
                if (dao.updatePromo(dto)) {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE, "Update Promotion successfully!");
                } else {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE, "Update Promotion failed!");                    
                }
                
            }
        } catch (SQLException | ClassNotFoundException ex) {
            log(ex.getMessage(), ex);
            url = UrlConstants.PAGE_ERROR;
        }
        
        request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_PROMO_DETAIL);
        request.setAttribute(UrlConstants.ATTR_ERROR, error);
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
