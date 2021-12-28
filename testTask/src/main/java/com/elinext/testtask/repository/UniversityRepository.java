package com.elinext.testtask.repository;

import com.elinext.testtask.entity.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UniversityRepository extends CrudRepository<University, Long> {
    University findByName(String name);
}
