package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Classroom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO {

  public List<Classroom> getAll() {
    List<Classroom> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM classrooms ORDER BY id")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Classroom getById(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM classrooms WHERE id = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(Classroom c) {
    String sql = "INSERT INTO classrooms (code, building, capacity, room_type) VALUES (?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, c.getCode());
      ps.setString(2, c.getBuilding());
      ps.setInt(3, c.getCapacity());
      ps.setString(4, c.getRoomType());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Classroom c) {
    String sql = "UPDATE classrooms SET code=?, building=?, capacity=?, room_type=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, c.getCode());
      ps.setString(2, c.getBuilding());
      ps.setInt(3, c.getCapacity());
      ps.setString(4, c.getRoomType());
      ps.setInt(5, c.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM classrooms WHERE id = ?")) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Classroom> search(String keyword) {
    List<Classroom> list = new ArrayList<>();
    String sql =
        "SELECT * FROM classrooms WHERE code LIKE ? OR building LIKE ? OR room_type LIKE ? ORDER BY id";
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
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM classrooms")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private Classroom mapRow(ResultSet rs) throws SQLException {
    Classroom c = new Classroom();
    c.setId(rs.getInt("id"));
    c.setCode(rs.getString("code"));
    c.setBuilding(rs.getString("building"));
    c.setCapacity(rs.getInt("capacity"));
    c.setRoomType(rs.getString("room_type"));
    return c;
  }
}
