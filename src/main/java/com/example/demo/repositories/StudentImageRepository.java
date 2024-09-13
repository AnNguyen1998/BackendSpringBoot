package com.example.demo.repositories;

import com.example.demo.models.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentImageRepository extends JpaRepository<StudentImage, Long> {
    List<StudentImage> findByStudentId(Long id);
}
