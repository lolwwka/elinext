package com.elinext.testtask.dto;


import java.util.List;

public class FacultyDto {
    private long universityId;
    private long facultyId;
    private String name;
    private List<StudentDto> studentDtoList;
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<StudentDto> getStudentDtoList() {
        return studentDtoList;
    }

    public void setStudentDtoList(List<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
