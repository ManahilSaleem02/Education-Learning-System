## Education Learning Management System (ELMS)

A comprehensive Java-based education management system that integrates course registration, gradebook management, and library reservation services into one unified platform.

# Features

1. Course Registration System
Enroll in courses with automatic schedule conflict detection
View personal class schedule
Drop courses easily
Pre-defined courses: OOP, Cyber Security, DSA, PF

 2. Gradebook System
Enter and update student grades (0–100 scale)
Automatic GPA calculation (4.0 scale)
Generate official transcripts with letter grades
View all students with their current GPA
Grade conversion: Percentage → Letter Grade → GPA

 3. Library E-Reservation System
Reserve books with waiting list management
Return books with automatic next-student assignment
View book status (available/reserved)
Track student’s borrowed books
View waiting lists per book
Available books:
Data Structures
OOP Fundamentals
Cyber Security Basics

# 🏗️ Project Structure
com/education/system/
├── Main.java                      # Entry point & main menu
├── Student.java                   # Student entity with courses & grades
├── Teacher.java                   # Teacher entity for grade management
├── Librarian.java                 # Librarian entity
├── Course.java                    # Course entity with schedule
├── Book.java                      # Book entity with waiting list
├── CourseRegistrationSystem.java  # Course enrollment logic
├── Gradebook.java                 # Grade management & transcripts
└── LibraryReservationSystem.java  # Library operations

# 🚀 Getting Started

1. Prerequisites
Java 8 or higher
VS Code (recommended) or any Java IDE
Git

2. Installation
   
1. Clone the repository
git clone git@github.com:ManahilSaleem02/Student-System.git
cd Student-System
2. Open in VS Code
code .

4. Run the application

Navigate to Main.java and click Run, or use terminal:

javac com/education/system/*.java
java com.education.system.Main

# 💻 Usage Guide

1.Default Login Credentials
Students
Ali (ID: 1)
Maryam (ID: 2)
Eman (ID: 3)
Teachers
Ms. Shama (ID: 101)
Sir Saad (ID: 102)
Librarian
Library Admin (ID: 201)

 2.Main Menu Options
Course Registration System (Student access)
Gradebook System (Teacher access)
Library E-Reservation System (Librarian access)
Exit (saves data automatically)
Student Features
Enroll in courses (with time conflict detection)
View class schedule
Drop courses

 3.Teacher Features
Enter and update grades
Generate student transcripts
View all students with GPA

 4.Librarian Features
Reserve and return books
Manage waiting lists
Track book inventory
View student borrowing history

# 💾 Data Persistence

The system automatically saves student data to students.ser when exiting. On next launch, it loads:

Enrolled courses
Grade history
Student information

# 🛠️ Technologies Used
Language: Java (OOP concepts)
Serialization: Data persistence
Collections: ArrayList, HashMap, LinkedList
Exception Handling: Input validation

# 📋 Sample Workflow
For Students
1. Main Menu → Option 1
2. Enter Student ID (e.g., 1)
3. Enroll Course → Select course number
4. View Schedule → See enrolled courses
5. Drop Course → Remove unwanted courses
For Teachers
1. Main Menu → Option 2
2. Enter Teacher ID (e.g., 101)
3. Enter Grades → Select student → Input course & grade
4. Generate Transcript → Select student → View report
For Librarians
1. Main Menu → Option 3
2. Enter Librarian ID (201)
3. Reserve Book → Select book → Enter student ID
4. Return Book → Select reserved book
5. View Book Status → See availability

# ⚙️ System Requirements
Java Runtime Environment (JRE) 8+
Terminal with UTF-8 support
Minimum 256MB RAM

# 🔧 Troubleshooting
Q: Box-drawing characters look broken?
Ensure terminal supports UTF-8 encoding
On Windows: Use PowerShell, Git Bash, or Windows Terminal
Q: "Class not found" errors?
Compile from parent directory of com/
Use:
javac com/education/system/*.java
Q: Serialization errors?
Delete students.ser to reset data
Ensure all classes define serialVersionUID

# 📝 Future Enhancements
GUI interface (JavaFX/Swing)
Database integration (MySQL/PostgreSQL)
User authentication system
Email notifications for book returns
Online payment for library fines
REST API for mobile apps

# 👩‍💻 Author

Manahil Saleem

# 📄 License

This project is for educational purposes only.

🙏 Acknowledgments
VS Code Java Extension
OpenSSH for GitHub integration
