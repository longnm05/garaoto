<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.garaoto.PayrollRow" %>
<%
    List<PayrollRow> payrollRows = (List<PayrollRow>) request.getAttribute("rows");
    int month = (Integer) request.getAttribute("month");
    int year = (Integer) request.getAttribute("year");
    boolean hasData = (Boolean) request.getAttribute("hasData");
    boolean locked = (Boolean) request.getAttribute("locked");
    double totalPayroll = 0;

    for (PayrollRow row : payrollRows) {
        totalPayroll += row.tongLuong();
    }
%>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng lương</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<main class="page">
    <section class="panel">
        <h2>Bảng lương nhân viên phục vụ</h2>
        <p>Tháng: <b><%= month %></b></p>
        <p>Năm: <b><%= year %></b></p>
        <p>Trạng thái: <b><%= locked ? "Đã chốt" : "Chưa chốt" %></b></p>

        <% if (!hasData) { %>
            <p class="notice">Không có phiếu hoàn thành trong kỳ này.</p>
        <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>Mã NV</th>
                        <th>Họ tên</th>
                        <th>Số công</th>
                        <th>Tiền công</th>
                        <th>Tổng lương</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (PayrollRow row : payrollRows) { %>
                        <tr>
                            <td><%= row.maNv %></td>
                            <td><%= row.hoTen %></td>
                            <td><%= row.soCong %></td>
                            <td><%= String.format("%,.0f", row.tienCong()) %></td>
                            <td><%= String.format("%,.0f", row.tongLuong()) %></td>
                            <td>
                                <% if (locked) { %>
                                    Đã chốt
                                <% } else { %>
                                    <a class="button" href="detail?id=<%= row.id %>&month=<%= month %>&year=<%= year %>">Chi tiết</a>
                                <% } %>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="4">TỔNG TIỀN LƯƠNG</th>
                        <th colspan="2"><%= String.format("%,.0f", totalPayroll) %> VND</th>
                    </tr>
                </tfoot>
            </table>
        <% } %>

        <div class="actions">
            <a class="button" href="index.jsp">Quay lại</a>
            <% if (hasData && !locked) { %>
                <form method="post" action="lock">
                    <input type="hidden" name="month" value="<%= month %>">
                    <input type="hidden" name="year" value="<%= year %>">
                    <button type="submit">Lưu và chốt bảng lương</button>
                </form>
            <% } %>
        </div>
    </section>
</main>
</body>
</html>
