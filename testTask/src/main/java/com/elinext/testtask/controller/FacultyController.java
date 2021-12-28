package com.elinext.testtask.controller;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.service.FacultyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping()
    public boolean createFaculty(@RequestBody FacultyDto facultyDto){
        return facultyService.createFaculty(facultyDto);
    }
    @GetMapping(value = "/{facultyId}")
    public FacultyDto getFacultyInfo(@PathVariable long facultyId){
        return facultyService.readFacultyDtoById(facultyId);
    }
    @PutMapping(value = "/{facultyId}")
    public boolean updateFaculty(@RequestBody FacultyDto facultyDto, @PathVariable long facultyId){
        return facultyService.updateFaculty(facultyDto, facultyId);
    }
    @DeleteMapping(value = "/{facultyId}")
    public boolean deleteFaculty(@PathVariable long facultyId){
        return facultyService.deleteFaculty(facultyId);
    }
}
