package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class StudentImageDTO {
    @JsonProperty("student_id")
    @Min(value = 1, message = "ID of Student have to bigger than 0")
    private Long studentId;
    @Size(min = 5, max = 200, message = "Image name have to bigger than 5")
    @JsonProperty("image_url")
    private String imageUrl;
}
