package com.university.ui;

import com.university.dao.*;
import java.awt.*;
import javax.swing.*;

public class DashboardPanel extends BasePanel {
  private final StudentDAO studentDAO = new StudentDAO();
  private final InstructorDAO instructorDAO = new InstructorDAO();
  private final CourseDAO courseDAO = new CourseDAO();
  private final DepartmentDAO departmentDAO = new DepartmentDAO();
  private final FacultyDAO facultyDAO = new FacultyDAO();
  private final ClassroomDAO classroomDAO = new ClassroomDAO();
  private final GradeDAO gradeDAO = new GradeDAO();
  private final NewsDAO newsDAO = new NewsDAO();

  private JPanel cardsPanel;

  public DashboardPanel() {
    super("📊 Dashboard", new String[] {"Entity", "Count", "Status"});
    // Remove the default table for dashboard, we'll use cards
    removeAll();
    setLayout(new BorderLayout(0, 0));
    setBackground(BG);
    setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
    initDashboard();
  }

  private void initDashboard() {
    // Header
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(BG);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    JLabel header = new JLabel("📊 Dashboard Overview");
    header.setFont(new Font("Segoe UI", Font.BOLD, 26));
    header.setForeground(TEXT);
    headerPanel.add(header, BorderLayout.WEST);

    JLabel subtitle = new JLabel("University Management System Statistics");
    subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 13));
    subtitle.setForeground(SUBTEXT);
    headerPanel.add(subtitle, BorderLayout.SOUTH);
    add(headerPanel, BorderLayout.NORTH);

    // Stats cards
    cardsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
    cardsPanel.setBackground(BG);
    add(cardsPanel, BorderLayout.CENTER);

    refreshData();
  }

  @Override
  public void refreshData() {
    if (cardsPanel == null) return;
    cardsPanel.removeAll();

    cardsPanel.add(createStatCard("👨‍🎓", "Students", studentDAO.count(), ACCENT));
    cardsPanel.add(createStatCard("👨‍🏫", "Instructors", instructorDAO.count(), GREEN));
    cardsPanel.add(createStatCard("📚", "Courses", courseDAO.count(), YELLOW));
    cardsPanel.add(
        createStatCard("🏛️", "Departments", departmentDAO.count(), new Color(203, 166, 247)));
    cardsPanel.add(createStatCard("🏫", "Faculties", facultyDAO.count(), new Color(245, 194, 231)));
    cardsPanel.add(
        createStatCard("🏠", "Classrooms", classroomDAO.count(), new Color(148, 226, 213)));
    cardsPanel.add(createStatCard("📝", "Grades", gradeDAO.count(), new Color(250, 179, 135)));
    cardsPanel.add(createStatCard("📰", "News", newsDAO.count(), new Color(180, 190, 254)));

    cardsPanel.revalidate();
    cardsPanel.repaint();
  }

  private JPanel createStatCard(String icon, String title, int count, Color color) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(SURFACE);
    card.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(OVERLAY, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

    JLabel iconLabel = new JLabel(icon);
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
    iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.add(iconLabel);

    card.add(Box.createVerticalStrut(10));

    JLabel countLabel = new JLabel(String.valueOf(count));
    countLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
    countLabel.setForeground(color);
    countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.add(countLabel);

    card.add(Box.createVerticalStrut(5));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    titleLabel.setForeground(SUBTEXT);
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.add(titleLabel);

    return card;
  }

  @Override
  protected void onSearch(String keyword) {}

  @Override
  protected void onAdd() {}

  @Override
  protected void onEdit(int row) {}

  @Override
  protected void onDelete(int row) {}
}
