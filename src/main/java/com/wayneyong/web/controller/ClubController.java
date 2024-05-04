package com.wayneyong.web.controller;


import com.wayneyong.web.dto.ClubDto;
import com.wayneyong.web.models.Club;
import com.wayneyong.web.service.ClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller

public class ClubController {

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);
    private ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        logger.info("Fetching all clubs from the database.");
        List<ClubDto> clubs = clubService.findAllClubs();
        if (clubs.isEmpty()) {
            logger.warn("No clubs found in the database.");
        } else {
            logger.info("Number of clubs found: {}", clubs.size());
        }
        model.addAttribute("clubs", clubs);
        logger.info("Returning clubs data to the club-list view.");
        return "clubs-list"; // Make sure this matches your HTML file name exactly
    }

    //create data by building a form
    //thymeleft form -> json -> server
    //<form role="form" method="post" th:action="@{/clubs/new}"(post endpoint) th:object="${club}"/>

    //Get request to display form
    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model) {

        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }


    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }

        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        return "clubs-edit";
    }


    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId, @Valid @ModelAttribute("club") ClubDto club, BindingResult result) {

        if (result.hasErrors()) {
            return "clubs-edit";
        }

        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

}
