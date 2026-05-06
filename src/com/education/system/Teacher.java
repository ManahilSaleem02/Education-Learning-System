package com.education.system;
import java.io.Serializable;

public class Teacher implements Serializable {
	
	private static final long serialVersionUID = -678380318347400343L;
	
    private int id;
    private String name;

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void assignGrade(Student student, String course, double grade) {
        student.addGrade(course, grade);
        System.out.println(getName() + " assigned grade " + grade + " to " + student.getName() + " in " + course);
    }
}
