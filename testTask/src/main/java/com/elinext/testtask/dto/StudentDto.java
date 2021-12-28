package com.elinext.testtask.dto;

import java.util.List;

public class StudentDto {
    private String firstName;
    private String secondName;
    private long facultyId;
    private boolean error;
    private List<ScheduleDto> scheduleDtoList;
    private long studentId;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public StudentDto() {
    }

    public StudentDto(long studentId) {
        this.studentId = studentId;
    }

    public List<ScheduleDto> getScheduleDtoList() {
        return scheduleDtoList;
    }

    public void setScheduleDtoList(List<ScheduleDto> scheduleDtoList) {
        this.scheduleDtoList = scheduleDtoList;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }
}
