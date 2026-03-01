package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

  public List<Department> getAll() {
    List<Department> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM departments ORDER BY id")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Department getById(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM departments WHERE id = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(Department d) {
    String sql =
        "INSERT INTO departments (name, name_ar, code, faculty_id, faculty_name) VALUES (?,?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, d.getName());
      ps.setString(2, d.getNameAr());
      ps.setString(3, d.getCode());
      ps.setInt(4, d.getFacultyId());
      ps.setString(5, d.getFacultyName());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Department d) {
    String sql =
        "UPDATE departments SET name=?, name_ar=?, code=?, faculty_id=?, faculty_name=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, d.getName());
      ps.setString(2, d.getNameAr());
      ps.setString(3, d.getCode());
      ps.setInt(4, d.getFacultyId());
      ps.setString(5, d.getFacultyName());
      ps.setInt(6, d.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM departments WHERE id = ?")) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Department> search(String keyword) {
    List<Department> list = new ArrayList<>();
    String sql =
        "SELECT * FROM departments WHERE name LIKE ? OR code LIKE ? OR faculty_name LIKE ? ORDER BY id";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      String kw = "%" + keyword + "%";
      ps.setString(1, kw);
      ps.setString(2, kw);
      ps.setString(3, kw);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public int count() {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM departments")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private Department mapRow(ResultSet rs) throws SQLException {
    Department d = new Department();
    d.setId(rs.getInt("id"));
    d.setName(rs.getString("name"));
    d.setNameAr(rs.getString("name_ar"));
    d.setCode(rs.getString("code"));
    d.setFacultyId(rs.getInt("faculty_id"));
    d.setFacultyName(rs.getString("faculty_name"));
    return d;
  }
}
