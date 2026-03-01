package com.university.ui;

import com.university.dao.NewsDAO;
import com.university.models.News;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class NewsPanel extends BasePanel {
  private final NewsDAO dao = new NewsDAO();

  public NewsPanel() {
    super(
        "📰 News",
        new String[] {
          "ID", "Title", "Author", "Date", "Category", "Pinned", "Active", "Department"
        });
    refreshData();
  }

  @Override
  public void refreshData() {
    loadData(dao.getAll());
  }

  private void loadData(List<News> list) {
    tableModel.setRowCount(0);
    for (News n : list) {
      String cat =
          n.getCategory() == 1
              ? "General"
              : n.getCategory() == 2 ? "Academic" : n.getCategory() == 3 ? "Event" : "Other";
      tableModel.addRow(
          new Object[] {
            n.getId(),
            n.getTitle(),
            n.getAuthor(),
            n.getPublishDate(),
            cat,
            n.isPinned() ? "📌 Yes" : "No",
            n.isActive() ? "✅ Yes" : "❌ No",
            n.getTargetDepartment().isEmpty() ? "All" : n.getTargetDepartment()
          });
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
    News n = dao.getById(id);
    if (n != null) showForm(n);
  }

  @Override
  protected void onDelete(int row) {
    int id = (int) tableModel.getValueAt(row, 0);
    if (dao.delete(id)) refreshData();
  }

  private void showForm(News existing) {
    JDialog dialog = createStyledDialog(existing == null ? "Add News" : "Edit News", 500, 520);
    JPanel form = new JPanel(new GridLayout(0, 1, 8, 4));
    form.setBackground(SURFACE);
    form.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JTextField titleField = new JTextField(existing != null ? existing.getTitle() : "");
    JTextArea contentArea = new JTextArea(existing != null ? existing.getContent() : "", 3, 20);
    contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    contentArea.setBackground(BG);
    contentArea.setForeground(TEXT);
    contentArea.setCaretColor(TEXT);
    contentArea.setLineWrap(true);
    contentArea.setWrapStyleWord(true);
    contentArea.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(OVERLAY, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    JTextField authorField = new JTextField(existing != null ? existing.getAuthor() : "Admin");
    JTextField dateField =
        new JTextField(existing != null ? existing.getPublishDate() : LocalDate.now().toString());
    JComboBox<String> catBox =
        new JComboBox<>(new String[] {"General", "Academic", "Event", "Other"});
    if (existing != null) catBox.setSelectedIndex(Math.max(0, existing.getCategory() - 1));
    JCheckBox pinnedBox = new JCheckBox("Pinned", existing != null && existing.isPinned());
    pinnedBox.setBackground(SURFACE);
    pinnedBox.setForeground(TEXT);
    JCheckBox activeBox = new JCheckBox("Active", existing == null || existing.isActive());
    activeBox.setBackground(SURFACE);
    activeBox.setForeground(TEXT);
    JTextField deptField = new JTextField(existing != null ? existing.getTargetDepartment() : "");

    form.add(createFormField("Title", titleField));

    JPanel contentPanel = new JPanel(new BorderLayout(0, 4));
    contentPanel.setBackground(SURFACE);
    JLabel contentLbl = new JLabel("Content");
    contentLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
    contentLbl.setForeground(TEXT);
    contentPanel.add(contentLbl, BorderLayout.NORTH);
    JScrollPane contentScroll = new JScrollPane(contentArea);
    contentScroll.setBorder(BorderFactory.createEmptyBorder());
    contentPanel.add(contentScroll, BorderLayout.CENTER);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
    form.add(contentPanel);

    form.add(createFormField("Author", authorField));
    form.add(createFormField("Publish Date", dateField));

    JPanel catPanel = new JPanel(new BorderLayout(0, 4));
    catPanel.setBackground(SURFACE);
    JLabel catLbl = new JLabel("Category");
    catLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
    catLbl.setForeground(TEXT);
    catPanel.add(catLbl, BorderLayout.NORTH);
    catPanel.add(catBox, BorderLayout.CENTER);
    catPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
    form.add(catPanel);

    JPanel checksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
    checksPanel.setBackground(SURFACE);
    checksPanel.add(pinnedBox);
    checksPanel.add(activeBox);
    form.add(checksPanel);

    form.add(createFormField("Target Department (empty = All)", deptField));

    dialog.add(form, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    btnPanel.setBackground(SURFACE);
    JButton saveBtn = createButton("💾 Save", ACCENT);
    JButton cancelBtn = createButton("Cancel", OVERLAY);
    cancelBtn.setForeground(TEXT);

    saveBtn.addActionListener(
        e -> {
          News n = existing != null ? existing : new News();
          n.setTitle(titleField.getText().trim());
          n.setContent(contentArea.getText().trim());
          n.setAuthor(authorField.getText().trim());
          n.setPublishDate(dateField.getText().trim());
          n.setCategory(catBox.getSelectedIndex() + 1);
          n.setPinned(pinnedBox.isSelected());
          n.setActive(activeBox.isSelected());
          n.setTargetDepartment(deptField.getText().trim());
          boolean ok = existing != null ? dao.update(n) : dao.insert(n);
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
