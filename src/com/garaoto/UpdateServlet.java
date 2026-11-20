package com.garaoto;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            int month = Integer.parseInt(request.getParameter("month"));
            int year = Integer.parseInt(request.getParameter("year"));
            double bonus = Double.parseDouble(request.getParameter("thuong"));
            double fine = Double.parseDouble(request.getParameter("phat"));
            double advance = Double.parseDouble(request.getParameter("tamUng"));
            new PayrollDAO().updateAdjustments(employeeId, month, year, bonus, fine, advance);
            response.sendRedirect("payroll?month=" + month + "&year=" + year + "&message=updated");
        } catch (Exception error) {
            response.sendError(500, error.getMessage());
        }
    }
}
