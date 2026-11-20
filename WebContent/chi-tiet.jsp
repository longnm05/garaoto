<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.garaoto.PayrollRow" %>
<%@ page import="java.util.Locale" %>
<%
    PayrollRow row = (PayrollRow) request.getAttribute("row");
    int month = (Integer) request.getAttribute("month");
    int year = (Integer) request.getAttribute("year");
    boolean locked = (Boolean) request.getAttribute("locked");
%>
<%!
    String money(double value) {
        return String.format(new Locale("vi", "VN"), "%,.0f", value);
    }
%>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết lương</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<main class="page">
    <section class="panel narrow-panel">
        <h1>Bảng lương chi tiết nhân viên</h1>
        <p>Mã NV: <b><%= row.maNv %></b></p>
        <p>Họ tên: <%= row.hoTen %></p>
        <p>Trạng thái: <b><%= locked ? "Đã chốt" : "Chưa chốt" %></b></p>

        <form method="post" action="update">
            <input type="hidden" name="id" value="<%= row.id %>">
            <input type="hidden" name="month" value="<%= month %>">
            <input type="hidden" name="year" value="<%= year %>">

            <table>
                <tr><th>Lương cứng</th><td><%= money(row.luongCung) %></td></tr>
                <tr><th>Số công</th><td><%= row.soCong %></td></tr>
                <tr><th>Tiền công</th><td><%= money(row.tienCong()) %></td></tr>
                <tr><th>Thưởng</th><td><input name="thuong" type="number" step="1000" value="<%= String.format("%.0f", row.thuong) %>" <%= locked ? "disabled" : "" %>></td></tr>
                <tr><th>Phạt</th><td><input name="phat" type="number" step="1000" value="<%= String.format("%.0f", row.phat) %>" <%= locked ? "disabled" : "" %>></td></tr>
                <tr><th>Tạm ứng</th><td><input name="tamUng" type="number" step="1000" value="<%= String.format("%.0f", row.tamUng) %>" <%= locked ? "disabled" : "" %>></td></tr>
                <tr><th>Tổng lương</th><td><%= money(row.tongLuong()) %></td></tr>
            </table>

            <div class="actions">
                <a class="button" href="payroll?month=<%= month %>&year=<%= year %>">Quay lại</a>
                <% if (!locked) { %><button type="submit">Cập nhật</button><% } %>
            </div>
        </form>
    </section>
</main>
</body>
</html>
