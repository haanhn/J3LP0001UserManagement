/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.promotion.PromotionDAO;
import haanh.promotion.PromotionDTO;
import haanh.promotion.PromotionError;
import haanh.utils.DataValidationUtils;
import haanh.utils.DtoUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ServletInsertPromotion extends HttpServlet {

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

        String name = request.getParameter("name").trim();
        String description = request.getParameter("description").trim();
        String fromDate = request.getParameter("fromDate").trim();
        String toDate = request.getParameter("toDate").trim();

        PromotionError error = null;
        
        try {
            error = validatePromoData(name, description, fromDate, toDate);
            if (error == null) {
                PromotionDTO dto = DtoUtils.getPromotion(null, name, description, fromDate, toDate, true);
                PromotionDAO dao = new PromotionDAO();
                boolean result = dao.insertPromo(dto);
                if (result) {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE, "Insert Promotion successfully!");
                } else {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE, "Insert Promotion failed!");
                }
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(ServletInsertPromotion.class.getName()).log(Level.SEVERE, null, ex);
            url = UrlConstants.PAGE_ERROR;
        }

        request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_INSERT_PROMO);
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

    public static PromotionError validatePromoData(String name, String description, String fromDateStr, String toDateStr) {
        PromotionError error = new PromotionError();
        boolean err = false;
        //validate name
        if (name.length() == 0) {
            error.setNameErr("Required");
            err = true;
        }
        //validate description
        if (description.length() == 0) {
            error.setDescriptionErr("Required");
            err = true;
        }

        //validate fromDate
        if (fromDateStr.length() == 0) {
            error.setFromDateErr("Required");
            err = true;
        } else if (!DataValidationUtils.validateDate(fromDateStr)) {
            error.setFromDateErr("Date format: dd/MM/yyyy and must be a valid date");
            err = true;
        }
        //validate toDate
        if (toDateStr.length() == 0) {
            error.setToDateErr("Required");
            err = true;
        } else if (!DataValidationUtils.validateDate(toDateStr)) {
            error.setToDateErr("Date format: dd/MM/yyyy and must be a valid date");
            err = true;
        }
        if (!err) {
            error = null;
        }
        return error;
    }

}
