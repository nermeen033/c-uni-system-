package com.university.models;

public class News {
  private int id;
  private String title = "";
  private String content = "";
  private String author = "";
  private String publishDate = "";
  private int category;
  private boolean pinned;
  private boolean active = true;
  private String targetDepartment = "";

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public boolean isPinned() {
    return pinned;
  }

  public void setPinned(boolean pinned) {
    this.pinned = pinned;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getTargetDepartment() {
    return targetDepartment;
  }

  public void setTargetDepartment(String targetDepartment) {
    this.targetDepartment = targetDepartment;
  }
}
