package com.elinext.testtask.repository;

import com.elinext.testtask.entity.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
}
