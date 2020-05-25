/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.FamilyDAO;
import dal.RegionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Family;
import model.Region;

/**
 *
 * @author Tamkien
 */
@WebServlet(name = "AddFamily", urlPatterns = {"/AddFamily"})
public class AddFamily extends HttpServlet {

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
        if (((Account) request.getSession().getAttribute("user")) == null) {
            request.setAttribute("success", Boolean.FALSE);
            request.getRequestDispatcher("landing.jsp").forward(request, response);
        } else {
            RegionDAO db = new RegionDAO();
            ArrayList<Region> r = db.getRegions();
            request.setAttribute("region", r);
            request.getRequestDispatcher("add-family.jsp").forward(request, response);
        }
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
        String famID = request.getParameter("famID");
        String regID = request.getParameter("region");
        float foodLeft = Float.parseFloat(request.getParameter("foodleft"));
        Family f = new Family();
        f.setFamilyID(famID);
        f.setRegionID(regID);
        f.setFoodLeft(foodLeft);
        FamilyDAO db = new FamilyDAO();
        db.insertFamily(f);
        db.insertFoodleft(f);
        request.setAttribute("success", Boolean.TRUE);
        request.setAttribute("message", "Đã thêm thông tin.");
        request.getRequestDispatcher("alert.jsp").forward(request, response);
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
