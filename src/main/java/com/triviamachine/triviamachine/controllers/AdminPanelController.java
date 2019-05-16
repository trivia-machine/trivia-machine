package com.triviamachine.triviamachine.controllers;

import com.triviamachine.triviamachine.database.*;
import com.triviamachine.triviamachine.util.BadDateException;
import com.triviamachine.triviamachine.util.ContentNotFoundException;
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

// Controller setup to run the additional administrative functions allowed by the admin account user.
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

//    Sets the admin account username and password. Currently there is only one admin user hard coded in below, but you
//    could make it so that there are more by either hard coding more accounts below or creating a admin signup.
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

//    This get route is the automatic routing for the admin route. This takes you to schedule so that you don't land on
//    a blank page.
    @GetMapping("")
    public String adminPanelDefaultRoute() {
        return "redirect:/admin/schedule";
    }

//  This route is where you are taken when you click the schedule button in the button list. This takes you to the
//    scheduling page where you can schedule the day in which the question will run live for votes.
    @GetMapping("/schedule/{id}")
    public String scheduling(
            @PathVariable Long id,
            Model model
    ) {
        Optional<Question> question = questionRepo.findById(id);
        model.addAttribute("question", question.get());
        return "admin/scheduling";
    }

    @GetMapping("/schedule/{id}/error")
    public String schedulingError(
            @PathVariable Long id,
            Model model
    ) {
        Optional<Question> question = questionRepo.findById(id);
        model.addAttribute("question", question.get());
        model.addAttribute("error", true);
        return "admin/scheduling";
    }

//    This post method follows the above get method of the same route. This follows the same logic but it will assign
//    the value picked by the admin to the specific question identified by its id.
    @PostMapping("/schedule/{id}")
    public RedirectView newSchedule(
            @PathVariable Long id,
            @RequestParam String date
    ) throws ParseException {
        Question question = questionRepo.getOne(id);

        try {
            Date mydate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            if (questionScheduleRepo.findByDate(mydate) != null) {
                return new RedirectView("/admin/schedule/" + id + "/error");
            }

            QuestionSchedule schedule = new QuestionSchedule();
            schedule.setQuestion(question);
            schedule.setDate(mydate);

            questionScheduleRepo.save(schedule);
        } catch (ParseException e) {
//            e.printStackTrace();
            throw new BadDateException(e.getMessage(), e.getErrorOffset());
        }


        return new RedirectView("/admin/schedule");
    }


//  The get method getSchedulePanel finds all of the schedule objects available and then for the list view of them on
//    the schedule viewing route.
    @GetMapping("/schedule")
    public String getSchedulePanel(@AuthenticationPrincipal AdminUser user, Model model) {
        List<QuestionSchedule> schedules = questionScheduleRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("schedules", schedules);
        return "admin/schedule";
    }


//    The delete method deleteSchedule takes the schedule identified by the get method above scheduling and deletes all
//    occurrences of the schedule with the specified Id.
    @DeleteMapping("schedule/{id}")
    public RedirectView deleteSchedule(
            @PathVariable Long id
    ) {
        this.questionScheduleRepo.deleteById(id);
        return new RedirectView("/admin/schedule");
    }


    // The get method getQuestionPanel finds all of the questions from the question repository and for the list
//    formatting on the question template
    @GetMapping("/question")
    public String getQuestionPanel(@AuthenticationPrincipal AdminUser user, Model model) {
        List<Question> questions = questionRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("questions", questions);
        return "admin/question";
    }

//    The post method newQuestion performs the creation of the question by assigning values to the parameters requested
//    from Question class. The values are added to the question object by the inputs designated for each parameter in
//    the create question form on the question template.
    @PostMapping("/createQuestion")
    public RedirectView newQuestion(
            @RequestParam String questionText,
            @RequestParam String answerOne,
            @RequestParam String answerTwo,
            @RequestParam String answerThree,
            @RequestParam String answerFour,
            @RequestParam Byte correctAnswer
    ) {

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setAnswerOne(answerOne);
        question.setAnswerTwo(answerTwo);
        question.setAnswerThree(answerThree);
        question.setAnswerFour(answerFour);
        question.setCorrectAnswer(correctAnswer);

        questionRepo.save(question);

        return new RedirectView("/admin/question");
    }

//  This get method updateDefaultRoute identifies the question wanting to be updated by id
    @GetMapping("/update/{id}")
    public String updateDefaultRoute(
            @PathVariable Long id,
            Model model
    ) {
        Optional<Question> question = questionRepo.findById(id);
        model.addAttribute("question", question.get());
        return "admin/update";
    }

//  The Post mapping method updateQuestions takes the specified question Id from the getter above and changes the requested
//  parameters.
    @PostMapping("/update/{id}")
    public RedirectView updateQuestion(
            @PathVariable Long id,
            @RequestParam String questionText,
            @RequestParam String answerOne,
            @RequestParam String answerTwo,
            @RequestParam String answerThree,
            @RequestParam String answerFour
    ) {
        Optional<Question> questions = this.questionRepo.findById(id);
        if (questions.isPresent()) {
            Question foundQuestion = questions.get();

            foundQuestion.setQuestionText(questionText);
            foundQuestion.setAnswerOne(answerOne);
            foundQuestion.setAnswerTwo(answerTwo);
            foundQuestion.setAnswerThree(answerThree);
            foundQuestion.setAnswerFour(answerFour);

            this.questionRepo.save(foundQuestion);

            return new RedirectView("/admin/question");
        }
        throw new ContentNotFoundException();
    }

//  The delete mapping method deleteQuestions takes the same question specified by Id from the getter above and then deletes
//  the occurrence of that question by the specified Id.
    @DeleteMapping("question/{id}")
    public RedirectView deleteQuestion(@PathVariable Long id) {
        this.questionRepo.deleteById(id);
        return new RedirectView("/admin/question");
    }

//  The get method getResultsPanel takes the user to the list of results taken from the results repository.
    @GetMapping("/results")
    public String getResultsPanel(@AuthenticationPrincipal AdminUser user, Model model) {
        List<Results> results = resultsRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("results", results);
        return "admin/results";
    }
}
