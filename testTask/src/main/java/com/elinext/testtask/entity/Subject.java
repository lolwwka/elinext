package com.elinext.testtask.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private int classroom;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Schedule> scheduleList;

    @PreRemove
    private void removeSubjectFromSchedule(){
        for(Schedule schedule : scheduleList){
            schedule.getSubjects().remove(this);
        }
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }
    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Subject equalSubject = (Subject) obj;
        if(!scheduleList.containsAll((equalSubject).getScheduleList()) &&
                !equalSubject.getScheduleList().containsAll(scheduleList)){
            return false;
        }
        return name.equals(equalSubject.getName()) && classroom == equalSubject.getClassroom();
    }

    @Override
    public int hashCode() {
        final int multiplier = 25;
        int result = 1;
        result+= multiplier*result + ((name == null) ? 0 : name.hashCode());
        result+= multiplier*result + classroom;
        result+= multiplier*id;
        return result;
    }
}
