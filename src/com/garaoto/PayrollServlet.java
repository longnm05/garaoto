package com.garaoto;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/payroll")
public class PayrollServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int month = Integer.parseInt(request.getParameter("month"));
            int year = Integer.parseInt(request.getParameter("year"));

            PayrollDAO dao = new PayrollDAO();
            List<PayrollRow> rows = dao.getPayroll(month, year);
            boolean hasData = rows.stream().anyMatch(row -> row.soCong > 0);

            request.setAttribute("rows", rows);
            request.setAttribute("month", month);
            request.setAttribute("year", year);
            request.setAttribute("hasData", hasData);
            request.setAttribute("locked", dao.isLocked(month, year));
            request.getRequestDispatcher("/bang-luong.jsp").forward(request, response);
        } catch (Exception error) {
            throw new ServletException(error);
        }
    }
}
