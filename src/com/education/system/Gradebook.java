package com.education.system;
import java.util.Map;
import java.util.Scanner;

/**
 * Gradebook System
 * Manages grade entry, transcript generation, and GPA calculation
 */
public class Gradebook {

    // Convert percentage grade to GPA (4.0 scale)
    // Using standard conversion: percentage / 25 = GPA
    private double convertToGPA(double grade) {
        double gpa = grade / 25.0;
        if (gpa > 4.0) gpa = 4.0;
        if (gpa < 0.0) gpa = 0.0;
        return gpa;
    }

    // Get letter grade based on percentage
    private String getLetterGrade(double grade) {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public void teacherMenu(Teacher teacher, Student[] students, Scanner sc) {
        while (true) {
            try {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("            TEACHER MENU - " + teacher.getName());
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println("  1. Enter Grades");
                System.out.println("  2. Update Grade");
                System.out.println("  3. Generate Transcript");
                System.out.println("  4. View All Students");
                System.out.println("  0. Logout");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.print("Choice: ");
                
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1:
                        enterGrade(teacher, students, sc);
                        break;
                    case 2:
                        updateGrade(teacher, students, sc);
                        break;
                    case 3:
                        generateTranscript(students, sc);
                        break;
                    case 4:
                        viewAllStudents(students);
                        break;
                    case 0:
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 0-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void enterGrade(Teacher teacher, Student[] students, Scanner sc) {
        Student student = selectStudent(students, sc);
        if (student == null) return;

        System.out.print("Course Name: ");
        String course = sc.nextLine().trim();
        
        if (course.isEmpty()) {
            System.out.println("Course name cannot be empty.");
            return;
        }

        System.out.print("Grade (/100): ");
        try {
            double grade = Double.parseDouble(sc.nextLine().trim());
            
            if (grade < 0 || grade > 100) {
                System.out.println("Grade must be between 0 and 100.");
                return;
            }

            teacher.assignGrade(student, course, grade);
            System.out.printf("Grade %.1f (%s, GPA: %.2f) assigned successfully!\n", 
                grade, getLetterGrade(grade), convertToGPA(grade));
                
        } catch (NumberFormatException e) {
            System.out.println("Invalid grade format.");
        }
    }

    private void updateGrade(Teacher teacher, Student[] students, Scanner sc) {
        Student student = selectStudent(students, sc);
        if (student == null) return;

        Map<String, Double> grades = student.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No grades found for this student.");
            return;
        }

        System.out.println("\nCurrent Grades:");
        int i = 1;
        for (String course : grades.keySet()) {
            System.out.printf("  %d. %s: %.1f%%\n", i++, course, grades.get(course));
        }

        System.out.print("Enter course name to update: ");
        String course = sc.nextLine().trim();
        
        if (!grades.containsKey(course)) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter new grade (/100): ");
        try {
            double grade = Double.parseDouble(sc.nextLine().trim());
            
            if (grade < 0 || grade > 100) {
                System.out.println("Grade must be between 0 and 100.");
                return;
            }

            teacher.assignGrade(student, course, grade);
            System.out.println("Grade updated successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid grade format.");
        }
    }

    private void generateTranscript(Student[] students, Scanner sc) {
        Student student = selectStudent(students, sc);
        if (student == null) return;

        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                  OFFICIAL TRANSCRIPT                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println("  Student: " + student.getName());
        System.out.println("  ID: " + student.getId());
        System.out.println("─────────────────────────────────────────────────────────");
        
        Map<String, Double> grades = student.getGrades();
        
        if (grades.isEmpty()) {
            System.out.println("  No grades entered yet.");
        } else {
            System.out.printf("  %-20s %10s %8s %8s\n", "Course", "Grade", "Letter", "GPA");
            System.out.println("─────────────────────────────────────────────────────────");
            
            double totalGPA = 0;
            int count = 0;
            
            for (Map.Entry<String, Double> entry : grades.entrySet()) {
                double percentGrade = entry.getValue();
                double gpaGrade = convertToGPA(percentGrade);
                String letter = getLetterGrade(percentGrade);
                
                System.out.printf("  %-20s %9.1f%% %7s %8.2f\n", 
                    entry.getKey(), percentGrade, letter, gpaGrade);
                totalGPA += gpaGrade;
                count++;
            }
            
            System.out.println("─────────────────────────────────────────────────────────");
            double cgpa = (count == 0) ? 0 : totalGPA / count;
            System.out.printf("  Total Courses: %d\n", count);
            System.out.printf("  Cumulative GPA: %.2f / 4.00\n", cgpa);
        }
        System.out.println("═════════════════════════════════════════════════════════\n");
    }

    private void viewAllStudents(Student[] students) {
        System.out.println("\n─────────────────────────────────────────────────────────");
        System.out.println("                  ALL STUDENTS");
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("  %-5s %-20s %-15s %-10s\n", "ID", "Name", "Courses", "GPA");
        System.out.println("─────────────────────────────────────────────────────────");
        
        for (Student s : students) {
            int courseCount = s.getCourses().size();
            double gpa = calculateGPA(s);
            System.out.printf("  %-5d %-20s %-15d %-10.2f\n", 
                s.getId(), s.getName(), courseCount, gpa);
        }
        System.out.println("─────────────────────────────────────────────────────────");
    }

    private double calculateGPA(Student student) {
        Map<String, Double> grades = student.getGrades();
        if (grades.isEmpty()) return 0.0;
        
        double totalGPA = 0;
        for (double grade : grades.values()) {
            totalGPA += convertToGPA(grade);
        }
        return totalGPA / grades.size();
    }

    private Student selectStudent(Student[] students, Scanner sc) {
        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("Select student:");
        for (Student s : students) {
            System.out.printf("  %d. %s\n", s.getId(), s.getName());
        }
        System.out.println("─────────────────────────────────────────────────────");
        System.out.print("Enter Student ID: ");
        
        try {
            int sid = Integer.parseInt(sc.nextLine().trim());
            
            for (Student s : students) {
                if (s.getId() == sid) return s;
            }
            
            System.out.println("Student not found.");
            return null;
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return null;
        }
    }
}