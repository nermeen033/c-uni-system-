package com.university.ui;

import com.university.dao.ClassroomDAO;
import com.university.models.Classroom;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ClassroomsPanel extends BasePanel {
  private final ClassroomDAO dao = new ClassroomDAO();

  public ClassroomsPanel() {
    super("🏠 Classrooms", new String[] {"ID", "Code", "Building", "Capacity", "Room Type"});
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<Classroom> list) {
    tableModel.setRowCount(0);
    for (Classroom c : list) {
      tableModel.addRow(
          new Object[] {c.getId(), c.getCode(), c.getBuilding(), c.getCapacity(), c.getRoomType()});
    }
    countLabel.setText("(" + list.size() + " records)");
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
    Classroom c = dao.getById(id);
    if (c != null) showForm(c);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(Classroom existing) {
    JDialog dialog =
        createStyledDialog(existing == null ? "Add Classroom" : "Edit Classroom", 400, 340);
    JPanel form = new JPanel(new GridLayout(0, 1, 8, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField codeField = new JTextField(existing != null ? existing.getCode() : "");
    JTextField buildingField = new JTextField(existing != null ? existing.getBuilding() : "");
    JTextField capacityField =
        new JTextField(String.valueOf(existing != null ? existing.getCapacity() : 0));
    JTextField typeField = new JTextField(existing != null ? existing.getRoomType() : "Hall");

    form.add(createFormField("Code", codeField));
    form.add(createFormField("Building", buildingField));
    form.add(createFormField("Capacity", capacityField));
    form.add(createFormField("Room Type", typeField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          try {
            Classroom c = existing != null ? existing : new Classroom();
            c.setCode(codeField.getText().trim());
            c.setBuilding(buildingField.getText().trim());
            c.setCapacity(Integer.parseInt(capacityField.getText().trim()));
            c.setRoomType(typeField.getText().trim());
            boolean ok = existing != null ? dao.update(c) : dao.insert(c);
            if (ok) {
              refreshData();
              dialog.dispose();
            } else
              JOptionPane.showMessageDialog(
                  dialog, "Failed to save.", "Error", JOptionPane.ERROR_MESSAGE);
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                dialog, "Please enter a valid capacity.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        });
    cancelBtn.addActionListener(e -> dialog.dispose());
    btnPanel.add(cancelBtn);
    btnPanel.add(saveBtn);
    dialog.add(btnPanel, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }
}
