package com.elinext.testtask.controller;

import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public boolean createStudent(@RequestBody StudentDto studentDto){
        return studentService.createStudent(studentDto);
    }
    @GetMapping(value = "/{studentId}")
    public StudentDto getStudentInfo(@PathVariable long studentId){
        return studentService.readStudentDtoById(studentId);
    }
    @DeleteMapping(value = "/{studentId}")
    public boolean deleteStudent(@PathVariable long studentId){
        return studentService.deleteStudent(studentId);
    }
    @PutMapping(value = "/{studentId}")
    public boolean updateStudent(@PathVariable long studentId, @RequestBody StudentDto studentDto){
        return studentService.updateStudent(studentDto,studentId);
    }
}
