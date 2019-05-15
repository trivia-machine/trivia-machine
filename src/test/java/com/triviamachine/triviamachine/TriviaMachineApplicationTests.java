package com.triviamachine.triviamachine;

import com.triviamachine.triviamachine.controllers.AdminPanelController;
import com.triviamachine.triviamachine.controllers.ApplicaitonController;
import com.triviamachine.triviamachine.controllers.VoteController;
import com.triviamachine.triviamachine.database.*;
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

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TriviaMachineApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	public WebApplicationContext webApplicationContext;

	@Autowired
	AdminPanelController adminPanelController;

	@Autowired
	ApplicaitonController applicaitonController;

//	@Autowired
//	VoteController voteController;
//
//	@Autowired
//	AdminUserRepository adminUserRepository;
//
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionScheduleRepository questionScheduleRepository;

//	@Autowired
//	ResultsRepository resultsRepository;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


//	Testing of the routes and redirects
	@Test
	public void adminRouteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/"))
				.andExpect(status().is(302));
		}

	@Test
	public void adminSchedIdRouteTest() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		QuestionSchedule schedule = new QuestionSchedule();
		schedule.setQuestion(question);

		questionRepository.save(question);
		questionScheduleRepository.save(schedule);

//		The request parameter was added to the URL to bypass the form
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/2?date=1222-01-21"))
				.andExpect(status().is(302));
	}


//	admin controller method testing

//
//	@Test
//	public void contextLoads() {
//	}

}
