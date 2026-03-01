package com.university.models;

public class MercyCandidate {
  private int gradeId;
  private int studentId;
  private String studentName = "";
  private String courseName = "";
  private double currentTotal;
  private double passingGrade;
  private double difference;
  private boolean applied;

  public int getGradeId() {
    return gradeId;
  }

  public void setGradeId(int gradeId) {
    this.gradeId = gradeId;
  }

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public double getCurrentTotal() {
    return currentTotal;
  }

  public void setCurrentTotal(double currentTotal) {
    this.currentTotal = currentTotal;
  }

  public double getPassingGrade() {
    return passingGrade;
  }

  public void setPassingGrade(double passingGrade) {
    this.passingGrade = passingGrade;
  }

  public double getDifference() {
    return difference;
  }

  public void setDifference(double difference) {
    this.difference = difference;
  }

  public boolean isApplied() {
    return applied;
  }

  public void setApplied(boolean applied) {
    this.applied = applied;
  }
}
