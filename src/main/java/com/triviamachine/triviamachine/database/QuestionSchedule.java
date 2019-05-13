package com.triviamachine.triviamachine.database;

import javax.persistence.*;
import java.util.Date;

@Entity
public class QuestionSchedule {
    @Id
    @GeneratedValue
    long id;

    @ManyToOne
    private Question question;

    private Date date;

    @OneToOne
    private Results results;
}
