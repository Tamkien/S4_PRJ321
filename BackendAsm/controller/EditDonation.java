/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.DonationDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Donation;

/**
 *
 * @author Tamkien
 */
@WebServlet(name = "EditDonation", urlPatterns = {"/EditDonation"})
public class EditDonation extends HttpServlet {

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
            DonationDAO db = new DonationDAO();
            Donation d = db.existedDonation(request.getParameter("id"));
            request.setAttribute("d", d);
            request.setAttribute("edit", Boolean.TRUE);
            request.getRequestDispatcher("add-donation.jsp").forward(request, response);
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
        DonationDAO db = new DonationDAO();
        Donation d = new Donation();
        String name = request.getParameter("fullname");
        String address = request.getParameter("address");
        String amount = request.getParameter("amount");
        String donationID = request.getParameter("donationID");
        d.setDonatorName(name);
        d.setAmount(amount);
        d.setDonatorAddress(address);
        d.setDonationID(donationID);
        db.updateDonation(d);
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
