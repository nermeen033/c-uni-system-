package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

  public List<Student> getAll() {
    List<Student> list = new ArrayList<>();
    String sql = "SELECT * FROM students ORDER BY id";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Student getById(int id) {
    String sql = "SELECT * FROM students WHERE id = ?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Student authenticate(String email, String password) {
    String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, password);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(Student s) {
    String sql =
        "INSERT INTO students (name, email, password, department, faculty_name, level, semester, track, seat_number, enrollment_year, phone, address, balance) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, s.getName());
      ps.setString(2, s.getEmail());
      ps.setString(3, s.getPassword());
      ps.setString(4, s.getDepartment());
      ps.setString(5, s.getFacultyName());
      ps.setInt(6, s.getLevel());
      ps.setInt(7, s.getSemester());
      ps.setString(8, s.getTrack());
      ps.setInt(9, s.getSeatNumber());
      ps.setString(10, s.getEnrollmentYear());
      ps.setString(11, s.getPhone());
      ps.setString(12, s.getAddress());
      ps.setDouble(13, s.getBalance());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Student s) {
    String sql =
        "UPDATE students SET name=?, email=?, password=?, department=?, faculty_name=?, level=?, semester=?, track=?, seat_number=?, enrollment_year=?, phone=?, address=?, balance=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, s.getName());
      ps.setString(2, s.getEmail());
      ps.setString(3, s.getPassword());
      ps.setString(4, s.getDepartment());
      ps.setString(5, s.getFacultyName());
      ps.setInt(6, s.getLevel());
      ps.setInt(7, s.getSemester());
      ps.setString(8, s.getTrack());
      ps.setInt(9, s.getSeatNumber());
      ps.setString(10, s.getEnrollmentYear());
      ps.setString(11, s.getPhone());
      ps.setString(12, s.getAddress());
      ps.setDouble(13, s.getBalance());
      ps.setInt(14, s.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    String sql = "DELETE FROM students WHERE id = ?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Student> search(String keyword) {
    List<Student> list = new ArrayList<>();
    String sql =
        "SELECT * FROM students WHERE name LIKE ? OR email LIKE ? OR department LIKE ? ORDER BY id";
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
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private Student mapRow(ResultSet rs) throws SQLException {
    Student s = new Student();
    s.setId(rs.getInt("id"));
    s.setName(rs.getString("name"));
    s.setEmail(rs.getString("email"));
    s.setPassword(rs.getString("password"));
    s.setDepartment(rs.getString("department"));
    s.setFacultyName(rs.getString("faculty_name"));
    s.setLevel(rs.getInt("level"));
    s.setSemester(rs.getInt("semester"));
    s.setTrack(rs.getString("track"));
    s.setSeatNumber(rs.getInt("seat_number"));
    s.setEnrollmentYear(rs.getString("enrollment_year"));
    s.setPhone(rs.getString("phone"));
    s.setAddress(rs.getString("address"));
    s.setBalance(rs.getDouble("balance"));
    return s;
  }
}
