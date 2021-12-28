package com.elinext.testtask.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String firstName;
    private String secondName;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public long getId() {
        return id;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Student equalStudent = (Student) obj;
        if(!equalStudent.getScheduleList().containsAll(scheduleList) &&
                !scheduleList.containsAll(equalStudent.getScheduleList())){
            return false;
        }
        return firstName.equals((equalStudent).getFirstName()) && secondName.equals(equalStudent.getSecondName());
    }

    @Override
    public int hashCode() {
        final int multiplier = 25;
        int result = 1;
        result +=multiplier*result + ((firstName == null ) ? 0 : firstName.hashCode());
        result +=multiplier*result + ((secondName == null ) ? 0 : secondName.hashCode());
        if(scheduleList != null) {
            for (Schedule schedule : scheduleList) {
                result += schedule.hashCode();
            }
        }
        return result;
    }
}
