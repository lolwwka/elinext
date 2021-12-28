package com.elinext.testtask.controller;

import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public boolean createSubject(@Valid @RequestBody SubjectDto subjectDto){
        return subjectService.createSubject(subjectDto);
    }
    @PutMapping(value = "/{subjectId}")
    public boolean updateSubject(@PathVariable long subjectId, @RequestBody SubjectDto subjectDto){
        return subjectService.updateSubject(subjectId,subjectDto);
    }
    @DeleteMapping(value = "/{subjectId}")
    public boolean deleteSubject(@PathVariable long subjectId){
        return subjectService.deleteSubjectById(subjectId);
    }
    @GetMapping(value = "/{subjectId}")
    public SubjectDto getSubjectInfo(@PathVariable long subjectId){
        return subjectService.getSubjectInfo(subjectId);
    }
}
