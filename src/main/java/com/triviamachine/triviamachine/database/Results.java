package com.triviamachine.triviamachine.database;

import javax.persistence.*;

@Entity
public class Results {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Question question;

    @OneToOne
    private QuestionSchedule schedule;

    private int answerOneVotes;
    private int answerTwoVotes;
    private int answerThreeVotes;
    private int answerFourVotes;


    public Question getQuestion() {
        return question;
    }

    public int getAnswerOneVotes() {
        return answerOneVotes;
    }

    public void setAnswerOneVotes(int answerOneVotes) {
        this.answerOneVotes = answerOneVotes;
    }

    public int getAnswerTwoVotes() {
        return answerTwoVotes;
    }

    public void setAnswerTwoVotes(int answerTwoVotes) {
        this.answerTwoVotes = answerTwoVotes;
    }

    public int getAnswerThreeVotes() {
        return answerThreeVotes;
    }

    public void setAnswerThreeVotes(int answerThreeVotes) {
        this.answerThreeVotes = answerThreeVotes;
    }

    public int getAnswerFourVotes() {
        return answerFourVotes;
    }

    public void setAnswerFourVotes(int answerFourVotes) {
        this.answerFourVotes = answerFourVotes;
    }

    public long getId() {
        return id;
    }

    public QuestionSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(QuestionSchedule schedule) {
        this.schedule = schedule;
    }
}
