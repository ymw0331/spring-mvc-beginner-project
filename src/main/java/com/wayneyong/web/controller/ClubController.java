package com.wayneyong.web.controller;


import com.wayneyong.web.dto.ClubDto;
import com.wayneyong.web.service.ClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
