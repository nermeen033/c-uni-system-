package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.News;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

  public List<News> getAll() {
    List<News> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM news ORDER BY pinned DESC, id DESC")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public News getById(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM news WHERE id = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insert(News n) {
    String sql =
        "INSERT INTO news (title, content, author, publish_date, category, pinned, active, target_department) VALUES (?,?,?,?,?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, n.getTitle());
      ps.setString(2, n.getContent());
      ps.setString(3, n.getAuthor());
      ps.setString(4, n.getPublishDate());
      ps.setInt(5, n.getCategory());
      ps.setInt(6, n.isPinned() ? 1 : 0);
      ps.setInt(7, n.isActive() ? 1 : 0);
      ps.setString(8, n.getTargetDepartment());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(News n) {
    String sql =
        "UPDATE news SET title=?, content=?, author=?, publish_date=?, category=?, pinned=?, active=?, target_department=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, n.getTitle());
      ps.setString(2, n.getContent());
      ps.setString(3, n.getAuthor());
      ps.setString(4, n.getPublishDate());
      ps.setInt(5, n.getCategory());
      ps.setInt(6, n.isPinned() ? 1 : 0);
      ps.setInt(7, n.isActive() ? 1 : 0);
      ps.setString(8, n.getTargetDepartment());
      ps.setInt(9, n.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM news WHERE id = ?")) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<News> search(String keyword) {
    List<News> list = new ArrayList<>();
    String sql =
        "SELECT * FROM news WHERE title LIKE ? OR content LIKE ? OR author LIKE ? ORDER BY pinned DESC, id DESC";
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
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM news")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private News mapRow(ResultSet rs) throws SQLException {
    News n = new News();
    n.setId(rs.getInt("id"));
    n.setTitle(rs.getString("title"));
    n.setContent(rs.getString("content"));
    n.setAuthor(rs.getString("author"));
    n.setPublishDate(rs.getString("publish_date"));
    n.setCategory(rs.getInt("category"));
    n.setPinned(rs.getInt("pinned") == 1);
    n.setActive(rs.getInt("active") == 1);
    n.setTargetDepartment(rs.getString("target_department"));
    return n;
  }
}
