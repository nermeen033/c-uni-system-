package com.university.models;

public class Course {
  private String code = "";
  private String name = "";
  private int creditHours = 3;
  private String instructor = "";
  private String department = "";
  private int semester = 1;
  private int level = 1;
  private String track = "General";
  private int maxStudents = 50;
  private int currentEnrolled;
  private double fees;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCreditHours() {
    return creditHours;
  }

  public void setCreditHours(int creditHours) {
    this.creditHours = creditHours;
  }

  public String getInstructor() {
    return instructor;
  }

  public void setInstructor(String instructor) {
    this.instructor = instructor;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getTrack() {
    return track;
  }

  public void setTrack(String track) {
    this.track = track;
  }

  public int getMaxStudents() {
    return maxStudents;
  }

  public void setMaxStudents(int maxStudents) {
    this.maxStudents = maxStudents;
  }

  public int getCurrentEnrolled() {
    return currentEnrolled;
  }

  public void setCurrentEnrolled(int currentEnrolled) {
    this.currentEnrolled = currentEnrolled;
  }

  public double getFees() {
    return fees;
  }

  public void setFees(double fees) {
    this.fees = fees;
  }

  @Override
  public String toString() {
    return code + " - " + name;
  }
}
