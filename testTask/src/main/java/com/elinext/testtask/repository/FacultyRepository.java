package com.elinext.testtask.repository;

import com.elinext.testtask.entity.Faculty;
import org.springframework.data.repository.CrudRepository;

public interface FacultyRepository extends CrudRepository<Faculty, Long> {
    Faculty findByName(String name);
    void deleteById(long id);
}
