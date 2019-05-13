package com.triviamachine.triviamachine.database;

import javax.persistence.*;
import java.util.Date;

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
