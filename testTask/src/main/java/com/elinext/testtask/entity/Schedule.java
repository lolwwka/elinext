package com.elinext.testtask.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String day;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "schedule_subjects",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Schedule equalSchedule = (Schedule) obj;
        if(!equalSchedule.getSubjects().containsAll(subjects) && !subjects.containsAll(equalSchedule.getSubjects())){
            return false;
        }
        return student.equals(equalSchedule.getStudent()) && day.equals(equalSchedule.getDay());
    }

    @Override
    public int hashCode() {
        final int multiplier = 25;
        int result = 1;
        result+= multiplier*result + ((day == null) ? 0 : day.hashCode());
        result+= multiplier*result + ((student == null) ? 0 : student.hashCode());
        result+= multiplier*id;
        return result;
    }
}
