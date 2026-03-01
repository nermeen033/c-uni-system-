package com.university.ui;

import com.university.dao.GradeDAO;
import com.university.models.Grade;
import com.university.models.MercyCandidate;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GradesPanel extends BasePanel {
  private final GradeDAO dao = new GradeDAO();
  private JTable mercyTable;
  private DefaultTableModel mercyModel;

  public GradesPanel() {
    super(
        "📝 Grades",
        new String[] {
          "ID",
          "Student ID",
          "Course",
          "S1",
          "S2",
          "YearWork",
          "Final",
          "Total",
          "Grade",
          "Pearson",
          "Semester",
          "Year"
        });

    // Add mercy section
    JPanel mercySection = new JPanel(new BorderLayout(0, 5));
    mercySection.setBackground(BG);
    mercySection.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

    JPanel mercyHeader = new JPanel(new BorderLayout());
    mercyHeader.setBackground(BG);
    JLabel mercyTitle = new JLabel("🙏 Mercy Candidates (45-49 marks)");
    mercyTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
    mercyTitle.setForeground(YELLOW);
    mercyHeader.add(mercyTitle, BorderLayout.WEST);

    JButton applyAllBtn = createButton("✅ Apply Mercy to Selected", GREEN);
    applyAllBtn.addActionListener(e -> applyMercy());
    mercyHeader.add(applyAllBtn, BorderLayout.EAST);
    mercySection.add(mercyHeader, BorderLayout.NORTH);

    mercyModel =
        new DefaultTableModel(
            new String[] {"Grade ID", "Student", "Course", "Current", "Passing", "Diff"}, 0) {
          @Override
          public boolean isCellEditable(int r, int c) {
            return false;
          }
        };
    mercyTable = new JTable(mercyModel);
    mercyTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    mercyTable.setRowHeight(32);
    mercyTable.setBackground(BG);
    mercyTable.setForeground(TEXT);
    mercyTable.setSelectionBackground(new Color(137, 180, 250, 40));
    mercyTable.setGridColor(OVERLAY);

    JScrollPane mercyScroll = new JScrollPane(mercyTable);
    mercyScroll.setPreferredSize(new Dimension(0, 150));
    mercyScroll.setBackground(BG);
    mercyScroll.getViewport().setBackground(BG);
    mercyScroll.setBorder(BorderFactory.createLineBorder(OVERLAY, 1));
    mercySection.add(mercyScroll, BorderLayout.CENTER);

    add(mercySection, BorderLayout.SOUTH);

    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
    loadMercyCandidates();
  }

  private void loadData(List<Grade> list) {
    tableModel.setRowCount(0);
    for (Grade g : list) {
      tableModel.addRow(
          new Object[] {
            g.getId(),
            g.getStudentId(),
            g.getCourseCode(),
            g.getS1Score(),
            g.getS2Score(),
            g.getYearWorkScore(),
            g.getFinalExamScore(),
            g.getTotalScore(),
            g.getLetterGrade(),
            g.getPearsonGrade(),
            g.getSemester(),
            g.getAcademicYear()
          });
    }
    countLabel.setText("(" + list.size() + " records)");
  }

  private void loadMercyCandidates() {
    if (mercyModel == null) return;
    mercyModel.setRowCount(0);
    for (MercyCandidate mc : dao.getMercyCandidates()) {
      mercyModel.addRow(
          new Object[] {
            mc.getGradeId(), mc.getStudentName(), mc.getCourseName(),
            mc.getCurrentTotal(), mc.getPassingGrade(), String.format("%.1f", mc.getDifference())
          });
    }
  }

  private void applyMercy() {
    int row = mercyTable.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(
          this, "Please select a mercy candidate.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    int gradeId = (int) mercyModel.getValueAt(row, 0);
    if (dao.applyMercy(gradeId)) {
      JOptionPane.showMessageDialog(
          this, "Mercy applied successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      refreshData();
    }
  }

  @Override
  protected void onSearch(String keyword) {
    refreshData();
  }

  @Override
  protected void onAdd() {
    showForm(null);
  }

  @Override
  protected void onEdit(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    Grade g = dao.getById(id);
    if (g != null) showForm(g);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(Grade existing) {
    JDialog dialog = createStyledDialog(existing == null ? "Add Grade" : "Edit Grade", 450, 520);
    JPanel form = new JPanel(new GridLayout(0, 2, 10, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField studentIdField =
        new JTextField(String.valueOf(existing != null ? existing.getStudentId() : 0));
    JTextField courseField = new JTextField(existing != null ? existing.getCourseCode() : "");
    JTextField s1Field =
        new JTextField(String.valueOf(existing != null ? existing.getS1Score() : 0.0));
    JTextField s2Field =
        new JTextField(String.valueOf(existing != null ? existing.getS2Score() : 0.0));
    JTextField ywField =
        new JTextField(String.valueOf(existing != null ? existing.getYearWorkScore() : 0.0));
    JTextField finalField =
        new JTextField(String.valueOf(existing != null ? existing.getFinalExamScore() : 0.0));
    JTextField semField =
        new JTextField(String.valueOf(existing != null ? existing.getSemester() : 1));
    JTextField yearField =
        new JTextField(existing != null ? existing.getAcademicYear() : "2024-2025");

    form.add(createFormField("Student ID", studentIdField));
    form.add(createFormField("Course Code", courseField));
    form.add(createFormField("S1 Score", s1Field));
    form.add(createFormField("S2 Score", s2Field));
    form.add(createFormField("Year Work", ywField));
    form.add(createFormField("Final Exam", finalField));
    form.add(createFormField("Semester", semField));
    form.add(createFormField("Academic Year", yearField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          try {
            Grade g = existing != null ? existing : new Grade();
            g.setStudentId(Integer.parseInt(studentIdField.getText().trim()));
            g.setCourseCode(courseField.getText().trim());
            g.setS1Score(Double.parseDouble(s1Field.getText().trim()));
            g.setS2Score(Double.parseDouble(s2Field.getText().trim()));
            g.setYearWorkScore(Double.parseDouble(ywField.getText().trim()));
            g.setFinalExamScore(Double.parseDouble(finalField.getText().trim()));
            g.setSemester(Integer.parseInt(semField.getText().trim()));
            g.setAcademicYear(yearField.getText().trim());
            g.calculateTotal(); // auto-calculate total, letter, pearson
            boolean ok = existing != null ? dao.update(g) : dao.insert(g);
            if (ok) {
              refreshData();
              dialog.dispose();
            } else
              JOptionPane.showMessageDialog(
                  dialog, "Failed to save.", "Error", JOptionPane.ERROR_MESSAGE);
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                dialog, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        });
    cancelBtn.addActionListener(e -> dialog.dispose());
    btnPanel.add(cancelBtn);
    btnPanel.add(saveBtn);
    dialog.add(btnPanel, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }
}
