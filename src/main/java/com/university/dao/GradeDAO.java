package com.university.dao;

import com.university.db.DatabaseManager;
import com.university.models.Grade;
import com.university.models.MercyCandidate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

  public List<Grade> getAll() {
    List<Grade> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM grades ORDER BY id")) {
      while (rs.next()) list.add(mapRow(rs));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public Grade getById(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM grades WHERE id = ?")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return mapRow(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Grade> getByStudentId(int studentId) {
    List<Grade> list = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps =
            conn.prepareStatement("SELECT * FROM grades WHERE student_id = ? ORDER BY id")) {
      ps.setInt(1, studentId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(mapRow(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean insert(Grade g) {
    String sql =
        "INSERT INTO grades (student_id, course_code, s1_score, s2_score, year_work_score, final_exam_score, total_score, letter_grade, pearson_grade, semester, academic_year) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, g.getStudentId());
      ps.setString(2, g.getCourseCode());
      ps.setDouble(3, g.getS1Score());
      ps.setDouble(4, g.getS2Score());
      ps.setDouble(5, g.getYearWorkScore());
      ps.setDouble(6, g.getFinalExamScore());
      ps.setDouble(7, g.getTotalScore());
      ps.setString(8, g.getLetterGrade());
      ps.setString(9, g.getPearsonGrade());
      ps.setInt(10, g.getSemester());
      ps.setString(11, g.getAcademicYear());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean update(Grade g) {
    String sql =
        "UPDATE grades SET student_id=?, course_code=?, s1_score=?, s2_score=?, year_work_score=?, final_exam_score=?, total_score=?, letter_grade=?, pearson_grade=?, semester=?, academic_year=? WHERE id=?";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, g.getStudentId());
      ps.setString(2, g.getCourseCode());
      ps.setDouble(3, g.getS1Score());
      ps.setDouble(4, g.getS2Score());
      ps.setDouble(5, g.getYearWorkScore());
      ps.setDouble(6, g.getFinalExamScore());
      ps.setDouble(7, g.getTotalScore());
      ps.setString(8, g.getLetterGrade());
      ps.setString(9, g.getPearsonGrade());
      ps.setInt(10, g.getSemester());
      ps.setString(11, g.getAcademicYear());
      ps.setInt(12, g.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean delete(int id) {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM grades WHERE id = ?")) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public int count() {
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM grades")) {
      return rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /** Get students who failed but are close to passing (within 5 marks of 50) */
  public List<MercyCandidate> getMercyCandidates() {
    List<MercyCandidate> list = new ArrayList<>();
    String sql =
        "SELECT g.id, g.student_id, s.name AS student_name, c.name AS course_name, "
            + "g.total_score, 50.0 AS passing_grade, (50.0 - g.total_score) AS difference "
            + "FROM grades g "
            + "JOIN students s ON g.student_id = s.id "
            + "JOIN courses c ON g.course_code = c.code "
            + "WHERE g.total_score < 50 AND g.total_score >= 45 "
            + "ORDER BY g.total_score DESC";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        MercyCandidate mc = new MercyCandidate();
        mc.setGradeId(rs.getInt("id"));
        mc.setStudentId(rs.getInt("student_id"));
        mc.setStudentName(rs.getString("student_name"));
        mc.setCourseName(rs.getString("course_name"));
        mc.setCurrentTotal(rs.getDouble("total_score"));
        mc.setPassingGrade(rs.getDouble("passing_grade"));
        mc.setDifference(rs.getDouble("difference"));
        mc.setApplied(false);
        list.add(mc);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  /** Apply mercy: set total to 50 and recalculate grade */
  public boolean applyMercy(int gradeId) {
    String sql =
        "UPDATE grades SET total_score = 50.0, letter_grade = 'D', pearson_grade = 'P' WHERE id = ? AND total_score >= 45 AND total_score < 50";
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, gradeId);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private Grade mapRow(ResultSet rs) throws SQLException {
    Grade g = new Grade();
    g.setId(rs.getInt("id"));
    g.setStudentId(rs.getInt("student_id"));
    g.setCourseCode(rs.getString("course_code"));
    g.setS1Score(rs.getDouble("s1_score"));
    g.setS2Score(rs.getDouble("s2_score"));
    g.setYearWorkScore(rs.getDouble("year_work_score"));
    g.setFinalExamScore(rs.getDouble("final_exam_score"));
    g.setTotalScore(rs.getDouble("total_score"));
    g.setLetterGrade(rs.getString("letter_grade"));
    g.setPearsonGrade(rs.getString("pearson_grade"));
    g.setSemester(rs.getInt("semester"));
    g.setAcademicYear(rs.getString("academic_year"));
    return g;
  }
}
