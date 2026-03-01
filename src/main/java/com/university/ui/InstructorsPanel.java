package com.university.ui;

import com.university.dao.InstructorDAO;
import com.university.models.Instructor;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class InstructorsPanel extends BasePanel {
  private final InstructorDAO dao = new InstructorDAO();

  public InstructorsPanel() {
    super(
        "👨‍🏫 Instructors",
        new String[] {"ID", "Name", "Email", "Phone", "Title", "Specialization"});
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<Instructor> list) {
    tableModel.setRowCount(0);
    for (Instructor i : list) {
      tableModel.addRow(
          new Object[] {
            i.getId(), i.getName(), i.getEmail(), i.getPhone(), i.getTitle(), i.getSpecialization()
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
    Instructor i = dao.getById(id);
    if (i != null) showForm(i);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(Instructor existing) {
    JDialog dialog =
        createStyledDialog(existing == null ? "Add Instructor" : "Edit Instructor", 420, 380);
    JPanel form = new JPanel(new GridLayout(0, 1, 8, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField nameField = new JTextField(existing != null ? existing.getName() : "");
    JTextField emailField = new JTextField(existing != null ? existing.getEmail() : "");
    JTextField phoneField = new JTextField(existing != null ? existing.getPhone() : "");
    JTextField titleField = new JTextField(existing != null ? existing.getTitle() : "");
    JTextField specField = new JTextField(existing != null ? existing.getSpecialization() : "");

    form.add(createFormField("Name", nameField));
    form.add(createFormField("Email", emailField));
    form.add(createFormField("Phone", phoneField));
    form.add(createFormField("Title", titleField));
    form.add(createFormField("Specialization", specField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          Instructor i = existing != null ? existing : new Instructor();
          i.setName(nameField.getText().trim());
          i.setEmail(emailField.getText().trim());
          i.setPhone(phoneField.getText().trim());
          i.setTitle(titleField.getText().trim());
          i.setSpecialization(specField.getText().trim());
          boolean ok = existing != null ? dao.update(i) : dao.insert(i);
          if (ok) {
            refreshData();
            dialog.dispose();
          } else
            JOptionPane.showMessageDialog(
                dialog, "Failed to save.", "Error", JOptionPane.ERROR_MESSAGE);
        });
    cancelBtn.addActionListener(e -> dialog.dispose());
    btnPanel.add(cancelBtn);
    btnPanel.add(saveBtn);
    dialog.add(btnPanel, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }
}
