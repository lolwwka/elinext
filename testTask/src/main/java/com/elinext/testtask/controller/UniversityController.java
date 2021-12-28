package com.elinext.testtask.controller;

import com.elinext.testtask.dto.UniversityDto;
import com.elinext.testtask.service.UniversityService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/university")
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping(value = "/{universityId}")
    public UniversityDto getUniversityInfo(@PathVariable long universityId) {
        return universityService.readUniversityDtoById(universityId);
    }
    @PostMapping
    public Boolean createUniversity(@RequestBody UniversityDto universityDto){
        return universityService.create(universityDto.getName());
    }
    @DeleteMapping(value = "/{universityId}")
    public boolean deleteUniversity(@PathVariable long universityId){
        return universityService.deleteUniversity(universityId);
    }
    @PutMapping(value = "/{universityId}")
    public UniversityDto updateUniversity(@RequestBody UniversityDto universityDto, @PathVariable long universityId){
        return universityService.updateUniversity(universityDto, universityId);
    }
}
