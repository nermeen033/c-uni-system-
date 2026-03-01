package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Faculty;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

  public List<Faculty> getAll() {
    List<Faculty> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM faculties ORDER BY id")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Faculty getById(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM faculties WHERE id = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(Faculty f) {
    String sql = "INSERT INTO faculties (name, name_ar, code) VALUES (?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, f.getName());
      ps.setString(2, f.getNameAr());
      ps.setString(3, f.getCode());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Faculty f) {
    String sql = "UPDATE faculties SET name=?, name_ar=?, code=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, f.getName());
      ps.setString(2, f.getNameAr());
      ps.setString(3, f.getCode());
      ps.setInt(4, f.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM faculties WHERE id = ?")) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Faculty> search(String keyword) {
    List<Faculty> list = new ArrayList<>();
    String sql =
        "SELECT * FROM faculties WHERE name LIKE ? OR name_ar LIKE ? OR code LIKE ? ORDER BY id";
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
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM faculties")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private Faculty mapRow(ResultSet rs) throws SQLException {
    Faculty f = new Faculty();
    f.setId(rs.getInt("id"));
    f.setName(rs.getString("name"));
    f.setNameAr(rs.getString("name_ar"));
    f.setCode(rs.getString("code"));
    return f;
  }
}
