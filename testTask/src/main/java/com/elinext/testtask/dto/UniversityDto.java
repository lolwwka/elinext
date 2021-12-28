package com.elinext.testtask.dto;

import java.util.Set;

public class UniversityDto {
    private String name;
    private Set<FacultyDto> faculties;
    private boolean error;

    public UniversityDto() {
    }

    public UniversityDto(String name) {
        this.name = name;
    }

    public UniversityDto(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FacultyDto> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<FacultyDto> faculties) {
        this.faculties = faculties;
    }
}
