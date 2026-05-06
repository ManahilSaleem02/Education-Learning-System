package com.education.system;
import java.io.Serializable;
import java.util.*;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
    private String title;
    private boolean isAvailable = true;
    private LinkedList<Student> waitingList = new LinkedList<>(); // Use LinkedList for serialization
    private Student currentHolder = null;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() { return title; }

    public void reserve(Student student) {
        if (isAvailable) {
            isAvailable = false;
            currentHolder = student;
            System.out.println(student.getName() + " reserved " + title);
        } else {
            waitingList.add(student);
            System.out.println(student.getName() + " added to waiting list for " + title);
        }
    }

    public void returnBook() {
        if (!waitingList.isEmpty()) {
            Student next = waitingList.poll();
            currentHolder = next;
            System.out.println(title + " is now reserved for " + next.getName());
        } else {
            isAvailable = true;
            currentHolder = null;
            System.out.println(title + " is now available.");
        }
    }

    public boolean isAvailable() { return isAvailable; }

    public String getCurrentHolderName() {
        return currentHolder != null ? currentHolder.getName() : "Available";
    }

    public int getWaitingListSize() {
        return waitingList.size();
    }

    public void printCurrentHolder() {
        if (currentHolder != null) {
            System.out.println(currentHolder.getName() + " currently holds " + title);
        } else {
            System.out.println(title + " is currently available.");
        }
    }
}