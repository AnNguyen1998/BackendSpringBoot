package com.example.demo.controllers;

import com.example.demo.dtos.StudentDTO;
import com.example.demo.dtos.StudentImageDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Student;
import com.example.demo.models.StudentImage;
import com.example.demo.models.XepHang;
import com.example.demo.reponses.ApiResponse;
import com.example.demo.reponses.ListStudentResponse;
import com.example.demo.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;
    @Operation(summary = "get All Students", description = "Get all student in table")
    @GetMapping("/getall")
    public ResponseEntity<ApiResponse> getAll(){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(studentService.getAllStudent())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> fe = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Co loi xay ra !")
                    .data(fe)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            studentService.createStudent(studentDTO);
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(studentDTO)
                    .message("Create successfully")
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@Valid @PathVariable(name = "id")Long id, @RequestBody StudentDTO studentDTO,BindingResult result){
        if(result.hasErrors()){
            List<String> err = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Co loi xay ra !")
                    .data(err)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            Student student = studentService.getStudentByID(id);
            if(student == null){
                throw new ResourceNotFoundException("Khong tim thay student voi id = "+id);
            }
            studentService.updateStudent(studentDTO,id);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Updated successfully")
                    .data(studentDTO)
                    .build();
            return ResponseEntity.ok(apiResponse);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable(name = "id")Long id){
        Student student = studentService.getStudentByID(id);
        if(student == null){
            throw new ResourceNotFoundException("Khong tim thay student voi id = "+id);
        }
        studentService.deleteStudent(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Deleted successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse> pagination(@RequestParam(name = "page", defaultValue = "0")int page,
                                    @RequestParam(name = "size", defaultValue = "5")int size){
        try{
            PageRequest pageRequest = PageRequest.of(page,size);
            Page<Student> pageSt = studentService.pagination(pageRequest);
            List<Student> st = pageSt.getContent();
            ListStudentResponse listStudentResponse = ListStudentResponse.builder()
                    .totalPage(pageSt.getTotalPages())
                    .listStudent(st)
                    .build();
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Pagination successfully")
                    .data(listStudentResponse)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByName(@RequestParam(value = "name")String name,
                                                    @RequestParam(name = "page", defaultValue = "0")int page,
                                                    @RequestParam(name = "size", defaultValue = "5")int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Student> pageSt = studentService.searchStudentByName(name, pageRequest);
        List<Student> st = pageSt.getContent();
        ListStudentResponse listStudentResponse = ListStudentResponse.builder()
                .listStudent(st)
                .totalPage(pageSt.getTotalPages())
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully by name = "+name)
                .data(listStudentResponse)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/search1")
    public ResponseEntity<ApiResponse> searchByRange(@RequestParam(value = "startYear", defaultValue = "1900")int startYear,
                                                     @RequestParam(value = "endYear", defaultValue = "3000")int endYear,
                                                     @RequestParam(name = "page", defaultValue = "0")int page,
                                                     @RequestParam(name = "size", defaultValue = "5")int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Student> pageSt = studentService.searchByYear(startYear,endYear, pageRequest);
        List<Student> st = pageSt.getContent();
        ListStudentResponse listStudentResponse = ListStudentResponse.builder()
                .listStudent(st)
                .totalPage(pageSt.getTotalPages())
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search by Year successfully")
                .data(listStudentResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/search2")
    public ResponseEntity<ApiResponse> searchRank(@RequestParam(value = "rank")XepHang xepHang,
                                                  @RequestParam(name = "page", defaultValue = "0")int page,
                                                  @RequestParam(name = "size", defaultValue = "5")int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Student> pageSt = studentService.searchByRank(xepHang, pageRequest);
        List<Student> st = pageSt.getContent();
        ListStudentResponse listStudentResponse = ListStudentResponse.builder()
                .listStudent(st)
                .totalPage(pageSt.getTotalPages())
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search by rank successfully")
                .data(listStudentResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/search3")
    public ResponseEntity<ApiResponse> searchProVip(
            @RequestParam(value = "name", required = false, defaultValue = "%")String name,
            @RequestParam(value = "startYear", defaultValue = "1990", required = false)int startYear,
            @RequestParam(value = "endYear", defaultValue = "3000", required = false)int endYear,
            @RequestParam(value = "rank", required = false)XepHang xepHang,
            @RequestParam(name = "page", defaultValue = "0", required = false)int page,
            @RequestParam(name = "size", defaultValue = "5", required = false)int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Student> pageSt = studentService.searchProVip(name, startYear,endYear,xepHang, pageRequest);
        List<Student> st = pageSt.getContent();
        ListStudentResponse listStudentResponse = ListStudentResponse.builder()
                .listStudent(st)
                .totalPage(pageSt.getTotalPages())
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search Than Thanh successfully")
                .data(listStudentResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getAllImage/{id}")
    public ResponseEntity<ApiResponse> getAllImage(@PathVariable(value = "id")Long studentId){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get Image Url Successfully")
                .data(studentService.getAllStudentImage(studentId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping(value = "/uploadsImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadImage(@PathVariable(value = "id")Long id, @ModelAttribute("files")List<MultipartFile> files) throws IOException {
        List<StudentImage> studentImages = new ArrayList<>();
        int count = 0;
        studentService.getStudentByID(id);
        for(MultipartFile file : files) {
            if (file != null) {
                if(file.getSize() == 0) {
                    count++;
                    continue;
                }
            }
            String fileName = storeFile(file);
            StudentImageDTO studentImageDTO = StudentImageDTO.builder()
                    .imageUrl(fileName)
                    .build();
            StudentImage studentImage = studentService.saveStudentImage(id, studentImageDTO);
            studentImages.add(studentImage);
        }
        if(count == 1){
            throw new IllegalArgumentException("File rong");
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Upload Image Url successfully")
                .data(studentImages)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString()+"_"+fileName;
        java.nio.file.Path uploadDdir = Paths.get("upload");
        if(!Files.exists(uploadDdir)){
            Files.createDirectory(uploadDdir);
        }
        java.nio.file.Path destination = Paths.get(uploadDdir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @GetMapping("getimage/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try{
            java.nio.file.Path imagePath = Paths.get("upload/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("upload/notfound.jpeg").toUri()));
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
