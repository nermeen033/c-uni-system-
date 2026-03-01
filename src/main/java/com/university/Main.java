package com.university;

import com.formdev.flatlaf.FlatDarkLaf;
import com.university.db.DatabaseManager;
import com.university.db.DatabaseSeeder;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    // Set FlatLaf Dark theme
    try {
      UIManager.setLookAndFeel(new FlatDarkLaf());
      UIManager.put("Button.arc", 8);
      UIManager.put("TextComponent.arc", 8);
      UIManager.put("Component.arc", 8);
    } catch (Exception e) {
      System.err.println("Failed to set FlatLaf theme: " + e.getMessage());
    }

    // Initialize database and seed sample data
    DatabaseManager.getInstance();
    DatabaseSeeder.seedIfEmpty();

    // Launch UI (Starts with Login)
    SwingUtilities.invokeLater(
        () -> {
          com.university.ui.LoginPanel loginPanel = new com.university.ui.LoginPanel();
          loginPanel.setVisible(true);
        });
  }
}
