package com.example.parth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int courseId;

    private String title;

    private String description;

    private String videoUrl;

    private String duration;

    private int lectureOrder;

    private boolean preview;

    public Lecture() {
    }

    public Lecture(int id, int courseId, String title,
                   String description, String videoUrl,
                   String duration, int lectureOrder,
                   boolean preview) {

        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.duration = duration;
        this.lectureOrder = lectureOrder;
        this.preview = preview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getLectureOrder() {
        return lectureOrder;
    }

    public void setLectureOrder(int lectureOrder) {
        this.lectureOrder = lectureOrder;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }
}