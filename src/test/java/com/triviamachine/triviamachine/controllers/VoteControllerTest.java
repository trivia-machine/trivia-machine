package com.triviamachine.triviamachine.controllers;

import com.triviamachine.triviamachine.database.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest

public class VoteControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public WebApplicationContext webApplicationContext;

    @Autowired
    AdminPanelController adminPanelController;

    @Autowired
    ApplicaitonController applicaitonController;

    @Autowired
    VoteController voteController;

    @Autowired
    AdminUserRepository adminUserRepository;
    //
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionScheduleRepository questionScheduleRepository;

    @Autowired
    ResultsRepository resultsRepository;

    @Autowired
    DateFormat dateFormat;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void vote() throws Exception {
        Question question = new Question();
        question.setQuestionText("idk");
        question.setAnswerOne("what");
        question.setAnswerTwo("huh");
        question.setCorrectAnswer((byte)1);

        QuestionSchedule schedule = new QuestionSchedule();
        schedule.setQuestion(question);

        Date today = new Date(System.currentTimeMillis());
        String todayToString = dateFormat.format(today);
        today = dateFormat.parse(todayToString);

        Results results = new Results();
        results.setQuestion(question);
        results.setAnswerOneVotes(4);
        results.setAnswerTwoVotes(5);

        schedule.setResults(results);

        schedule.setDate(today);
        questionRepository.save(question);
        resultsRepository.save(results);
        questionScheduleRepository.save(schedule);

        mockMvc.perform(MockMvcRequestBuilders.post("/vote/").param("answer", "1"))
                .andExpect(status().isOk());
    }
}