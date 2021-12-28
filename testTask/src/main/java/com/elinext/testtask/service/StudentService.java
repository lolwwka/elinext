package com.elinext.testtask.service;

import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.entity.Student;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StudentService {
    boolean createStudent(StudentDto studentDto);
    boolean deleteStudent(long id);
    Student readById(long id);
    boolean updateStudent(StudentDto studentDto, long id);
    StudentDto readStudentDtoById(long id);
}
