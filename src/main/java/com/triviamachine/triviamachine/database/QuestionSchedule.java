package com.triviamachine.triviamachine.database;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class QuestionSchedule {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Question question;


    private Date date;

    @OneToOne
    private Results results;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public String getDateToString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public boolean isAfterToday() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = formatter.parse(formatter.format(new Date()));
        today = new Date(today.getTime() + TimeUnit.DAYS.toMillis(1));
        return this.date.after(today);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public long getId() {
        return id;
    }
}
