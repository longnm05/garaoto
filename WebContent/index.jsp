<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Chọn kỳ lương</title>
  <link rel="stylesheet" href="assets/style.css">
</head>
<body>
  <main class="page"><section class="panel narrow-panel">
    <h1>Chọn thời gian tính lương</h1>
    <% if (request.getParameter("error") != null) { %><p class="notice">Vui lòng chọn tháng và năm hợp lệ.</p><% } %>
    <form action="payroll" method="get">
      <div class="form-row"><label>Tháng:</label><select name="month" required><option value="">-- Chọn tháng --</option><% for (int i = 1; i <= 12; i++) { %><option value="<%= i %>">Tháng <%= i %></option><% } %></select></div>
      <div class="form-row"><label>Năm:</label><input name="year" type="number" value="2026" required></div>
      <div class="actions"><button type="submit">Xem bảng lương</button></div>
    </form>
  </section></main>
</body>
</html>
