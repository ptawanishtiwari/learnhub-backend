package com.example.parth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.parth.entity.Lecture;
import com.example.parth.service.LectureService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/lecture")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    // CREATE LECTURE
    @PostMapping("/create")
    public Lecture createLecture(@RequestBody Lecture lecture) {
        return lectureService.createLecture(lecture);
    }

    // GET ALL LECTURES OF COURSE
    @GetMapping("/course/{courseId}")
    public List<Lecture> getLecturesByCourse(
            @PathVariable int courseId) {

        return lectureService.getLecturesByCourse(courseId);
    }

    // GET SINGLE LECTURE
    @GetMapping("/{id}")
    public Lecture getLecture(@PathVariable int id) {
        return lectureService.getLectureById(id);
    }

    // UPDATE LECTURE
    @PutMapping("/{id}")
    public Lecture updateLecture(
            @PathVariable int id,
            @RequestBody Lecture lecture) {

        return lectureService.updateLecture(id, lecture);
    }
    
    @PostMapping("/upload/{lectureId}")
    public Lecture uploadVideo(
            @PathVariable int lectureId,
            @RequestParam("video") MultipartFile video)
            throws IOException {

        return lectureService.uploadVideo(lectureId, video);
    }

    // DELETE LECTURE
    @DeleteMapping("/{id}")
    public String deleteLecture(@PathVariable int id) {

        lectureService.deleteLecture(id);

        return "Lecture deleted successfully";
    }
}