package com.university.ui;

import com.university.dao.FacultyDAO;
import com.university.models.Faculty;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FacultiesPanel extends BasePanel {
  private final FacultyDAO dao = new FacultyDAO();

  public FacultiesPanel() {
    super("🏫 Faculties", new String[] {"ID", "Name", "Name (AR)", "Code"});
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<Faculty> list) {
    tableModel.setRowCount(0);
    for (Faculty f : list) {
      tableModel.addRow(new Object[] {f.getId(), f.getName(), f.getNameAr(), f.getCode()});
    }
    countLabel.setText("(" + list.size() + " records)");
  }

  @Override
  protected void onSearch(String keyword) {
    loadData(dao.getAll()); // Faculty has no search, just reload
  }

  @Override
  protected void onAdd() {
    showForm(null);
  }

  @Override
  protected void onEdit(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    Faculty f = dao.getById(id);
    if (f != null) showForm(f);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(Faculty existing) {
    JDialog dialog =
        createStyledDialog(existing == null ? "Add Faculty" : "Edit Faculty", 400, 300);
    JPanel form = new JPanel(new GridLayout(0, 1, 8, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField nameField = new JTextField(existing != null ? existing.getName() : "");
    JTextField nameArField = new JTextField(existing != null ? existing.getNameAr() : "");
    JTextField codeField = new JTextField(existing != null ? existing.getCode() : "");

    form.add(createFormField("Name", nameField));
    form.add(createFormField("Name (Arabic)", nameArField));
    form.add(createFormField("Code", codeField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          Faculty f = existing != null ? existing : new Faculty();
          f.setName(nameField.getText().trim());
          f.setNameAr(nameArField.getText().trim());
          f.setCode(codeField.getText().trim());
          boolean ok = existing != null ? dao.update(f) : dao.insert(f);
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
