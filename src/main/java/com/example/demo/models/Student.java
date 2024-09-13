package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Ten khong duoc trong")
    @Size(min = 2, max = 50, message = "Do dai ten phai tu 2 den 50")
    private String  name;
    @NotBlank(message = "Ten thanh pho khong duoc trong")
    private String city;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Phai la 1 ngay trong qua khu")
    private LocalDate birth;
    @NotNull(message = "rank khong duoc trong")
    @Enumerated(EnumType.STRING)
    private XepHang xepHang;
}
