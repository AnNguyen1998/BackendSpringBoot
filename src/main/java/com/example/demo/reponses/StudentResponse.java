package com.example.demo.reponses;

import com.example.demo.models.XepHang;
import com.example.demo.models.Student;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
public class StudentResponse extends BaseResponse{
    private Long id;
    private String name;
    private String city;
    private XepHang xepHang;
    private LocalDate birth;

    public static StudentResponse fromStudent(Student student){
        StudentResponse studentResponse = StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .city(student.getCity())
                .xepHang(student.getXepHang())
                .birth(student.getBirth())
                .build();
        studentResponse.setCreatedAt(student.getCreatedAt());
        studentResponse.setUpdateAt(student.getUpdatedAt());
        return studentResponse;
    }
}
