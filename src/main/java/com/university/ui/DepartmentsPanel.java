package com.university.ui;

import com.university.dao.DepartmentDAO;
import com.university.models.Department;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class DepartmentsPanel extends BasePanel {
  private final DepartmentDAO dao = new DepartmentDAO();

  public DepartmentsPanel() {
    super(
        "🏛️ Departments",
        new String[] {"ID", "Name", "Name (AR)", "Code", "Faculty ID", "Faculty Name"});
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<Department> list) {
    tableModel.setRowCount(0);
    for (Department d : list) {
      tableModel.addRow(
          new Object[] {
            d.getId(), d.getName(), d.getNameAr(), d.getCode(), d.getFacultyId(), d.getFacultyName()
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
    Department d = dao.getById(id);
    if (d != null) showForm(d);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(Department existing) {
    JDialog dialog =
        createStyledDialog(existing == null ? "Add Department" : "Edit Department", 420, 400);
    JPanel form = new JPanel(new GridLayout(0, 1, 8, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField nameField = new JTextField(existing != null ? existing.getName() : "");
    JTextField nameArField = new JTextField(existing != null ? existing.getNameAr() : "");
    JTextField codeField = new JTextField(existing != null ? existing.getCode() : "");
    JTextField facIdField =
        new JTextField(String.valueOf(existing != null ? existing.getFacultyId() : 0));
    JTextField facNameField = new JTextField(existing != null ? existing.getFacultyName() : "");

    form.add(createFormField("Name", nameField));
    form.add(createFormField("Name (Arabic)", nameArField));
    form.add(createFormField("Code", codeField));
    form.add(createFormField("Faculty ID", facIdField));
    form.add(createFormField("Faculty Name", facNameField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          try {
            Department d = existing != null ? existing : new Department();
            d.setName(nameField.getText().trim());
            d.setNameAr(nameArField.getText().trim());
            d.setCode(codeField.getText().trim());
            d.setFacultyId(Integer.parseInt(facIdField.getText().trim()));
            d.setFacultyName(facNameField.getText().trim());
            boolean ok = existing != null ? dao.update(d) : dao.insert(d);
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
