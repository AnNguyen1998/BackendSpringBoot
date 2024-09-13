package com.example.demo.services;

import com.example.demo.dtos.StudentDTO;
import com.example.demo.models.Student;
import com.example.demo.models.XepHang;
import com.example.demo.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService implements IStudentService{

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentByID(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void createStudent(StudentDTO studentDTO) {
        Student st = Student.builder()
                .birth(studentDTO.getBirth())
                .city(studentDTO.getCity())
                .name(studentDTO.getName())
                .xepHang(studentDTO.getXepHang())
                .build();
        studentRepository.save(st);
    }

    @Override
    public void updateStudent(StudentDTO studentDTO, Long id) {
        Student st = getStudentByID(id);
        st.setBirth(studentDTO.getBirth());
        st.setCity(studentDTO.getCity());
        st.setName(studentDTO.getName());
        st.setXepHang(studentDTO.getXepHang());
        studentRepository.save(st);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Page<Student> pagination(PageRequest pageRequest) {
        return studentRepository.findAll(pageRequest);
    }

    @Override
    public Page<Student> searchStudentByName(String name, PageRequest pageRequest) {
        return studentRepository.searchByName(name, pageRequest);
    }

    @Override
    public Page<Student> searchByYear(int startYear, int endYear, PageRequest pageRequest) {
        return studentRepository.searchByYear(startYear,endYear, pageRequest);
    }

    @Override
    public Page<Student> searchByRank(XepHang xepHang, PageRequest pageRequest) {
        return studentRepository.searchByRank(xepHang, pageRequest);
    }

    @Override
    public Page<Student> searchProVip(String name, int startYear, int endYear, XepHang xepHang, PageRequest pageRequest) {
        return studentRepository.searchProVip(name, startYear, endYear, xepHang, pageRequest);
    }
}
