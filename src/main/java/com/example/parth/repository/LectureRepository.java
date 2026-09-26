package com.example.parth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parth.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    List<Lecture> findByCourseIdOrderByLectureOrderAsc(int courseId);
}