package com.exam.dto;

import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Alias("TodoDTO")
public class TodoDTO {

    int id;
    String userid;
    String description;
    LocalDate targetDate;  // 실제컬럼명은 target_date
    boolean done;

    public TodoDTO() {}

    public TodoDTO(int id, String userid, String description, LocalDate targetDate, boolean done) {
        this.id = id;
        this.userid = userid;
        this.description = description;
        this.targetDate = targetDate;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", description='" + description + '\'' +
                ", targetDate=" + targetDate +
                ", done=" + done +
                '}';
    }
}