package com.triviamachine.triviamachine.controllers;

import com.triviamachine.triviamachine.database.AdminUserRepository;
import com.triviamachine.triviamachine.database.QuestionRepository;
import com.triviamachine.triviamachine.database.QuestionScheduleRepository;
import com.triviamachine.triviamachine.database.ResultsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
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

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void vote() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/vote/"))
                .andExpect(status().isOk());
    }
}