package com.education.system;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Student class
 * Represents a student with courses and grades
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private Map<String, Double> grades = new HashMap<>();
    private List<Course> courses = new ArrayList<>();

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter methods
    public int getId() { return id; }
    public String getName() { return name; }
    public Map<String, Double> getGrades() { return grades; }
    public List<Course> getCourses() { return courses; }

    // Grade-related
    public void addGrade(String course, double grade) {
        grades.put(course, grade);
    }

    // Course enrollment
    public void enrollCourse(Course course) {
        // Check for time conflicts
        for (Course enrolled : courses) {
            if (hasTimeConflict(enrolled.getSchedule(), course.getSchedule())) {
                System.out.println("Time conflict with " + enrolled.getName() + 
                    " (" + enrolled.getSchedule() + ")");
                return;
            }
        }
        
        if (!courses.contains(course)) {
            courses.add(course);
            System.out.println(name + " enrolled in " + course.getName());
        } else {
            System.out.println( name + " is already enrolled in " + course.getName());
        }
    }

    // Drop course
    public void dropCourse(Course course) {
        if (courses.remove(course)) {
            System.out.println( name + " dropped " + course.getName());
        } else {
            System.out.println( name + " is not enrolled in " + course.getName());
        }
    }

    // Check for time conflicts (simple check based on day)
    private boolean hasTimeConflict(String schedule1, String schedule2) {
        String day1 = schedule1.split(" ")[0];
        String day2 = schedule2.split(" ")[0];
        return day1.equals(day2);
    }

    @Override
    public String toString() {
        return "Student{ID=" + id + ", Name='" + name + "', Courses=" + courses.size() + "}";
    }
}