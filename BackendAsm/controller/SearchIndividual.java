/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.IndividualDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Individual;

/**
 *
 * @author Tamkien
 */
@WebServlet(name = "SearchIndividual", urlPatterns = {"/SearchIndividual"})
public class SearchIndividual extends HttpServlet {

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
        }
        String famid = request.getParameter("famid");
        if (!famid.isEmpty()) {
            Individual i = new Individual();
            IndividualDAO idb = new IndividualDAO();
            i.setFamilyID(request.getParameter("famid"));
            i.setRegionID("0");
            i.setStatusID("0");
            i.setPersonID("");
            i.setPersonName("");
            ArrayList<Individual> ar = idb.searchIndividual(i);
            request.setAttribute("list", ar);
            request.getRequestDispatcher("list.jsp").forward(request, response);
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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        IndividualDAO idb = new IndividualDAO();
        String cmnd = request.getParameter("cmnd");
        String name = request.getParameter("name");
        String famid = request.getParameter("famid");
        String region = request.getParameter("region");
        String status = request.getParameter("status");
        Individual i = new Individual();
        i.setPersonID(cmnd);
        i.setFamilyID(famid);
        i.setRegionID(region);
        i.setPersonName(name);
        i.setStatusID(status);
        ArrayList<Individual> ar = idb.searchIndividual(i);
        request.setAttribute("list", ar);
        request.getRequestDispatcher("list.jsp").forward(request, response);
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
