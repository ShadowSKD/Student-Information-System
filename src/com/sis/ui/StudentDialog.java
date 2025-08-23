package com.sis.ui;

import com.sis.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A dialog window for adding a new student or editing an existing one.
 * It provides a form with fields for student details.
 */
public class StudentDialog extends JDialog {

    private JTextField nameField;
    private JTextField majorField;
    private JTextField gpaField;
    private JButton okButton;
    private JButton cancelButton;

    private Student student;
    private boolean succeeded;

    public StudentDialog(Frame parent, String title, Student studentToEdit) {
        super(parent, title, true); // true for modal

        this.student = studentToEdit;
        succeeded = false;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(5, 5, 5, 5);

        // --- Name ---
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(new JLabel("Name:"), cs);

        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        nameField = new JTextField(20);
        panel.add(nameField, cs);

        // --- Major ---
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(new JLabel("Major:"), cs);

        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        majorField = new JTextField(20);
        panel.add(majorField, cs);

        // --- GPA ---
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(new JLabel("GPA:"), cs);

        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        gpaField = new JTextField(20);
        panel.add(gpaField, cs);

        // --- Buttons ---
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(okButton);
        bp.add(cancelButton);

        // If editing, populate fields with existing data
        if (student != null) {
            nameField.setText(student.getName());
            majorField.setText(student.getMajor());
            gpaField.setText(String.valueOf(student.getGpa()));
        }

        // --- Action Listeners ---
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    if (student == null) { // Creating a new student
                        student = new Student(0, nameField.getText(), majorField.getText(), Double.parseDouble(gpaField.getText()));
                    } else { // Updating existing student
                        student.setName(nameField.getText());
                        student.setMajor(majorField.getText());
                        student.setGpa(Double.parseDouble(gpaField.getText()));
                    }
                    succeeded = true;
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // --- Layout ---
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private boolean validateInput() {
        // Name validation
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Major validation
        if (majorField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Major cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // GPA validation
        try {
            double gpa = Double.parseDouble(gpaField.getText());
            if (gpa < 0.0 || gpa > 4.0) {
                JOptionPane.showMessageDialog(this, "GPA must be between 0.0 and 4.0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid GPA. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
