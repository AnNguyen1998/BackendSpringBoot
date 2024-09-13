package com.example.demo.services;

import com.example.demo.dtos.StudentDTO;
import com.example.demo.dtos.StudentImageDTO;
import com.example.demo.models.Student;
import com.example.demo.models.StudentImage;
import com.example.demo.models.XepHang;
import com.example.demo.reponses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IStudentService {
    List<Student> getAllStudent();
    Student getStudentByID(Long id);
    void createStudent(StudentDTO studentDTO);
    void updateStudent(StudentDTO studentDTO, Long id);
    void deleteStudent(Long id);
    Page<Student> pagination(PageRequest pageRequest);
    Page<Student> searchStudentByName(String name, PageRequest pageRequest);
    Page<Student> searchByYear(int startYear, int endYear, PageRequest pageRequest);
    Page<Student> searchByRank(XepHang xepHang, PageRequest pageRequest);
    Page<Student> searchProVip(String name, int startYear, int endYear, XepHang xepHang, PageRequest pageRequest);

    StudentImage saveStudentImage(Long id, StudentImageDTO studentImageDTO);
    List<StudentImage> getAllStudentImage(Long id);
}
