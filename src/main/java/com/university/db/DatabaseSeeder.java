package com.university.db;

import java.sql.*;

public class DatabaseSeeder {

  public static void seedIfEmpty() {
    try (Connection conn = DatabaseManager.getInstance().getConnection()) {
      if (isTableEmpty(conn, "faculties")) {
        seedFaculties(conn);
        seedDepartments(conn);
        seedStudents(conn);
        seedInstructors(conn);
        seedCourses(conn);
        seedClassrooms(conn);
        seedGrades(conn);
        seedNews(conn);
        System.out.println("✓ Sample data seeded successfully.");
      }
    } catch (SQLException e) {
      System.err.println("Error seeding data: " + e.getMessage());
    }
  }

  private static boolean isTableEmpty(Connection conn, String table) throws SQLException {
    try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table)) {
      return rs.getInt(1) == 0;
    }
  }

  private static void seedFaculties(Connection conn) throws SQLException {
    String sql = "INSERT INTO faculties (name, name_ar, code) VALUES (?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      String[][] data = {
        {"Faculty of Computer Science", "كلية علوم الحاسب", "FCS"},
        {"Faculty of Engineering", "كلية الهندسة", "FEN"},
        {"Faculty of Business", "كلية إدارة الأعمال", "FBA"},
        {"Faculty of Arts", "كلية الآداب", "FAR"},
        {"Faculty of Science", "كلية العلوم", "FSC"}
      };
      for (String[] row : data) {
        ps.setString(1, row[0]);
        ps.setString(2, row[1]);
        ps.setString(3, row[2]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedDepartments(Connection conn) throws SQLException {
    String sql =
        "INSERT INTO departments (name, name_ar, code, faculty_id, faculty_name) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      String[][] data = {
        {"Computer Science", "علوم الحاسب", "CS", "1", "Faculty of Computer Science"},
        {"Information Technology", "تكنولوجيا المعلومات", "IT", "1", "Faculty of Computer Science"},
        {"Software Engineering", "هندسة البرمجيات", "SE", "1", "Faculty of Computer Science"},
        {"Civil Engineering", "الهندسة المدنية", "CE", "2", "Faculty of Engineering"},
        {"Electrical Engineering", "الهندسة الكهربائية", "EE", "2", "Faculty of Engineering"},
        {"Accounting", "المحاسبة", "ACC", "3", "Faculty of Business"},
        {"Marketing", "التسويق", "MKT", "3", "Faculty of Business"}
      };
      for (String[] row : data) {
        ps.setString(1, row[0]);
        ps.setString(2, row[1]);
        ps.setString(3, row[2]);
        ps.setInt(4, Integer.parseInt(row[3]));
        ps.setString(5, row[4]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedStudents(Connection conn) throws SQLException {
    String sql =
        "INSERT INTO students (name, email, password, department, faculty_name, level, semester, track, seat_number, enrollment_year, phone, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      Object[][] data = {
        {
          "Ahmed Mohamed",
          "ahmed@uni.edu",
          "123456",
          "Computer Science",
          "Faculty of Computer Science",
          2,
          1,
          "General",
          101,
          "2023",
          "01012345678",
          5000.0
        },
        {
          "Sara Ali",
          "sara@uni.edu",
          "123456",
          "Information Technology",
          "Faculty of Computer Science",
          1,
          1,
          "General",
          102,
          "2024",
          "01098765432",
          4500.0
        },
        {
          "Omar Hassan",
          "omar@uni.edu",
          "123456",
          "Software Engineering",
          "Faculty of Computer Science",
          3,
          2,
          "AI",
          103,
          "2022",
          "01155566677",
          3000.0
        },
        {
          "Fatma Ibrahim",
          "fatma@uni.edu",
          "123456",
          "Civil Engineering",
          "Faculty of Engineering",
          2,
          1,
          "General",
          201,
          "2023",
          "01233344455",
          6000.0
        },
        {
          "Youssef Khaled",
          "youssef@uni.edu",
          "123456",
          "Accounting",
          "Faculty of Business",
          1,
          1,
          "General",
          301,
          "2024",
          "01566677788",
          4000.0
        },
        {
          "Nour Ahmed",
          "nour@uni.edu",
          "123456",
          "Computer Science",
          "Faculty of Computer Science",
          4,
          2,
          "Cybersecurity",
          104,
          "2021",
          "01277788899",
          2000.0
        },
        {
          "Mina Samir",
          "mina@uni.edu",
          "123456",
          "Electrical Engineering",
          "Faculty of Engineering",
          2,
          1,
          "General",
          202,
          "2023",
          "01099900011",
          5500.0
        },
        {
          "Layla Mostafa",
          "layla@uni.edu",
          "123456",
          "Marketing",
          "Faculty of Business",
          3,
          1,
          "Digital Marketing",
          302,
          "2022",
          "01144455566",
          3500.0
        }
      };
      for (Object[] row : data) {
        ps.setString(1, (String) row[0]);
        ps.setString(2, (String) row[1]);
        ps.setString(3, (String) row[2]);
        ps.setString(4, (String) row[3]);
        ps.setString(5, (String) row[4]);
        ps.setInt(6, (int) row[5]);
        ps.setInt(7, (int) row[6]);
        ps.setString(8, (String) row[7]);
        ps.setInt(9, (int) row[8]);
        ps.setString(10, (String) row[9]);
        ps.setString(11, (String) row[10]);
        ps.setDouble(12, (double) row[11]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedInstructors(Connection conn) throws SQLException {
    String sql =
        "INSERT INTO instructors (name, email, phone, title, specialization) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      String[][] data = {
        {
          "Dr. Mohamed Fathy",
          "m.fathy@uni.edu",
          "01001112233",
          "Professor",
          "Artificial Intelligence"
        },
        {
          "Dr. Amira Saeed",
          "a.saeed@uni.edu",
          "01004445566",
          "Associate Professor",
          "Database Systems"
        },
        {"Dr. Khalid Nabil", "k.nabil@uni.edu", "01007778899", "Assistant Professor", "Networks"},
        {"Dr. Heba Yasser", "h.yasser@uni.edu", "01002223344", "Lecturer", "Software Engineering"},
        {"Dr. Tarek Mansour", "t.mansour@uni.edu", "01005556677", "Professor", "Data Science"}
      };
      for (String[] row : data) {
        ps.setString(1, row[0]);
        ps.setString(2, row[1]);
        ps.setString(3, row[2]);
        ps.setString(4, row[3]);
        ps.setString(5, row[4]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedCourses(Connection conn) throws SQLException {
    String sql =
        "INSERT INTO courses (code, name, credit_hours, instructor, department, semester, level, track, max_students, fees) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      Object[][] data = {
        {
          "CS101",
          "Introduction to Programming",
          3,
          "Dr. Mohamed Fathy",
          "Computer Science",
          1,
          1,
          "General",
          60,
          1500.0
        },
        {
          "CS201",
          "Data Structures",
          3,
          "Dr. Amira Saeed",
          "Computer Science",
          1,
          2,
          "General",
          50,
          1500.0
        },
        {
          "CS301",
          "Algorithms",
          3,
          "Dr. Mohamed Fathy",
          "Computer Science",
          1,
          3,
          "General",
          45,
          1800.0
        },
        {
          "IT101",
          "IT Fundamentals",
          3,
          "Dr. Khalid Nabil",
          "Information Technology",
          1,
          1,
          "General",
          55,
          1200.0
        },
        {
          "SE201",
          "Software Design",
          3,
          "Dr. Heba Yasser",
          "Software Engineering",
          1,
          2,
          "General",
          40,
          1600.0
        },
        {
          "CS401",
          "Machine Learning",
          3,
          "Dr. Tarek Mansour",
          "Computer Science",
          2,
          4,
          "AI",
          35,
          2000.0
        },
        {
          "CS402",
          "Cybersecurity Fundamentals",
          3,
          "Dr. Khalid Nabil",
          "Computer Science",
          2,
          4,
          "Cybersecurity",
          30,
          2000.0
        },
        {
          "CE101",
          "Engineering Drawing",
          2,
          "Dr. Tarek Mansour",
          "Civil Engineering",
          1,
          1,
          "General",
          50,
          1000.0
        }
      };
      for (Object[] row : data) {
        ps.setString(1, (String) row[0]);
        ps.setString(2, (String) row[1]);
        ps.setInt(3, (int) row[2]);
        ps.setString(4, (String) row[3]);
        ps.setString(5, (String) row[4]);
        ps.setInt(6, (int) row[5]);
        ps.setInt(7, (int) row[6]);
        ps.setString(8, (String) row[7]);
        ps.setInt(9, (int) row[8]);
        ps.setDouble(10, (double) row[9]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedClassrooms(Connection conn) throws SQLException {
    String sql = "INSERT INTO classrooms (code, building, capacity, room_type) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      Object[][] data = {
        {"A101", "Building A", 60, "Lecture Hall"},
        {"A102", "Building A", 40, "Lab"},
        {"B201", "Building B", 100, "Lecture Hall"},
        {"B202", "Building B", 30, "Lab"},
        {"C301", "Building C", 50, "Seminar Room"},
        {"C302", "Building C", 25, "Computer Lab"}
      };
      for (Object[] row : data) {
        ps.setString(1, (String) row[0]);
        ps.setString(2, (String) row[1]);
        ps.setInt(3, (int) row[2]);
        ps.setString(4, (String) row[3]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedGrades(Connection conn) throws SQLException {
    String sql =
        "INSERT INTO grades (student_id, course_code, s1_score, s2_score, year_work_score, final_exam_score, total_score, letter_grade, pearson_grade, semester, academic_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      Object[][] data = {
        {1, "CS101", 18.0, 17.0, 25.0, 30.0, 90.0, "A+", "D", 1, "2023-2024"},
        {1, "CS201", 15.0, 14.0, 20.0, 25.0, 74.0, "C+", "M", 1, "2024-2025"},
        {2, "IT101", 19.0, 18.0, 28.0, 28.0, 93.0, "A+", "D", 1, "2024-2025"},
        {3, "CS301", 10.0, 12.0, 15.0, 12.0, 49.0, "F", "F", 1, "2024-2025"},
        {4, "CE101", 16.0, 15.0, 22.0, 20.0, 73.0, "C+", "M", 1, "2023-2024"},
        {6, "CS402", 17.0, 16.0, 24.0, 26.0, 83.0, "A", "D", 2, "2024-2025"}
      };
      for (Object[] row : data) {
        ps.setInt(1, (int) row[0]);
        ps.setString(2, (String) row[1]);
        ps.setDouble(3, (double) row[2]);
        ps.setDouble(4, (double) row[3]);
        ps.setDouble(5, (double) row[4]);
        ps.setDouble(6, (double) row[5]);
        ps.setDouble(7, (double) row[6]);
        ps.setString(8, (String) row[7]);
        ps.setString(9, (String) row[8]);
        ps.setInt(10, (int) row[9]);
        ps.setString(11, (String) row[10]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private static void seedNews(Connection conn) throws SQLException {
    String sql =
        "INSERT INTO news (title, content, author, publish_date, category, pinned, active, target_department) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      Object[][] data = {
        {
          "Registration Open for Spring 2025",
          "Spring semester registration is now open. Students can register through the system.",
          "Admin",
          "2025-01-15",
          1,
          1,
          1,
          ""
        },
        {
          "Exam Schedule Published",
          "Final exam schedule for Fall 2024 has been published. Check your department board.",
          "Academic Affairs",
          "2024-12-01",
          2,
          0,
          1,
          ""
        },
        {
          "AI Workshop Announcement",
          "Free AI workshop for CS students on Feb 20. Register now!",
          "Dr. Mohamed Fathy",
          "2025-02-10",
          3,
          1,
          1,
          "Computer Science"
        },
        {
          "Library Hours Extended",
          "Library will be open 24/7 during exam period.",
          "Library Admin",
          "2024-11-25",
          1,
          0,
          1,
          ""
        }
      };
      for (Object[] row : data) {
        ps.setString(1, (String) row[0]);
        ps.setString(2, (String) row[1]);
        ps.setString(3, (String) row[2]);
        ps.setString(4, (String) row[3]);
        ps.setInt(5, (int) row[4]);
        ps.setInt(6, (int) row[5]);
        ps.setInt(7, (int) row[6]);
        ps.setString(8, (String) row[7]);
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }
}
