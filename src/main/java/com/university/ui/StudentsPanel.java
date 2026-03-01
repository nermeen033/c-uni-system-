package com.university.ui;

import com.university.dao.StudentDAO;
import com.university.models.Student;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class StudentsPanel extends BasePanel {
  private final StudentDAO dao = new StudentDAO();

  public StudentsPanel() {
    super(
        "👨‍🎓 Students",
        new String[] {
          "ID",
          "Name",
          "Email",
          "Department",
          "Faculty",
          "Level",
          "Semester",
          "Track",
          "Seat#",
          "Balance"
        });
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<Student> list) {
    tableModel.setRowCount(0);
    for (Student s : list) {
      tableModel.addRow(
          new Object[] {
            s.getId(), s.getName(), s.getEmail(), s.getDepartment(),
            s.getFacultyName(), s.getLevel(), s.getSemester(), s.getTrack(),
            s.getSeatNumber(), String.format("%.2f", s.getBalance())
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
    int id = (int) tableModel.getValueAt(row, 0);
    Student s = dao.getById(id);
    if (s != null) showForm(s);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(Student existing) {
    JDialog dialog =
        createStyledDialog(existing == null ? "Add Student" : "Edit Student", 500, 580);
    JPanel form = new JPanel(new GridLayout(0, 2, 10, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField nameField = new JTextField(existing != null ? existing.getName() : "");
    JTextField emailField = new JTextField(existing != null ? existing.getEmail() : "");
    JTextField passwordField = new JTextField(existing != null ? existing.getPassword() : "123456");
    JTextField deptField = new JTextField(existing != null ? existing.getDepartment() : "");
    JTextField facultyField = new JTextField(existing != null ? existing.getFacultyName() : "");
    JTextField levelField =
        new JTextField(String.valueOf(existing != null ? existing.getLevel() : 1));
    JTextField semesterField =
        new JTextField(String.valueOf(existing != null ? existing.getSemester() : 1));
    JTextField trackField = new JTextField(existing != null ? existing.getTrack() : "General");
    JTextField seatField =
        new JTextField(String.valueOf(existing != null ? existing.getSeatNumber() : 0));
    JTextField enrollField =
        new JTextField(existing != null ? existing.getEnrollmentYear() : "2024");
    JTextField phoneField = new JTextField(existing != null ? existing.getPhone() : "");
    JTextField addressField = new JTextField(existing != null ? existing.getAddress() : "");
    JTextField balanceField =
        new JTextField(String.valueOf(existing != null ? existing.getBalance() : 0.0));

    form.add(createFormField("Name", nameField));
    form.add(createFormField("Email", emailField));
    form.add(createFormField("Password", passwordField));
    form.add(createFormField("Department", deptField));
    form.add(createFormField("Faculty", facultyField));
    form.add(createFormField("Level", levelField));
    form.add(createFormField("Semester", semesterField));
    form.add(createFormField("Track", trackField));
    form.add(createFormField("Seat Number", seatField));
    form.add(createFormField("Enrollment Year", enrollField));
    form.add(createFormField("Phone", phoneField));
    form.add(createFormField("Address", addressField));
    form.add(createFormField("Balance", balanceField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          try {
            Student s = existing != null ? existing : new Student();
            s.setName(nameField.getText().trim());
            s.setEmail(emailField.getText().trim());
            s.setPassword(passwordField.getText().trim());
            s.setDepartment(deptField.getText().trim());
            s.setFacultyName(facultyField.getText().trim());
            s.setLevel(Integer.parseInt(levelField.getText().trim()));
            s.setSemester(Integer.parseInt(semesterField.getText().trim()));
            s.setTrack(trackField.getText().trim());
            s.setSeatNumber(Integer.parseInt(seatField.getText().trim()));
            s.setEnrollmentYear(enrollField.getText().trim());
            s.setPhone(phoneField.getText().trim());
            s.setAddress(addressField.getText().trim());
            s.setBalance(Double.parseDouble(balanceField.getText().trim()));

            boolean ok = existing != null ? dao.update(s) : dao.insert(s);
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
