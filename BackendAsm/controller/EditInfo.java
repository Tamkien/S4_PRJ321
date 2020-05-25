/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.IndividualDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Individual;
import model.Status;

/**
 *
 * @author Tamkien
 */
@WebServlet(name = "EditInfo", urlPatterns = {"/EditInfo"})
public class EditInfo extends HttpServlet {

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
            IndividualDAO idb = new IndividualDAO();
            Individual i = idb.searchIndividualByID(request.getParameter("id"));
            request.setAttribute("d", i);
            request.getRequestDispatcher("edit-individual.jsp").forward(request, response);
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
        String cmnd = request.getParameter("id");
        String name = request.getParameter("name");
        Date sDate = Date.valueOf(request.getParameter("dob"));
        boolean gender = request.getParameter("gender").equals("1");
        IndividualDAO idb = new IndividualDAO();
        Individual i = new Individual();
        i.setPersonID(cmnd);
        i.setDob(sDate);
        i.setPersonName(name);
        i.setMale(gender);
        idb.editPerson(i);
        request.setAttribute("success", Boolean.TRUE);
        request.setAttribute("message", "Đã sửa.");
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
