package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Instructor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAO {

  public List<Instructor> getAll() {
    List<Instructor> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM instructors ORDER BY id")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Instructor getById(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM instructors WHERE id = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(Instructor i) {
    String sql =
        "INSERT INTO instructors (name, email, phone, title, specialization) VALUES (?,?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, i.getName());
      ps.setString(2, i.getEmail());
      ps.setString(3, i.getPhone());
      ps.setString(4, i.getTitle());
      ps.setString(5, i.getSpecialization());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Instructor i) {
    String sql =
        "UPDATE instructors SET name=?, email=?, phone=?, title=?, specialization=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, i.getName());
      ps.setString(2, i.getEmail());
      ps.setString(3, i.getPhone());
      ps.setString(4, i.getTitle());
      ps.setString(5, i.getSpecialization());
      ps.setInt(6, i.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM instructors WHERE id = ?")) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Instructor> search(String keyword) {
    List<Instructor> list = new ArrayList<>();
    String sql =
        "SELECT * FROM instructors WHERE name LIKE ? OR email LIKE ? OR specialization LIKE ? ORDER BY id";
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
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM instructors")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private Instructor mapRow(ResultSet rs) throws SQLException {
    Instructor i = new Instructor();
    i.setId(rs.getInt("id"));
    i.setName(rs.getString("name"));
    i.setEmail(rs.getString("email"));
    i.setPhone(rs.getString("phone"));
    i.setTitle(rs.getString("title"));
    i.setSpecialization(rs.getString("specialization"));
    return i;
  }
}
