package com.elinext.testtask.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Validated
public class ScheduleDto {
    @Min(1)
    private long studentId;
    @NotBlank
    private String day;
    private boolean error;
    private List<Long> subjectIds;
    private Set<SubjectDto> subjectDtoSet;
    private StudentDto studentDto;
    private long scheduleId;

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public StudentDto getStudentDto() {
        return studentDto;
    }

    public void setStudentDto(StudentDto studentDto) {
        this.studentDto = studentDto;
    }

    public Set<SubjectDto> getSubjectDtoSet() {
        return subjectDtoSet;
    }

    public void setSubjectDtoSet(Set<SubjectDto> subjectDtoSet) {
        this.subjectDtoSet = subjectDtoSet;
    }

    public ScheduleDto(boolean error) {
        this.error = error;
    }

    public ScheduleDto() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
