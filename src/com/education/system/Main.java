package com.education.system;
import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load students with error handling
        Student[] students = loadStudents();
        if (students == null) {
            students = new Student[]{
                new Student(1, "Ali"),
                new Student(2, "Maryam"),
                new Student(3, "Eman")
            };
            System.out.println("Initialized with default students.");
        }

        // Initialize teachers
        Teacher[] teachers = {
            new Teacher(101, "Ms. Shama"),
            new Teacher(102, "Sir Saad")
        };

        Librarian librarian = new Librarian(201, "Library Admin");

        // Initialize books
        Book[] books = new Book[]{
            new Book("Data Structures"),
            new Book("OOP Fundamentals"),
            new Book("Cyber Security Basics")
        };

        // Initialize courses
        Course[] courses = {
            new Course("OOP", "Mon 9-11 AM"),
            new Course("Cyber Security", "Wed 2-4 PM"),
            new Course("DSA", "Tue 10-12 PM"),
            new Course("PF", "Thu 2-4 PM")
        };

        Gradebook gradebook = new Gradebook();
        LibraryReservationSystem library = new LibraryReservationSystem();
        CourseRegistrationSystem courseReg = new CourseRegistrationSystem();

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║   EDUCATION LEARNING MANAGEMENT SYSTEM                 ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        while (true) {
            try {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("                    MAIN MENU                          ");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println("  1. Course Registration System (Student)");
                System.out.println("  2. Gradebook System (Teacher)");
                System.out.println("  3. Library E-Reservation System (Librarian)");
                System.out.println("  0. Exit");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.print("Choose option: ");
                
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1:
                        handleStudentLogin(students, courses, courseReg, sc);
                        break;

                    case 2:
                        handleTeacherLogin(teachers, students, gradebook, sc);
                        break;

                    case 3:
                        handleLibrarianLogin(librarian, students, books, library, sc);
                        break;

                    case 0:
                        saveStudents(students);
                        System.out.println("\n╔════════════════════════════════════════════════════════╗");
                        System.out.println("║  Thank you for using the Education Learning System!    ║");
                        System.out.println("║  Data saved successfully. Goodbye!                     ║");
                        System.out.println("╚════════════════════════════════════════════════════════╝");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please select 0-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Student login handler
    private static void handleStudentLogin(Student[] students, Course[] courses, 
                                          CourseRegistrationSystem courseReg, Scanner sc) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Available Students:");
        for (Student s : students) {
            System.out.println("  ID: " + s.getId() + " - " + s.getName());
        }
        System.out.print("Enter Student ID: ");
        
        try {
            int sid = Integer.parseInt(sc.nextLine().trim());
            Student student = findStudentById(students, sid);
            
            if (student != null) {
                System.out.println("Welcome, " + student.getName() + "!");
                courseReg.studentMenu(student, courses, sc);
            } else {
                System.out.println("Student not found. Please check the ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    // Teacher login handler
    private static void handleTeacherLogin(Teacher[] teachers, Student[] students, 
                                          Gradebook gradebook, Scanner sc) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Available Teachers:");
        for (Teacher t : teachers) {
            System.out.println("  ID: " + t.getId() + " - " + t.getName());
        }
        System.out.print("Enter Teacher ID: ");
        
        try {
            int tid = Integer.parseInt(sc.nextLine().trim());
            Teacher teacher = findTeacherById(teachers, tid);
            
            if (teacher != null) {
                System.out.println("Welcome, " + teacher.getName() + "!");
                gradebook.teacherMenu(teacher, students, sc);
            } else {
                System.out.println("Invalid Teacher ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    // Librarian login handler
    private static void handleLibrarianLogin(Librarian librarian, Student[] students, 
                                            Book[] books, LibraryReservationSystem library, Scanner sc) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Librarian ID: " + librarian.getId());
        System.out.print("Enter Librarian ID: ");
        
        try {
            int lid = Integer.parseInt(sc.nextLine().trim());
            
            if (lid == librarian.getId()) {
                System.out.println("✓ Welcome, " + librarian.getName() + "!");
                library.librarianMenu(students, books, sc);
            } else {
                System.out.println("Invalid Librarian ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    // Helper method to find student
    private static Student findStudentById(Student[] students, int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    // Helper method to find teacher
    private static Teacher findTeacherById(Teacher[] teachers, int id) {
        for (Teacher t : teachers) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    // Student persistence with better error handling
    private static void saveStudents(Student[] students) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.ser"))) {
            out.writeObject(students);
            System.out.println("Student data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }

    private static Student[] loadStudents() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.ser"))) {
            System.out.println("Student data loaded successfully.");
            return (Student[]) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading student data: " + e.getMessage());
            return null;
        }
    }
}