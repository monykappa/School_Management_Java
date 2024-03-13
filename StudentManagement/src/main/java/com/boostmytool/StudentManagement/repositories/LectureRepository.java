package com.boostmytool.StudentManagement.repositories;

import com.boostmytool.StudentManagement.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    Optional<Lecture> findByFullname(String fullname);
    Lecture findByFullnameAndPassword(String fullname, String password);
    List<Lecture> findBySkill(String skill);
}
