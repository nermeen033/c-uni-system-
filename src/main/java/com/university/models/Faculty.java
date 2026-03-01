package com.university.models;

public class Faculty {
  private int id;
  private String name = "";
  private String nameAr = "";
  private String code = "";

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

  public String getNameAr() {
    return nameAr;
  }

  public void setNameAr(String nameAr) {
    this.nameAr = nameAr;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
