package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

  public List<Course> getAll() {
    List<Course> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM courses ORDER BY code")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Course getByCode(String code) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM courses WHERE code = ?")) {
      ps.setString(1, code);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(Course c) {
    String sql =
        "INSERT INTO courses (code, name, credit_hours, instructor, department, semester, level, track, max_students, fees) VALUES (?,?,?,?,?,?,?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, c.getCode());
      ps.setString(2, c.getName());
      ps.setInt(3, c.getCreditHours());
      ps.setString(4, c.getInstructor());
      ps.setString(5, c.getDepartment());
      ps.setInt(6, c.getSemester());
      ps.setInt(7, c.getLevel());
      ps.setString(8, c.getTrack());
      ps.setInt(9, c.getMaxStudents());
      ps.setDouble(10, c.getFees());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Course c) {
    String sql =
        "UPDATE courses SET name=?, credit_hours=?, instructor=?, department=?, semester=?, level=?, track=?, max_students=?, fees=? WHERE code=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, c.getName());
      ps.setInt(2, c.getCreditHours());
      ps.setString(3, c.getInstructor());
      ps.setString(4, c.getDepartment());
      ps.setInt(5, c.getSemester());
      ps.setInt(6, c.getLevel());
      ps.setString(7, c.getTrack());
      ps.setInt(8, c.getMaxStudents());
      ps.setDouble(9, c.getFees());
      ps.setString(10, c.getCode());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(String code) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE code = ?")) {
      ps.setString(1, code);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Course> search(String keyword) {
    List<Course> list = new ArrayList<>();
    String sql =
        "SELECT * FROM courses WHERE code LIKE ? OR name LIKE ? OR instructor LIKE ? OR department LIKE ? ORDER BY code";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      String kw = "%" + keyword + "%";
      ps.setString(1, kw);
      ps.setString(2, kw);
      ps.setString(3, kw);
      ps.setString(4, kw);
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
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM courses")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private Course mapRow(ResultSet rs) throws SQLException {
    Course c = new Course();
    c.setCode(rs.getString("code"));
    c.setName(rs.getString("name"));
    c.setCreditHours(rs.getInt("credit_hours"));
    c.setInstructor(rs.getString("instructor"));
    c.setDepartment(rs.getString("department"));
    c.setSemester(rs.getInt("semester"));
    c.setLevel(rs.getInt("level"));
    c.setTrack(rs.getString("track"));
    c.setMaxStudents(rs.getInt("max_students"));
    c.setCurrentEnrolled(rs.getInt("current_enrolled"));
    c.setFees(rs.getDouble("fees"));
    return c;
  }
}
