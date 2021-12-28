package com.elinext.testtask.repository;

import com.elinext.testtask.entity.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findByNameAndClassroom(String name, int classroom);
}
