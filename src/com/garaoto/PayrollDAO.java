package com.garaoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {
    public List<PayrollRow> getPayroll(int month, int year) throws SQLException {
        String sql = "SELECT nv.id, nv.ma_nv, nv.ho_ten, nv.luong_cung, nv.don_gia_cong, "
                + "COUNT(p.id) AS so_cong, COALESCE(b.thuong, 0) AS thuong, "
                + "COALESCE(b.phat, 0) AS phat, COALESCE(b.tam_ung, 0) AS tam_ung "
                + "FROM nhan_vien nv "
                + "LEFT JOIN phieu_tiep_nhan p ON p.nhan_vien_id = nv.id "
                + "AND p.trang_thai = 'Hoàn thành' "
                + "AND MONTH(p.ngay_hoan_thanh) = ? AND YEAR(p.ngay_hoan_thanh) = ? "
                + "LEFT JOIN bang_luong b ON b.nhan_vien_id = nv.id "
                + "AND b.thang = ? AND b.nam = ? "
                + "GROUP BY nv.id, nv.ma_nv, nv.ho_ten, nv.luong_cung, nv.don_gia_cong, "
                + "b.thuong, b.phat, b.tam_ung ORDER BY nv.ma_nv";

        List<PayrollRow> rows = new ArrayList<>();
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, month);
            statement.setInt(2, year);
            statement.setInt(3, month);
            statement.setInt(4, year);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                PayrollRow row = new PayrollRow();
                row.id = result.getInt("id");
                row.maNv = result.getString("ma_nv");
                row.hoTen = result.getString("ho_ten");
                row.luongCung = result.getDouble("luong_cung");
                row.donGiaCong = result.getDouble("don_gia_cong");
                row.soCong = result.getInt("so_cong");
                row.thuong = result.getDouble("thuong");
                row.phat = result.getDouble("phat");
                row.tamUng = result.getDouble("tam_ung");
                rows.add(row);
            }
        }
        return rows;
    }

    public boolean isLocked(int month, int year) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bang_luong WHERE thang = ? AND nam = ? AND da_chot = 1";
        try (Connection connection = DB.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, month);
            statement.setInt(2, year);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1) > 0;
        }
    }

    public PayrollRow getEmployeePayroll(int employeeId, int month, int year) throws SQLException {
        for (PayrollRow row : getPayroll(month, year)) {
            if (row.id == employeeId) return row;
        }
        return null;
    }

    public void updateAdjustments(int employeeId, int month, int year, double bonus, double fine, double advance) throws SQLException {
        if (isLocked(month, year)) throw new SQLException("Bảng lương đã chốt.");
        PayrollRow row = getEmployeePayroll(employeeId, month, year);
        if (row == null) throw new SQLException("Không tìm thấy nhân viên.");

        row.thuong = bonus;
        row.phat = fine;
        row.tamUng = advance;
        saveRow(row, month, year, false);
    }

    public void lockMonth(int month, int year) throws SQLException {
        if (isLocked(month, year)) return;
        try (Connection connection = DB.getConnection()) {
            connection.setAutoCommit(false);
            for (PayrollRow row : getPayroll(month, year)) saveRow(row, month, year, true);
            connection.commit();
        }
    }

    private void saveRow(PayrollRow row, int month, int year, boolean lock) throws SQLException {
        String sql = "INSERT INTO bang_luong (nhan_vien_id, thang, nam, so_cong, tien_cong, thuong, phat, tam_ung, tong_luong, da_chot) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE so_cong = VALUES(so_cong), tien_cong = VALUES(tien_cong), "
                + "thuong = VALUES(thuong), phat = VALUES(phat), tam_ung = VALUES(tam_ung), "
                + "tong_luong = VALUES(tong_luong), da_chot = VALUES(da_chot)";
        try (Connection connection = DB.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, row.id); statement.setInt(2, month); statement.setInt(3, year);
            statement.setInt(4, row.soCong); statement.setDouble(5, row.tienCong());
            statement.setDouble(6, row.thuong); statement.setDouble(7, row.phat);
            statement.setDouble(8, row.tamUng); statement.setDouble(9, row.tongLuong());
            statement.setBoolean(10, lock); statement.executeUpdate();
        }
    }
}
