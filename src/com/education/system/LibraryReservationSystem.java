package com.education.system;
import java.util.Scanner;

/**
 * Library E-Reservation System
 * Manages book reservations, returns, and availability tracking
 */
public class LibraryReservationSystem {

    public void librarianMenu(Student[] students, Book[] books, Scanner sc) {
        while (true) {
            try {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("            LIBRARY E-RESERVATION SYSTEM");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println("  1. Reserve Book");
                System.out.println("  2. Return Book");
                System.out.println("  3. View All Book Status");
                System.out.println("  4. View Student's Books");
                System.out.println("  5. View Waiting Lists");
                System.out.println("  0. Logout");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.print("Choice: ");
                
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                
                int choice = Integer.parseInt(line);

                switch (choice) {
                    case 1:
                        reserveBook(students, books, sc);
                        break;
                    case 2:
                        returnBook(books, sc);
                        break;
                    case 3:
                        viewAllBookStatus(books);
                        break;
                    case 4:
                        viewStudentBooks(students, books, sc);
                        break;
                    case 5:
                        viewWaitingLists(books);
                        break;
                    case 0:
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 0-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void reserveBook(Student[] students, Book[] books, Scanner sc) {
        if (books.length == 0) {
            System.out.println("No books available in the system.");
            return;
        }

        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("           AVAILABLE BOOKS");
        System.out.println("─────────────────────────────────────────────────────");
        System.out.printf("  %-5s %-30s %-15s\n", "No.", "Title", "Status");
        System.out.println("─────────────────────────────────────────────────────");
        
        for (int i = 0; i < books.length; i++) {
            String status = books[i].isAvailable() ? "Available" : "Reserved";
            System.out.printf("  %-5d %-30s %-15s\n", (i+1), books[i].getTitle(), status);
        }
        System.out.println("─────────────────────────────────────────────────────");
        System.out.print("Select book number (0 to cancel): ");
        
        String bookInput = sc.nextLine().trim();
        if (bookInput.isEmpty() || bookInput.equals("0")) {
            System.out.println("Reservation cancelled.");
            return;
        }

        try {
            int bookChoice = Integer.parseInt(bookInput);
            
            if (bookChoice < 1 || bookChoice > books.length) {
                System.out.println("Invalid book number.");
                return;
            }

            System.out.println("\nAvailable Students:");
            for (Student s : students) {
                System.out.printf("  %d. %s\n", s.getId(), s.getName());
            }
            System.out.print("Enter Student ID: ");
            
            int sid = Integer.parseInt(sc.nextLine().trim());
            Student student = findStudent(students, sid);
            
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            books[bookChoice - 1].reserve(student);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void returnBook(Book[] books, Scanner sc) {
        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("           RESERVED BOOKS");
        System.out.println("─────────────────────────────────────────────────────");
        
        boolean hasReserved = false;
        for (int i = 0; i < books.length; i++) {
            if (!books[i].isAvailable()) {
                System.out.printf("  %d. %-30s (Holder: %s)\n", 
                    (i+1), books[i].getTitle(), books[i].getCurrentHolderName());
                hasReserved = true;
            }
        }
        
        if (!hasReserved) {
            System.out.println("  No books are currently reserved.");
            System.out.println("─────────────────────────────────────────────────────");
            return;
        }
        
        System.out.println("─────────────────────────────────────────────────────");
        System.out.print("Select book number to return (0 to cancel): ");
        
        try {
            int bookChoice = Integer.parseInt(sc.nextLine().trim());
            
            if (bookChoice == 0) {
                System.out.println("Return cancelled.");
                return;
            }
            
            if (bookChoice >= 1 && bookChoice <= books.length) {
                if (books[bookChoice - 1].isAvailable()) {
                    System.out.println("This book is already available.");
                } else {
                    books[bookChoice - 1].returnBook();
                }
            } else {
                System.out.println("Invalid book number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void viewAllBookStatus(Book[] books) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║              ALL BOOKS STATUS REPORT                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.printf("  %-5s %-30s %-20s\n", "No.", "Book Title", "Current Holder");
        System.out.println("─────────────────────────────────────────────────────────");
        
        int available = 0, reserved = 0;
        for (int i = 0; i < books.length; i++) {
            String holder = books[i].isAvailable() ? "Available" : books[i].getCurrentHolderName();
            System.out.printf("  %-5d %-30s %-20s\n", (i+1), books[i].getTitle(), holder);
            
            if (books[i].isAvailable()) available++;
            else reserved++;
        }
        
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("  Total Books: %d  |  Available: %d  |  Reserved: %d\n", 
            books.length, available, reserved);
        System.out.println("═════════════════════════════════════════════════════════\n");
    }

    private void viewStudentBooks(Student[] students, Book[] books, Scanner sc) {
        System.out.println("\nAvailable Students:");
        for (Student s : students) {
            System.out.printf("  %d. %s\n", s.getId(), s.getName());
        }
        System.out.print("Enter Student ID: ");
        
        try {
            int sid = Integer.parseInt(sc.nextLine().trim());
            Student student = findStudent(students, sid);
            
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.println("\n─────────────────────────────────────────────────────");
            System.out.println("  Books held by " + student.getName() + ":");
            System.out.println("─────────────────────────────────────────────────────");
            
            boolean hasBooks = false;
            for (Book book : books) {
                if (!book.isAvailable() && 
                    book.getCurrentHolderName().equals(student.getName())) {
                    System.out.println("  • " + book.getTitle());
                    hasBooks = true;
                }
            }
            
            if (!hasBooks) {
                System.out.println("  No books currently held.");
            }
            System.out.println("─────────────────────────────────────────────────────");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void viewWaitingLists(Book[] books) {
        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.println("           WAITING LISTS");
        System.out.println("─────────────────────────────────────────────────────");
        
        boolean hasWaiting = false;
        for (Book book : books) {
            int waitingCount = book.getWaitingListSize();
            if (waitingCount > 0) {
                System.out.printf("  %s: %d student(s) waiting\n", 
                    book.getTitle(), waitingCount);
                hasWaiting = true;
            }
        }
        
        if (!hasWaiting) {
            System.out.println("  No students in waiting lists.");
        }
        System.out.println("─────────────────────────────────────────────────────");
    }

    private Student findStudent(Student[] students, int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }
}