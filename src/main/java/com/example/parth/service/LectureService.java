package com.example.parth.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.parth.entity.Lecture;
import com.example.parth.repository.LectureRepository;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    private final String uploadDirectory = "uploads/lectures/";

    // Create lecture
    public Lecture createLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    // Get lectures by course
    public List<Lecture> getLecturesByCourse(int courseId) {
        return lectureRepository.findByCourseIdOrderByLectureOrderAsc(courseId);
    }

    // Get lecture
    public Lecture getLectureById(int id) {
        return lectureRepository.findById(id).orElse(null);
    }

    // Update lecture
    public Lecture updateLecture(int id, Lecture lecture) {

        Lecture existingLecture =
                lectureRepository.findById(id).orElse(null);

        if (existingLecture == null) {
            return null;
        }

        existingLecture.setTitle(lecture.getTitle());
        existingLecture.setDescription(lecture.getDescription());
        existingLecture.setDuration(lecture.getDuration());
        existingLecture.setLectureOrder(lecture.getLectureOrder());
        existingLecture.setPreview(lecture.isPreview());

        return lectureRepository.save(existingLecture);
    }

    // Delete lecture
    public void deleteLecture(int id) {
        lectureRepository.deleteById(id);
    }

    // Upload video
    public Lecture uploadVideo(
            int lectureId,
            MultipartFile video) throws IOException {

        Lecture lecture =
                lectureRepository.findById(lectureId).orElse(null);

        if (lecture == null) {
            return null;
        }

        if (video == null || video.isEmpty()) {
            throw new IllegalArgumentException("Video file is required");
        }

        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = video.getOriginalFilename();

        String extension = "";

        if (originalFileName != null &&
                originalFileName.contains(".")) {

            extension = originalFileName.substring(
                    originalFileName.lastIndexOf("."));
        }

        String fileName =
                "lecture_" + lectureId + "_" +
                UUID.randomUUID() + extension;

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                video.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        String videoUrl =
                "/videos/lectures/" + fileName;

        lecture.setVideoUrl(videoUrl);

        return lectureRepository.save(lecture);
    }
}