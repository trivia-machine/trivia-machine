package com.triviamachine.triviamachine;

import com.triviamachine.triviamachine.controllers.AdminPanelController;
import com.triviamachine.triviamachine.controllers.ApplicaitonController;
import com.triviamachine.triviamachine.controllers.VoteController;
import com.triviamachine.triviamachine.database.*;
import org.hamcrest.Matchers;
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

//Below are the AdminPanelController routes testing
//	Schedule Methods Testing
	@Test
	public void adminRouteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/"))
				.andExpect(status().is(302));
		}

	@Test
	public void adminSchedPostTest() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		QuestionSchedule schedule = new QuestionSchedule();
		schedule.setQuestion(question);

		questionRepository.save(question);
		questionScheduleRepository.save(schedule);

		long id=question.getId();

		mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/" + id + "?date=1222-01-21"))
				.andExpect(status().is(302));
	}

	@Test
	public void adminSchedDeleteTest() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		QuestionSchedule schedule = new QuestionSchedule();
		schedule.setQuestion(question);

		questionRepository.save(question);
		questionScheduleRepository.save(schedule);

		long id=question.getId();

		mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/" + id + "?date=1222-01-21"))
				.andExpect(status().is(302));

		long schedId=schedule.getId();

		mockMvc.perform(MockMvcRequestBuilders.delete("/admin/schedule/" + schedId))
				.andExpect(status().is(302));
	}

	@Test
	public void getAdminSchedTest() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		QuestionSchedule schedule = new QuestionSchedule();
		schedule.setQuestion(question);

		questionRepository.save(question);
		questionScheduleRepository.save(schedule);

		long id=question.getId();

		mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/" + id + "?date=1222-01-21"))
				.andExpect(status().is(302));

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/schedule"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(Matchers.containsString("idk")));
	}

	@Test
	public void getAdminSchedIDTest() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		QuestionSchedule schedule = new QuestionSchedule();
		schedule.setQuestion(question);

		questionRepository.save(question);
		questionScheduleRepository.save(schedule);

		long id=question.getId();

		mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/" + id + "?date=1222-01-21"))
				.andExpect(status().is(302));

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/schedule/" + id))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(Matchers.containsString("idk")));

	}

//	Question Methods testing

	@Test
	public void getAdminQuestTest() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		questionRepository.save(question);

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/question"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(Matchers.containsString("idk")));
	}

	@Test
	public void postAdminCreateQuest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/admin/createQuestion?questionText=%E2%80%9Cidk%E2%80%9D&answerOne=%E2%80%9Cugh%E2%80%9D&answerTwo=%E2%80%9Cmurr%E2%80%9D&answerThree=%E2%80%9C%E2%80%9D&answerFour="))
				.andExpect(status().is(302));
	}

	@Test
	public void getAdminUpdateId() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		questionRepository.save(question);

		long id = question.getId();

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/update/" + id))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(Matchers.containsString("idk")));
	}

	@Test
	public void postUpdateId() throws Exception {

		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");
		question.setAnswerThree("");
		question.setAnswerFour("");
		questionRepository.save(question);

		long id = question.getId();
		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/admin/update/"+id+"?questionText=%E2%80%9Cidk%E2%80%9D&answerOne=%E2%80%9Cugh%E2%80%9D&answerTwo=%E2%80%9Cmurr%E2%80%9D&answerThree=%E2%80%9C%E2%80%9D&answerFour="))
				.andExpect(status().is(302));
	}

	@Test
	public void deleteAdminQuestId() throws Exception {
		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");
		question.setAnswerThree("");
		question.setAnswerFour("");
		questionRepository.save(question);

		long id = question.getId();

		mockMvc.perform(MockMvcRequestBuilders.delete("/admin/question/" + id))
				.andExpect(status().is(302));
	}

//  Testing the Admin Results
	@Test
	public void getAdminResults() throws Exception {
		Question question = new Question();
		question.setQuestionText("idk");
		question.setAnswerOne("what");
		question.setAnswerTwo("huh");

		QuestionSchedule schedule = new QuestionSchedule();
		schedule.setQuestion(question);

		Results results = new Results();
		results.setQuestion(question);
		results.setAnswerOneVotes(4);
		results.setAnswerTwoVotes(5);

		questionRepository.save(question);

		questionScheduleRepository.save(schedule);

		resultsRepository.save(results);

		long id=question.getId();

		mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/" + id + "?date=1222-01-21"))
				.andExpect(status().is(302));

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/results"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(Matchers.containsString("idk")));
	}



}
