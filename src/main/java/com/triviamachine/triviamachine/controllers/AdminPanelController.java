package com.triviamachine.triviamachine.controllers;

import com.triviamachine.triviamachine.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private QuestionScheduleRepository questionScheduleRepo;
    @Autowired
    private ResultsRepository resultsRepo;
    @Autowired
    AdminUserRepository adminRepo;

    @PostConstruct
    public void init() {
        adminRepo.findByUsername("TriviaAdmin");
        if (adminRepo.findByUsername("TriviaAdmin") == null) {
            AdminUser user = new AdminUser();
            user.setUsername("TriviaAdmin");
            user.setPassword("$2a$10$UR4qARLD3/VdcQgnudyjAuWhyYQC0nM51t.b/GkzMeI0bTFAlCwT6");
            adminRepo.save(user);
        }

    }

    @GetMapping("/test")
    public RedirectView dummyData(){

        Question dummyQuestion = new Question();
        dummyQuestion.setQuestionText("Test!");
        dummyQuestion.setAnswerOne("ONE");
        dummyQuestion.setAnswerTwo("TWO");
        dummyQuestion.setAnswerThree("THREE");
        dummyQuestion.setAnswerFour("FOUR");
        questionRepo.save(dummyQuestion);

        QuestionSchedule testSched = new QuestionSchedule();
        testSched.setQuestion(dummyQuestion);
//        long time = 1557903600000L;
        testSched.setDate(new Date(1557817200000L));

        questionScheduleRepo.save(testSched);

//        Results results = new Results();
//        results.setAnswerOneVotes(4);
//        results.setAnswerTwoVotes(3);
//        results.setAnswerThreeVotes(8);
//        results.setAnswerFourVotes(9);
//        results.setQuestion(dummyQuestion);
//        results.setSchedule(testSched);

//        resultsRepo.save(results);

        return new RedirectView("/admin");
    }


    // schedule manipulation
    @GetMapping("")
    public String adminPanelDefaultRoute(){
        return "redirect:/admin/schedule";
    }

    // TODO: "create schedule" route

    @GetMapping("/schedule/{id}")
    public String scheduling(
            @PathVariable Long id,
            Model model
    ){
        Optional<Question> question = questionRepo.findById(id);
        model.addAttribute("question", question.get());
        return "admin/scheduling";
    }

    @PostMapping("/schedule/{id}")
    public RedirectView newSchedule(
            @PathVariable Long id,
            @RequestParam String date
    ) {
        Question question = questionRepo.getOne(id);

        try {
            Date mydate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        QuestionSchedule schedule = new QuestionSchedule();
        schedule.setQuestion(question);
        schedule.setDate(mydate);

        questionScheduleRepo.save(schedule);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return new RedirectView("/admin/schedule");
    }

    // read sechdule
    @GetMapping("/schedule")
    public String getSchedulePanel(@AuthenticationPrincipal AdminUser user, Model model) {
        List<QuestionSchedule> schedules = questionScheduleRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("schedules", schedules);
        return "admin/schedule";
    }

    // TODO: "update schedule" route

    // TODO: "delete schedule" route


    // question manipulation
    @GetMapping("/question")
    public String getQuestionPanel(@AuthenticationPrincipal AdminUser user, Model model){
        List<Question> questions = questionRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("questions", questions);
        return "admin/question";
    }

    @PostMapping("/createQuestion")
    public RedirectView newQuestion(
            @RequestParam String questionText,
            @RequestParam String answerOne,
            @RequestParam String answerTwo,
            @RequestParam String answerThree,
            @RequestParam String answerFour
    ) {

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setAnswerOne(answerOne);
        question.setAnswerTwo(answerTwo);
        question.setAnswerThree(answerThree);
        question.setAnswerFour(answerFour);

        questionRepo.save(question);

        return new RedirectView("/admin/question");
    }

    //TODO: "update question" route

    //TODO: "delete question" route

    @GetMapping("/results")
    public String getResultsPanel(@AuthenticationPrincipal AdminUser user, Model model){
        List<Results> results = resultsRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("results", results);
        return "admin/results";
    }
}
