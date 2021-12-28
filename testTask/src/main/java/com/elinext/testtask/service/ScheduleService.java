package com.elinext.testtask.service;

import com.elinext.testtask.dto.ScheduleDto;
import com.elinext.testtask.entity.Schedule;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ScheduleService {
    boolean create(ScheduleDto scheduleDto);
    Schedule readById(long scheduleId);
    boolean updateSchedule(long scheduleId, ScheduleDto scheduleDto);
    boolean deleteById(long id);
    ScheduleDto getScheduleInfo(long id);

}
