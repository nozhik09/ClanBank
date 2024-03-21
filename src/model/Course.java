package model;

import java.time.LocalDateTime;

public class Course {

//    private Currency currency;
    private double course;
    LocalDateTime localDateTime;


    public Course(double course) {
        this.course = course;
        localDateTime = LocalDateTime.now();
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "course=" + course +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
