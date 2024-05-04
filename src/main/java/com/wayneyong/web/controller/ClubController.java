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
        logger.info("Request received to list all clubs");
        List<ClubDto> clubs = clubService.findAllClubs();
        if (clubs.isEmpty()) {
            logger.warn("No clubs found in the database during list operation.");
        } else {
            logger.info("Number of clubs listed: {}", clubs.size());
        }
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        logger.info("Request received to display the form for creating a new club");
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.warn("Validation errors when attempting to save a new club: {}", clubDto);
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        logger.info("New club successfully saved with ID: {}", clubDto.getId());
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") Long clubId) {
        logger.info("Request received to delete club with ID: {}", clubId);
        clubService.delete(clubId);
        logger.info("Club with ID: {} has been successfully deleted", clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model) {
        logger.info("Request received to view details for club with ID: {}", clubId);
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        logger.info("Details for club ID: {} have been loaded into the model", clubId);
        return "clubs-detail";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model) {
        logger.info("Request received to edit club with ID: {}", clubId);
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        logger.info("Data for editing club ID: {} has been added to the model", clubId);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId, @Valid @ModelAttribute("club") ClubDto club, BindingResult result) {
        logger.info("Attempting to update club with ID: {}", clubId);
        if (result.hasErrors()) {
            logger.warn("Validation errors occurred while updating club with ID: {}", clubId);
            return "clubs-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        logger.info("Club with ID: {} has been successfully updated", clubId);
        return "redirect:/clubs";
    }
}
