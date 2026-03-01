package com.university.db;

import java.sql.*;

public class DatabaseManager {
  private static DatabaseManager instance;
  private static final String DB_URL = "jdbc:sqlite:university.db";

  private DatabaseManager() {
    createTables();
  }

  public static synchronized DatabaseManager getInstance() {
    if (instance == null) {
      instance = new DatabaseManager();
    }
    return instance;
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  private void createTables() {
    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement()) {

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS faculties ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "name TEXT NOT NULL,"
              + "name_ar TEXT DEFAULT '',"
              + "code TEXT DEFAULT ''"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS departments ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "name TEXT NOT NULL,"
              + "name_ar TEXT DEFAULT '',"
              + "code TEXT DEFAULT '',"
              + "faculty_id INTEGER DEFAULT 0,"
              + "faculty_name TEXT DEFAULT '',"
              + "FOREIGN KEY(faculty_id) REFERENCES faculties(id)"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS students ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "name TEXT NOT NULL,"
              + "email TEXT UNIQUE NOT NULL,"
              + "password TEXT NOT NULL,"
              + "department TEXT DEFAULT '',"
              + "faculty_name TEXT DEFAULT '',"
              + "level INTEGER DEFAULT 1,"
              + "semester INTEGER DEFAULT 1,"
              + "track TEXT DEFAULT 'General',"
              + "seat_number INTEGER DEFAULT 0,"
              + "enrollment_year TEXT DEFAULT '',"
              + "phone TEXT DEFAULT '',"
              + "address TEXT DEFAULT '',"
              + "balance REAL DEFAULT 0.0,"
              + "s1 REAL DEFAULT 0.0,"
              + "s2 REAL DEFAULT 0.0,"
              + "final_exam REAL DEFAULT 0.0"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS instructors ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "name TEXT NOT NULL,"
              + "email TEXT DEFAULT '',"
              + "phone TEXT DEFAULT '',"
              + "title TEXT DEFAULT '',"
              + "specialization TEXT DEFAULT ''"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS courses ("
              + "code TEXT PRIMARY KEY,"
              + "name TEXT NOT NULL,"
              + "credit_hours INTEGER DEFAULT 3,"
              + "instructor TEXT DEFAULT '',"
              + "department TEXT DEFAULT '',"
              + "semester INTEGER DEFAULT 1,"
              + "level INTEGER DEFAULT 1,"
              + "track TEXT DEFAULT 'General',"
              + "max_students INTEGER DEFAULT 50,"
              + "current_enrolled INTEGER DEFAULT 0,"
              + "fees REAL DEFAULT 0.0"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS classrooms ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "code TEXT DEFAULT '',"
              + "building TEXT DEFAULT '',"
              + "capacity INTEGER DEFAULT 0,"
              + "room_type TEXT DEFAULT 'Hall'"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS grades ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "student_id INTEGER NOT NULL,"
              + "course_code TEXT NOT NULL,"
              + "s1_score REAL DEFAULT 0.0,"
              + "s2_score REAL DEFAULT 0.0,"
              + "year_work_score REAL DEFAULT 0.0,"
              + "final_exam_score REAL DEFAULT 0.0,"
              + "total_score REAL DEFAULT 0.0,"
              + "letter_grade TEXT DEFAULT '',"
              + "pearson_grade TEXT DEFAULT '',"
              + "semester INTEGER DEFAULT 1,"
              + "academic_year TEXT DEFAULT '',"
              + "FOREIGN KEY(student_id) REFERENCES students(id),"
              + "FOREIGN KEY(course_code) REFERENCES courses(code)"
              + ")");

      stmt.execute(
          "CREATE TABLE IF NOT EXISTS news ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "title TEXT NOT NULL,"
              + "content TEXT DEFAULT '',"
              + "author TEXT DEFAULT '',"
              + "publish_date TEXT DEFAULT '',"
              + "category INTEGER DEFAULT 0,"
              + "pinned INTEGER DEFAULT 0,"
              + "active INTEGER DEFAULT 1,"
              + "target_department TEXT DEFAULT ''"
              + ")");

      System.out.println("✓ Database tables created successfully.");

    } catch (SQLException e) {
      System.err.println("Error creating tables: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
