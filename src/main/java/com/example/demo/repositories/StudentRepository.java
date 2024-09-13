package com.example.demo.repositories;

import com.example.demo.models.Student;
import com.example.demo.models.XepHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameIgnoreCase(String name);

    @Query("select s from Student s where s.xepHang = ?1")
    Page<Student> searchByRank(XepHang xepHang, PageRequest pageRequest);

    @Query("select s from Student s where year(s.birth) between ?1 and ?2")
    Page<Student> searchByYear(int startYear, int endYear, PageRequest pageRequest);

    @Query("select s from Student s where s.name like ?1%")
    Page<Student> searchByName(String name, PageRequest pageRequest);

    @Query("select s from Student s where s.name like %?1% and s.xepHang = ?4 and year(s.birth) between ?2 and ?3")
    Page<Student> searchProVip(String name, int startYear, int endYear, XepHang xepHang, PageRequest pageRequest);

}
