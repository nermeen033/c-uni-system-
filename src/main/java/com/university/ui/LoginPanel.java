package com.university.ui;

import com.university.dao.StudentDAO;
import com.university.models.Student;
import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JFrame {
  private JTextField emailField;
  private JPasswordField passwordField;
  private JButton loginButton;

  public LoginPanel() {
    setTitle("University System - Login");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    initUI();
  }

  private void initUI() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(30, 30, 46));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel titleLabel = new JLabel("\uD83C\uDF93 UniSystem Login");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
    titleLabel.setForeground(new Color(137, 180, 250));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(10, 10, 30, 10);
    panel.add(titleLabel, gbc);

    gbc.insets = new Insets(5, 10, 5, 10);
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setForeground(new Color(205, 214, 244));
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    panel.add(emailLabel, gbc);

    emailField = new JTextField(20);
    gbc.gridx = 1;
    panel.add(emailField, gbc);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setForeground(new Color(205, 214, 244));
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(passLabel, gbc);

    passwordField = new JPasswordField(20);
    gbc.gridx = 1;
    panel.add(passwordField, gbc);

    loginButton = new JButton("Login");
    loginButton.setBackground(new Color(137, 180, 250));
    loginButton.setForeground(new Color(30, 30, 46));
    loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    loginButton.setFocusPainted(false);
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(25, 10, 10, 10);
    panel.add(loginButton, gbc);

    loginButton.addActionListener(e -> login());

    // Support hitting Enter
    getRootPane().setDefaultButton(loginButton);

    add(panel);
  }

  private void login() {
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());

    // Fast-path for testing the UI
    if ("admin".equals(email) && "admin".equals(password)) {
      openMainFrame();
      return;
    }

    StudentDAO dao = new StudentDAO();
    Student s = dao.authenticate(email, password);
    if (s != null) {
      openMainFrame();
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Invalid email or password.\nUse admin/admin for quick access.",
          "Login Failed",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void openMainFrame() {
    SwingUtilities.invokeLater(
        () -> {
          MainFrame frame = new MainFrame();
          frame.setVisible(true);
        });
    this.dispose();
  }
}
