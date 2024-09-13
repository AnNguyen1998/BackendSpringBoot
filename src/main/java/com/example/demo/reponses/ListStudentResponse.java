package com.example.demo.reponses;

import com.example.demo.models.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListStudentResponse {
    private List<Student> listStudent;
    private int totalPage;
}
