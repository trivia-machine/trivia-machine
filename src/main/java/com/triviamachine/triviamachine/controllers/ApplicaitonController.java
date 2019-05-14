package com.triviamachine.triviamachine.controllers;

import com.triviamachine.triviamachine.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
public class ApplicaitonController {

    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private QuestionScheduleRepository questionScheduleRepo;
    @Autowired
    private ResultsRepository resultsRepo;
    @Autowired
    AdminUserRepository adminRepo;

    @Autowired
    DateFormat dateFormat;

    @GetMapping("/login")
    public String getLogin(@AuthenticationPrincipal AdminUser user, Model model) {
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("")
    public String getFrontPage(@AuthenticationPrincipal AdminUser user, Model model) throws ParseException {
        model.addAttribute("user", user);

        Date today = new Date(System.currentTimeMillis());
        String todayToString = dateFormat.format(today);
        today = dateFormat.parse(todayToString);

        QuestionSchedule currentQuestion = questionScheduleRepo.findByDate(today);

        if (currentQuestion.getResults() == null) {
            Results currentResults = new Results();
            currentResults.setQuestion(currentQuestion.getQuestion());

            currentQuestion.setResults(currentResults);
            currentResults.setSchedule(currentQuestion);

            resultsRepo.save(currentResults);
            questionScheduleRepo.save(currentQuestion);
        }

        model.addAttribute("question", currentQuestion.getQuestion());

        return "index";
    }

    @GetMapping("/results")
    public String getOlderResults(@AuthenticationPrincipal AdminUser user, Model model) {
        model.addAttribute("user", user);

        List<Results> results = resultsRepo.findAll();
        model.addAttribute("results", results);

        return "results";
    }
}
