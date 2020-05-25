/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.FamilyDAO;
import dal.IndividualDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Family;
import model.Status;

/**
 *
 * @author Tamkien
 */
@WebServlet(name = "AddIndividual", urlPatterns = {"/AddIndividual"})
public class AddIndividual extends HttpServlet {

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
        request.setAttribute("found", Boolean.TRUE);
        request.getRequestDispatcher("search-family.jsp").forward(request, response);
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
        FamilyDAO db = new FamilyDAO();
        String id = request.getParameter("id");
        Family f = db.searchFamily(id);
        if (f == null) {
            request.setAttribute("found", Boolean.FALSE);
            request.getRequestDispatcher("search-family.jsp").forward(request, response);
        } else {
            IndividualDAO idb = new IndividualDAO();
            ArrayList<Status> s = idb.getStatus();
            request.setAttribute("status", s);
            request.setAttribute("family", f);
            request.getRequestDispatcher("add-individual.jsp").forward(request, response);
        }
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
