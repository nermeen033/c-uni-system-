package com.university.ui;

import com.university.dao.CourseDAO;
import com.university.models.Course;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class CoursesPanel extends BasePanel {
  private final CourseDAO dao = new CourseDAO();

  public CoursesPanel() {
    super(
        "📚 Courses",
        new String[] {
          "Code",
          "Name",
          "Credits",
          "Instructor",
          "Department",
          "Semester",
          "Level",
          "Track",
          "Max",
          "Enrolled",
          "Fees"
        });
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<Course> list) {
    tableModel.setRowCount(0);
    for (Course c : list) {
      tableModel.addRow(
          new Object[] {
            c.getCode(), c.getName(), c.getCreditHours(), c.getInstructor(), c.getDepartment(),
            c.getSemester(), c.getLevel(), c.getTrack(), c.getMaxStudents(), c.getCurrentEnrolled(),
            String.format("%.2f", c.getFees())
          });
    }
    countLabel.setText("(" + list.size() + " records)");
  }

  @Override
  protected void onSearch(String keyword) {
    if (keyword.isEmpty()) refreshData();
    else loadData(dao.search(keyword));
  }

  @Override
  protected void onAdd() {
    showForm(null);
  }

  @Override
  protected void onEdit(int row) {
    String code = (String) tableModel.getValueAt(row, 0);
    Course c = dao.getByCode(code);
    if (c != null) showForm(c);
  }

  @Override
  protected void onDelete(int row) {
    String code = (String) tableModel.getValueAt(row, 0);
    if (dao.delete(code)) refreshData();
  }

  private void showForm(Course existing) {
    JDialog dialog = createStyledDialog(existing == null ? "Add Course" : "Edit Course", 480, 520);
    JPanel form = new JPanel(new GridLayout(0, 2, 10, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField codeField = new JTextField(existing != null ? existing.getCode() : "");
    if (existing != null) codeField.setEditable(false);
    JTextField nameField = new JTextField(existing != null ? existing.getName() : "");
    JTextField creditsField =
        new JTextField(String.valueOf(existing != null ? existing.getCreditHours() : 3));
    JTextField instrField = new JTextField(existing != null ? existing.getInstructor() : "");
    JTextField deptField = new JTextField(existing != null ? existing.getDepartment() : "");
    JTextField semField =
        new JTextField(String.valueOf(existing != null ? existing.getSemester() : 1));
    JTextField levelField =
        new JTextField(String.valueOf(existing != null ? existing.getLevel() : 1));
    JTextField trackField = new JTextField(existing != null ? existing.getTrack() : "General");
    JTextField maxField =
        new JTextField(String.valueOf(existing != null ? existing.getMaxStudents() : 50));
    JTextField feesField =
        new JTextField(String.valueOf(existing != null ? existing.getFees() : 0.0));

    form.add(createFormField("Code", codeField));
    form.add(createFormField("Name", nameField));
    form.add(createFormField("Credit Hours", creditsField));
    form.add(createFormField("Instructor", instrField));
    form.add(createFormField("Department", deptField));
    form.add(createFormField("Semester", semField));
    form.add(createFormField("Level", levelField));
    form.add(createFormField("Track", trackField));
    form.add(createFormField("Max Students", maxField));
    form.add(createFormField("Fees", feesField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          try {
            Course c = existing != null ? existing : new Course();
            if (existing == null) c.setCode(codeField.getText().trim());
            c.setName(nameField.getText().trim());
            c.setCreditHours(Integer.parseInt(creditsField.getText().trim()));
            c.setInstructor(instrField.getText().trim());
            c.setDepartment(deptField.getText().trim());
            c.setSemester(Integer.parseInt(semField.getText().trim()));
            c.setLevel(Integer.parseInt(levelField.getText().trim()));
            c.setTrack(trackField.getText().trim());
            c.setMaxStudents(Integer.parseInt(maxField.getText().trim()));
            c.setFees(Double.parseDouble(feesField.getText().trim()));
            boolean ok = existing != null ? dao.update(c) : dao.insert(c);
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
