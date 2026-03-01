package com.university.models;

public class Grade {
  private int id;
  private int studentId;
  private String courseCode = "";
  private double s1Score;
  private double s2Score;
  private double yearWorkScore;
  private double finalExamScore;
  private double totalScore;
  private String letterGrade = "";
  private String pearsonGrade = "";
  private int semester;
  private String academicYear = "";

  public void calculateTotal() {
    totalScore = s1Score + s2Score + yearWorkScore + finalExamScore;
    calculateLetterGrade();
    calculatePearsonGrade();
  }

  public void calculateLetterGrade() {
    if (totalScore >= 90) letterGrade = "A+";
    else if (totalScore >= 85) letterGrade = "A";
    else if (totalScore >= 80) letterGrade = "B+";
    else if (totalScore >= 75) letterGrade = "B";
    else if (totalScore >= 70) letterGrade = "C+";
    else if (totalScore >= 65) letterGrade = "C";
    else if (totalScore >= 60) letterGrade = "D+";
    else if (totalScore >= 50) letterGrade = "D";
    else letterGrade = "F";
  }

  public void calculatePearsonGrade() {
    if (totalScore >= 80) pearsonGrade = "D";
    else if (totalScore >= 65) pearsonGrade = "M";
    else if (totalScore >= 50) pearsonGrade = "P";
    else pearsonGrade = "F";
  }

  public String getPearsonDescription() {
    if ("D".equals(pearsonGrade)) return "Distinction (\u0627\u0645\u062A\u064A\u0627\u0632)";
    if ("M".equals(pearsonGrade)) return "Merit (\u062C\u064A\u062F \u062C\u062F\u0627\u064B)";
    if ("P".equals(pearsonGrade)) return "Pass (\u0646\u0627\u062C\u062D)";
    return "Fail (\u0631\u0627\u0633\u0628)";
  }

  public double getGPA() {
    if ("A+".equals(letterGrade)) return 4.0;
    if ("A".equals(letterGrade)) return 3.7;
    if ("B+".equals(letterGrade)) return 3.3;
    if ("B".equals(letterGrade)) return 3.0;
    if ("C+".equals(letterGrade)) return 2.7;
    if ("C".equals(letterGrade)) return 2.3;
    if ("D+".equals(letterGrade)) return 2.0;
    if ("D".equals(letterGrade)) return 1.0;
    return 0.0;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public double getS1Score() {
    return s1Score;
  }

  public void setS1Score(double s1Score) {
    this.s1Score = s1Score;
  }

  public double getS2Score() {
    return s2Score;
  }

  public void setS2Score(double s2Score) {
    this.s2Score = s2Score;
  }

  public double getYearWorkScore() {
    return yearWorkScore;
  }

  public void setYearWorkScore(double yearWorkScore) {
    this.yearWorkScore = yearWorkScore;
  }

  public double getFinalExamScore() {
    return finalExamScore;
  }

  public void setFinalExamScore(double finalExamScore) {
    this.finalExamScore = finalExamScore;
  }

  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }

  public String getLetterGrade() {
    return letterGrade;
  }

  public void setLetterGrade(String letterGrade) {
    this.letterGrade = letterGrade;
  }

  public String getPearsonGrade() {
    return pearsonGrade;
  }

  public void setPearsonGrade(String pearsonGrade) {
    this.pearsonGrade = pearsonGrade;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public String getAcademicYear() {
    return academicYear;
  }

  public void setAcademicYear(String academicYear) {
    this.academicYear = academicYear;
  }
}
