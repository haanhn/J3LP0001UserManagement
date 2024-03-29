/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.role.RoleDAO;
import haanh.user.UserDAO;
import haanh.user.UserDTO;
import static haanh.servlet.ServletInsertUser.insertPhoto;
import static haanh.servlet.ServletInsertUser.updateUserPhotoData;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author HaAnh
 */
public class ServletChangeUserPhoto extends HttpServlet {

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
        
        try {
            Map<String, String> params = (Map<String, String>) request.getAttribute(UrlConstants.ATTR_PARAMS);
            FileItem photoItem = (FileItem) request.getAttribute(UrlConstants.ATTR_PHOTO_ITEM);

            String userId = params.get("userId");
            String photoPath = getServletContext().getRealPath("/");
            
            boolean resultPhoto = insertPhoto(photoItem, userId, photoPath);
            if (resultPhoto) {
                boolean updatePhoto = updateUserPhotoData(photoItem, userId);
                if (updatePhoto) {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Insert Photo successfully!");
                }
            } else {
                request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Insert Photo failed!");
            }
            
            request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_USER_DETAIL);
            request.setAttribute(UrlConstants.ATTR_USER, getUserDetail(userId));
            request.setAttribute(UrlConstants.ATTR_ROLES, getRoles());
        } catch (Exception ex) {
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

    private UserDTO getUserDetail(String userId) throws NamingException, SQLException {
        UserDAO dao = new UserDAO();
        UserDTO dto = dao.getUserByUserId(userId);
        return dto;
    }
    
    private Map<String, String> getRoles() throws SQLException, NamingException {
        RoleDAO dao = new RoleDAO();
        return dao.getAllNonAdminRoles();
    }
}
