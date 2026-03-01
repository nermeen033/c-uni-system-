package com.university.models;

public class Student {
  private int id;
  private String name = "";
  private String email = "";
  private String password = "";
  private String department = "";
  private String facultyName = "";
  private int level = 1;
  private int semester = 1;
  private String track = "General";
  private int seatNumber;
  private String enrollmentYear = "";
  private String phone = "";
  private String address = "";
  private double balance;
  private double s1;
  private double s2;
  private double finalExam;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getFacultyName() {
    return facultyName;
  }

  public void setFacultyName(String facultyName) {
    this.facultyName = facultyName;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public String getTrack() {
    return track;
  }

  public void setTrack(String track) {
    this.track = track;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

  public String getEnrollmentYear() {
    return enrollmentYear;
  }

  public void setEnrollmentYear(String enrollmentYear) {
    this.enrollmentYear = enrollmentYear;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public double getS1() {
    return s1;
  }

  public void setS1(double s1) {
    this.s1 = s1;
  }

  public double getS2() {
    return s2;
  }

  public void setS2(double s2) {
    this.s2 = s2;
  }

  public double getFinalExam() {
    return finalExam;
  }

  public void setFinalExam(double finalExam) {
    this.finalExam = finalExam;
  }

  @Override
  public String toString() {
    return name + " (" + email + ")";
  }
}
