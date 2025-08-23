package com.sis.ui;

import com.sis.dao.StudentDAO;
import com.sis.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * The main window of the Student Information System.
 * It displays a list of students in a table and provides buttons for
 * adding, editing, and deleting students, as well as generating reports.
 */
public class MainFrame extends JFrame {

    private final StudentDAO studentDAO;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        studentDAO = new StudentDAO();

        setTitle("Student Information System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // --- Menu Bar ---
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu reportMenu = new JMenu("Reports");
        JMenuItem transcriptItem = new JMenuItem("Generate Transcript");
        transcriptItem.addActionListener(e -> generateTranscript());
        reportMenu.add(transcriptItem);

        menuBar.add(fileMenu);
        menuBar.add(reportMenu);
        setJMenuBar(menuBar);


        // --- Table ---
        String[] columnNames = {"ID", "Name", "Major", "GPA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
               // Make table cells non-editable
               return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getTableHeader().setReorderingAllowed(false);
        studentTable.setFont(new Font("Inter", Font.PLAIN, 14));
        studentTable.setRowHeight(25);

        // --- Buttons Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add Student");
        JButton editButton = new JButton("Edit Student");
        JButton deleteButton = new JButton("Delete Student");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // --- Layout ---
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        // Load initial data
        refreshStudentTable();
    }

    private void refreshStudentTable() {
        // Clear existing data
        tableModel.setRowCount(0);
        // Get fresh data from DAO
        List<Student> students = studentDAO.getAllStudents();
        // Populate the table
        for (Student student : students) {
            Object[] row = {
                    student.getId(),
                    student.getName(),
                    student.getMajor(),
                    student.getGpa()
            };
            tableModel.addRow(row);
        }
    }

    private void addStudent() {
        StudentDialog dialog = new StudentDialog(this, "Add New Student", null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            studentDAO.addStudent(dialog.getStudent());
            refreshStudentTable();
        }
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String major = (String) tableModel.getValueAt(selectedRow, 2);
            double gpa = (double) tableModel.getValueAt(selectedRow, 3);
            Student selectedStudent = new Student(studentId, name, major, gpa);

            StudentDialog dialog = new StudentDialog(this, "Edit Student", selectedStudent);
            dialog.setVisible(true);

            if (dialog.isSucceeded()) {
                studentDAO.updateStudent(dialog.getStudent());
                refreshStudentTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            String studentName = (String) tableModel.getValueAt(selectedRow, 1);
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + studentName + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                studentDAO.deleteStudent(studentId);
                refreshStudentTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void generateTranscript() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String major = (String) tableModel.getValueAt(selectedRow, 2);
            double gpa = (double) tableModel.getValueAt(selectedRow, 3);
            Student selectedStudent = new Student(studentId, name, major, gpa);

            ReportDialog reportDialog = new ReportDialog(this, selectedStudent);
            reportDialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to generate a transcript.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
