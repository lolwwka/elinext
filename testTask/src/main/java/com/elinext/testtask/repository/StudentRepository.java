package com.elinext.testtask.repository;

import com.elinext.testtask.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
    void deleteById(long id);
    Student findByFirstNameAndSecondName(String firstName, String secondName);
}
