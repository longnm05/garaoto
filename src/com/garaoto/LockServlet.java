package com.garaoto;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lock")
public class LockServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int month = Integer.parseInt(request.getParameter("month"));
            int year = Integer.parseInt(request.getParameter("year"));
            new PayrollDAO().lockMonth(month, year);
            response.sendRedirect("payroll?month=" + month + "&year=" + year + "&message=locked");
        } catch (Exception error) {
            response.sendError(500, error.getMessage());
        }
    }
}
