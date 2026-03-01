package com.university.ui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel contentPanel;
  private JPanel sidebarPanel;
  private String currentCard = "dashboard";

  // Colors
  private static final Color SIDEBAR_BG = new Color(30, 30, 46);
  private static final Color SIDEBAR_HOVER = new Color(49, 50, 68);
  private static final Color SIDEBAR_ACTIVE = new Color(137, 180, 250);
  private static final Color ACCENT = new Color(137, 180, 250);
  private static final Color TEXT_COLOR = new Color(205, 214, 244);
  private static final Color TEXT_DIM = new Color(147, 153, 178);

  public MainFrame() {
    setTitle("University Management System");
    setSize(1280, 800);
    setMinimumSize(new Dimension(1024, 600));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    initUI();
  }

  private void initUI() {
    setLayout(new BorderLayout());

    // Sidebar
    sidebarPanel = createSidebar();
    add(sidebarPanel, BorderLayout.WEST);

    // Content area
    cardLayout = new CardLayout();
    contentPanel = new JPanel(cardLayout);
    contentPanel.setBackground(new Color(30, 30, 46));

    contentPanel.add(new DashboardPanel(), "dashboard");
    contentPanel.add(new StudentsPanel(), "students");
    contentPanel.add(new InstructorsPanel(), "instructors");
    contentPanel.add(new CoursesPanel(), "courses");
    contentPanel.add(new DepartmentsPanel(), "departments");
    contentPanel.add(new FacultiesPanel(), "faculties");
    contentPanel.add(new ClassroomsPanel(), "classrooms");
    contentPanel.add(new GradesPanel(), "grades");
    contentPanel.add(new NewsPanel(), "news");

    add(contentPanel, BorderLayout.CENTER);

    cardLayout.show(contentPanel, "dashboard");
  }

  private JPanel createSidebar() {
    JPanel sidebar = new JPanel();
    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
    sidebar.setBackground(SIDEBAR_BG);
    sidebar.setPreferredSize(new Dimension(220, 0));
    sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(49, 50, 68)));

    // Logo area
    JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    logoPanel.setBackground(SIDEBAR_BG);
    logoPanel.setMaximumSize(new Dimension(220, 70));
    logoPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
    JLabel logo = new JLabel("\uD83C\uDF93 UniSystem");
    logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
    logo.setForeground(ACCENT);
    logoPanel.add(logo);
    sidebar.add(logoPanel);

    sidebar.add(Box.createVerticalStrut(10));

    // Separator
    JSeparator sep = new JSeparator();
    sep.setMaximumSize(new Dimension(200, 1));
    sep.setForeground(new Color(49, 50, 68));
    sidebar.add(sep);
    sidebar.add(Box.createVerticalStrut(10));

    // Menu items
    String[][] menuItems = {
      {"📊", "Dashboard", "dashboard"},
      {"👨‍🎓", "Students", "students"},
      {"👨‍🏫", "Instructors", "instructors"},
      {"📚", "Courses", "courses"},
      {"🏛️", "Departments", "departments"},
      {"🏫", "Faculties", "faculties"},
      {"🏠", "Classrooms", "classrooms"},
      {"📝", "Grades", "grades"},
      {"📰", "News", "news"}
    };

    for (String[] item : menuItems) {
      JButton btn = createNavButton(item[0], item[1], item[2]);
      sidebar.add(btn);
      sidebar.add(Box.createVerticalStrut(3));
    }

    sidebar.add(Box.createVerticalGlue());

    // Footer
    JLabel version = new JLabel("v1.0.0");
    version.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    version.setForeground(TEXT_DIM);
    version.setAlignmentX(Component.CENTER_ALIGNMENT);
    version.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
    sidebar.add(version);

    return sidebar;
  }

  private JButton createNavButton(String icon, String text, String cardName) {
    JButton btn = new JButton(icon + "  " + text);
    btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    btn.setForeground(TEXT_COLOR);
    btn.setBackground(SIDEBAR_BG);
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setOpaque(true);
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btn.setMaximumSize(new Dimension(220, 40));
    btn.setPreferredSize(new Dimension(220, 40));
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 10));

    if (cardName.equals("dashboard")) {
      btn.setBackground(SIDEBAR_HOVER);
      btn.setForeground(ACCENT);
    }

    btn.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent e) {
            if (!currentCard.equals(cardName)) {
              btn.setBackground(SIDEBAR_HOVER);
            }
          }

          public void mouseExited(java.awt.event.MouseEvent e) {
            if (!currentCard.equals(cardName)) {
              btn.setBackground(SIDEBAR_BG);
            }
          }
        });

    btn.addActionListener(
        e -> {
          // Reset all buttons
          for (Component c : sidebarPanel.getComponents()) {
            if (c instanceof JButton) {
              c.setBackground(SIDEBAR_BG);
              c.setForeground(TEXT_COLOR);
            }
          }
          // Highlight active
          btn.setBackground(SIDEBAR_HOVER);
          btn.setForeground(ACCENT);
          currentCard = cardName;
          cardLayout.show(contentPanel, cardName);

          // Refresh panel data
          for (Component comp : contentPanel.getComponents()) {
            if (comp.isVisible() && comp instanceof BasePanel) {
              ((BasePanel) comp).refreshData();
            }
          }
        });

    return btn;
  }
}
