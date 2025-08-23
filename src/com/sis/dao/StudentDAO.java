package com.sis.dao;

import com.sis.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * StudentDAO (Data Access Object) manages the persistence of Student objects.
 * This class handles all CRUD (Create, Read, Update, Delete) operations.
 * In this implementation, it uses an in-memory List to simulate a database.
 */
public class StudentDAO {

    private final List<Student> studentList;
    private int nextId = 1;

    public StudentDAO() {
        studentList = new ArrayList<>();
        // Add some dummy data for demonstration purposes
        addStudent(new Student(0, "Alice Johnson", "Computer Science", 3.8));
        addStudent(new Student(0, "Bob Williams", "Physics", 3.5));
        addStudent(new Student(0, "Charlie Brown", "Mathematics", 3.9));
        addStudent(new Student(0, "Diana Miller", "Biology", 3.2));
    }

    /**
     * Retrieves all students from the data store.
     * @return A list of all students.
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentList); // Return a copy to prevent modification
    }

    /**
     * Adds a new student to the data store.
     * It automatically assigns a unique ID.
     * @param student The student object to add.
     */
    public void addStudent(Student student) {
        student.setId(nextId++);
        studentList.add(student);
    }

    /**
     * Updates an existing student's information.
     * @param updatedStudent The student object with updated details.
     */
    public void updateStudent(Student updatedStudent) {
        Optional<Student> studentOptional = studentList.stream()
                .filter(s -> s.getId() == updatedStudent.getId())
                .findFirst();

        studentOptional.ifPresent(student -> {
            student.setName(updatedStudent.getName());
            student.setMajor(updatedStudent.getMajor());
            student.setGpa(updatedStudent.getGpa());
        });
    }

    /**
     * Deletes a student from the data store based on their ID.
     * @param studentId The ID of the student to delete.
     */
    public void deleteStudent(int studentId) {
        studentList.removeIf(student -> student.getId() == studentId);
    }
}
