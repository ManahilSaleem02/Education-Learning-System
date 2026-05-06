package com.education.system;
import java.io.Serializable;

public class Course implements Serializable {
	private static final long serialVersionUID = 2L;
    private String name;
    private String schedule; // e.g., "Mon 9-11 AM"

    public Course(String name, String schedule) {
        this.name = name;
        this.schedule = schedule;
    }

    public String getName() { return name; }
    public String getSchedule() { return schedule; }

    @Override
    public String toString() {
        return name + " (" + schedule + ")";
    }
}
