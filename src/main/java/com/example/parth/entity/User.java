package com.example.parth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String email;

    private String password;

    private String course;

    private Double marks;

    private Double attendance;

    private Integer semester;


    // Default Constructor
    public User() {
        super();
    }


    // Parameterized Constructor
    public User(long id, String name, String email, String password,
                String course, Double marks, Double attendance, Integer semester) {

        super();

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
        this.semester = semester;
    }


    // ID
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Course
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


    // Marks
    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }


    // Attendance
    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }


    // Semester
    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}