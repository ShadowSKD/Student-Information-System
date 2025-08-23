package com.sis.ui;

import com.sis.model.Student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A dialog to display a generated report, such as a student transcript.
 */
public class ReportDialog extends JDialog {

    public ReportDialog(Frame parent, Student student) {
        super(parent, "Student Transcript", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setMargin(new Insets(10, 10, 10, 10));

        // --- Generate Transcript Content ---
        StringBuilder sb = new StringBuilder();
        sb.append("****************************************\n");
        sb.append("      ACADEMIC TRANSCRIPT\n");
        sb.append("****************************************\n\n");
        sb.append(String.format("Student Name: %s\n", student.getName()));
        sb.append(String.format("Student ID:   %d\n", student.getId()));
        sb.append(String.format("Major:        %s\n\n", student.getMajor()));
        sb.append("----------------------------------------\n");
        sb.append("         ACADEMIC PERFORMANCE\n");
        sb.append("----------------------------------------\n\n");
        sb.append(String.format("Cumulative CGPA: %.2f\n\n", student.getGpa()));
        sb.append("Academic Standing: ");
        if (student.getGpa() >= 8.5) {
            sb.append("Good Standing (Dean's List)\n");
        } else if (student.getGpa() >= 6.5) {
            sb.append("Good Standing\n");
        } else {
            sb.append("Academic Probation\n");
        }
        sb.append("\n\n\n");
        sb.append("----------------------------------------\n");
        sb.append("      END OF TRANSCRIPT\n");
        sb.append("Generated on: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))).append("\n");
        sb.append("----------------------------------------\n");


        reportArea.setText(sb.toString());

        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // --- Close Button ---
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
