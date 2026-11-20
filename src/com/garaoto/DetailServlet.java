package com.garaoto;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            int month = Integer.parseInt(request.getParameter("month"));
            int year = Integer.parseInt(request.getParameter("year"));
            PayrollDAO dao = new PayrollDAO();
            request.setAttribute("row", dao.getEmployeePayroll(employeeId, month, year));
            request.setAttribute("month", month);
            request.setAttribute("year", year);
            request.setAttribute("locked", dao.isLocked(month, year));
            request.getRequestDispatcher("/chi-tiet.jsp").forward(request, response);
        } catch (Exception error) {
            throw new ServletException(error);
        }
    }
}
