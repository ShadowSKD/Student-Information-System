# Student Information System

A simple Java Swing application for managing student records, including adding, editing, deleting students, and generating academic transcripts.

## Features

- Add, edit, and delete student records
- View all students in a table
- Generate and view student transcripts
- In-memory data storage (no external database required)
- User-friendly graphical interface

## Project Structure

```
StudentInformationSystem/
├── .gitignore
├── src/
│   └── com/
│       └── sis/
│           ├── App.java
│           ├── dao/
│           │   └── StudentDAO.java
│           ├── model/
│           │   └── Student.java
│           └── ui/
│               ├── MainFrame.java
│               ├── ReportDialog.java
│               └── StudentDialog.java
```

## How to Build and Run

1. **Compile the source code:**

   ```sh
   javac -d out src/com/sis/*.java src/com/sis/dao/*.java src/com/sis/model/*.java src/com/sis/ui/*.java
   ```

2. **Run the application:**

   ```sh
   java -cp out com.sis.App
   ```

## Requirements

- Java 8 or higher

## License

This project is for educational purposes.