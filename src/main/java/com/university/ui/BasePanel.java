package com.university.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/** Base panel with common styling and layout for all entity panels. */
public abstract class BasePanel extends JPanel {
  protected JTable table;
  protected DefaultTableModel tableModel;
  protected JTextField searchField;
  protected JLabel titleLabel;
  protected JLabel countLabel;

  // Catppuccin Mocha theme colors
  protected static final Color BG = new Color(30, 30, 46);
  protected static final Color SURFACE = new Color(36, 39, 58);
  protected static final Color OVERLAY = new Color(49, 50, 68);
  protected static final Color TEXT = new Color(205, 214, 244);
  protected static final Color SUBTEXT = new Color(147, 153, 178);
  protected static final Color ACCENT = new Color(137, 180, 250);
  protected static final Color GREEN = new Color(166, 218, 149);
  protected static final Color RED = new Color(243, 139, 168);
  protected static final Color YELLOW = new Color(249, 226, 175);
  protected static final Color TABLE_ROW_ALT = new Color(36, 39, 58);

  public BasePanel(String title, String[] columns) {
    setLayout(new BorderLayout(0, 0));
    setBackground(BG);
    setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

    // Top bar
    JPanel topBar = new JPanel(new BorderLayout(15, 0));
    topBar.setBackground(BG);
    topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

    // Title + count
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    titlePanel.setBackground(BG);
    titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
    titleLabel.setForeground(TEXT);
    titlePanel.add(titleLabel);

    countLabel = new JLabel("");
    countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    countLabel.setForeground(SUBTEXT);
    titlePanel.add(countLabel);
    topBar.add(titlePanel, BorderLayout.WEST);

    // Search + buttons
    JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    actionsPanel.setBackground(BG);

    searchField = new JTextField(18);
    searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    searchField.setBackground(SURFACE);
    searchField.setForeground(TEXT);
    searchField.setCaretColor(TEXT);
    searchField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(OVERLAY, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    searchField.putClientProperty("JTextField.placeholderText", "Search...");
    searchField.addKeyListener(
        new KeyAdapter() {
          public void keyReleased(KeyEvent e) {
            onSearch(searchField.getText().trim());
          }
        });
    actionsPanel.add(searchField);

    JButton addBtn = createButton("+ Add", ACCENT);
    addBtn.addActionListener(e -> onAdd());
    actionsPanel.add(addBtn);

    JButton editBtn = createButton("✏ Edit", YELLOW);
    editBtn.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          if (row >= 0) onEdit(row);
          else
            JOptionPane.showMessageDialog(
                this, "Please select a row first.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    actionsPanel.add(editBtn);

    JButton deleteBtn = createButton("🗑 Delete", RED);
    deleteBtn.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          if (row >= 0) {
            int confirm =
                JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) onDelete(row);
          } else {
            JOptionPane.showMessageDialog(
                this, "Please select a row first.", "Info", JOptionPane.INFORMATION_MESSAGE);
          }
        });
    actionsPanel.add(deleteBtn);

    topBar.add(actionsPanel, BorderLayout.EAST);
    add(topBar, BorderLayout.NORTH);

    // Table
    tableModel =
        new DefaultTableModel(columns, 0) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    table = new JTable(tableModel);
    styleTable();

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBackground(BG);
    scrollPane.getViewport().setBackground(BG);
    scrollPane.setBorder(BorderFactory.createLineBorder(OVERLAY, 1));
    add(scrollPane, BorderLayout.CENTER);
  }

  private void styleTable() {
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setRowHeight(36);
    table.setBackground(BG);
    table.setForeground(TEXT);
    table.setSelectionBackground(new Color(137, 180, 250, 40));
    table.setSelectionForeground(ACCENT);
    table.setGridColor(OVERLAY);
    table.setShowGrid(true);
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(false);
    table.setIntercellSpacing(new Dimension(0, 1));
    table.setFillsViewportHeight(true);

    // Header
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setBackground(SURFACE);
    header.setForeground(ACCENT);
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
    header.setPreferredSize(new Dimension(0, 40));
    header.setReorderingAllowed(false);

    // Alternating rows
    table.setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(
              JTable t, Object value, boolean selected, boolean focused, int row, int col) {
            super.getTableCellRendererComponent(t, value, selected, focused, row, col);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            if (selected) {
              setBackground(new Color(137, 180, 250, 40));
              setForeground(ACCENT);
            } else {
              setBackground(row % 2 == 0 ? BG : TABLE_ROW_ALT);
              setForeground(TEXT);
            }
            return this;
          }
        });
  }

  protected JButton createButton(String text, Color color) {
    JButton btn = new JButton(text);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    btn.setForeground(new Color(30, 30, 46));
    btn.setBackground(color);
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btn.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
    btn.addMouseListener(
        new MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            btn.setBackground(color.brighter());
          }

          public void mouseExited(MouseEvent e) {
            btn.setBackground(color);
          }
        });
    return btn;
  }

  protected JPanel createFormField(String label, JComponent input) {
    JPanel panel = new JPanel(new BorderLayout(0, 4));
    panel.setBackground(SURFACE);
    JLabel lbl = new JLabel(label);
    lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
    lbl.setForeground(TEXT);
    panel.add(lbl, BorderLayout.NORTH);

    if (input instanceof JTextField) {
      input.setFont(new Font("Segoe UI", Font.PLAIN, 13));
      input.setBackground(BG);
      input.setForeground(TEXT);
      ((JTextField) input).setCaretColor(TEXT);
      input.setBorder(
          BorderFactory.createCompoundBorder(
              BorderFactory.createLineBorder(OVERLAY, 1),
              BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }
    panel.add(input, BorderLayout.CENTER);
    panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
    return panel;
  }

  protected JDialog createStyledDialog(String title, int width, int height) {
    JDialog dialog =
        new JDialog(
            SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setSize(width, height);
    dialog.setLocationRelativeTo(this);
    dialog.getContentPane().setBackground(SURFACE);
    return dialog;
  }

  public abstract void refreshData();

  protected abstract void onSearch(String keyword);

  protected abstract void onAdd();

  protected abstract void onEdit(int selectedRow);

  protected abstract void onDelete(int selectedRow);
}

