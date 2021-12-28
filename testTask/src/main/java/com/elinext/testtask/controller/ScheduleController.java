package com.elinext.testtask.controller;

import com.elinext.testtask.dto.ScheduleDto;
import com.elinext.testtask.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public boolean createSchedule(@Valid @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.create(scheduleDto);
    }

    @GetMapping(value = "/{scheduleId}")
    public ScheduleDto getScheduleInfo(@PathVariable long scheduleId) {
        return scheduleService.getScheduleInfo(scheduleId);
    }
    @PutMapping(value = "/{scheduleId}")
    public boolean updateSchedule(@PathVariable long scheduleId, @RequestBody ScheduleDto scheduleDto){
        return scheduleService.updateSchedule(scheduleId, scheduleDto);
    }
    @DeleteMapping(value = "/{scheduleId}")
    public boolean deleteSchedule(@PathVariable long scheduleId){
        return scheduleService.deleteById(scheduleId);
    }
}
