package com.education.system;
import java.util.Scanner;

public class CourseRegistrationSystem {

    public void studentMenu(Student student, Course[] courses, Scanner sc) {
        while (true) {
            try {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("              STUDENT MENU - " + student.getName());
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println("  1. Enroll in Course");
                System.out.println("  2. View My Schedule");
                System.out.println("  3. Drop Course");
                System.out.println("  0. Logout");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.print("Choice: ");

                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1:
                        enrollCourse(student, courses, sc);
                        break;
                    case 2:
                        viewSchedule(student);
                        break;
                    case 3:
                        dropCourse(student, sc);
                        break;
                    case 0:
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 0-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void enrollCourse(Student student, Course[] courses, Scanner sc) {
        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("           AVAILABLE COURSES");
        System.out.println("─────────────────────────────────────────────────────");
        
        for (int i = 0; i < courses.length; i++) {
            System.out.printf("  %d. %-20s (%s)\n", (i+1), 
                courses[i].getName(), courses[i].getSchedule());
        }
        System.out.println("─────────────────────────────────────────────────────");
        System.out.print("Select course number (0 to cancel): ");
        
        try {
            int opt = Integer.parseInt(sc.nextLine().trim());
            
            if (opt == 0) {
                System.out.println("Enrollment cancelled.");
                return;
            }
            
            if (opt >= 1 && opt <= courses.length) {
                student.enrollCourse(courses[opt - 1]);
            } else {
                System.out.println("Invalid course number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void viewSchedule(Student student) {
        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("         MY COURSE SCHEDULE - " + student.getName());
        System.out.println("─────────────────────────────────────────────────────");
        
        if (student.getCourses().isEmpty()) {
            System.out.println("  No courses enrolled yet.");
        } else {
            System.out.printf("  %-5s %-20s %-20s\n", "No.", "Course", "Schedule");
            System.out.println("─────────────────────────────────────────────────────");
            
            int count = 1;
            for (Course c : student.getCourses()) {
                System.out.printf("  %-5d %-20s %-20s\n", count++, 
                    c.getName(), c.getSchedule());
            }
            System.out.println("─────────────────────────────────────────────────────");
            System.out.println("  Total Courses: " + student.getCourses().size());
        }
        System.out.println("─────────────────────────────────────────────────────");
    }

    private void dropCourse(Student student, Scanner sc) {
        if (student.getCourses().isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }

        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("           YOUR ENROLLED COURSES");
        System.out.println("─────────────────────────────────────────────────────");
        
        for (int i = 0; i < student.getCourses().size(); i++) {
            Course c = student.getCourses().get(i);
            System.out.printf("  %d. %-20s (%s)\n", (i+1), 
                c.getName(), c.getSchedule());
        }
        System.out.println("─────────────────────────────────────────────────────");
        System.out.print("Select course to drop (0 to cancel): ");
        
        try {
            int opt = Integer.parseInt(sc.nextLine().trim());
            
            if (opt == 0) {
                System.out.println("Drop cancelled.");
                return;
            }
            
            if (opt >= 1 && opt <= student.getCourses().size()) {
                Course dropped = student.getCourses().get(opt - 1);
                student.dropCourse(dropped);
                System.out.println("✓ Successfully dropped " + dropped.getName());
            } else {
                System.out.println("Invalid course number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}