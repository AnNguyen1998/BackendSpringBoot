package com.example.demo.dtos;


import com.example.demo.models.XepHang;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentDTO {
    @NotBlank(message = "Ten khong duoc trong")
    private String  name;
    @NotBlank(message = "Ten khong duoc trong")
    private String city;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Phai la 1 ngay trong qua khu")
    private LocalDate birth;
    @NotNull(message = "rank khong duoc trong")
    @Enumerated(EnumType.STRING)
    private XepHang xepHang;
}
