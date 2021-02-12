package com.przemkeapp.housingassociationapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.przemkeapp.housingassociationapp.Entity.Announcement;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.AnnouncementService;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    private final UserService userService;

    private final AnnouncementService announcementService;

    private final ObjectMapper mapper;

    public AnnouncementController(UserService userService, AnnouncementService announcementService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.mapper = objectMapper;
    }

    @GetMapping("/listForCommunity")
    public String showListForCommunity(@ModelAttribute("currentUser") User user, Model model) {
        int pagesCount = announcementService.announcementsPagesCountForCommunity(user.getCommunity().getId());
        model.addAttribute("pagesCount", pagesCount);
        return "announcements/list-community";
    }

    @GetMapping("/listForUser")
    public String showListForAuthor(Model model) {
        int pagesCount = announcementService.announcementsPagesCountForAuthor
                (SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("pagesCount", pagesCount);
        return "announcements/list-author";
    }

    @GetMapping(value = "/getPageForCommunity", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void getPage(@ModelAttribute("currentUser") User user, @RequestParam("page") int page, HttpServletResponse response) throws IOException {
        List<Announcement> announcements = announcementService.findAnnouncementsByCommunityId(user.getCommunity().getId(), page);
        String jsonStringAnnouncements = mapper.writeValueAsString(announcements);
        response.getOutputStream().print(jsonStringAnnouncements);
    }

    @GetMapping(value = "/getPageForUser", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void getPage(@RequestParam("page") int page, HttpServletResponse response) throws IOException {
        List<Announcement> announcements = announcementService.findAnnouncementsByAuthor
                (SecurityContextHolder.getContext().getAuthentication().getName(), page);
        String jsonStringAnnouncements = mapper.writeValueAsString(announcements);
        response.getOutputStream().print(jsonStringAnnouncements);
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public String showFormForCreate(Authentication auth, Model model) {
        User user = userService.findUserByUsername(auth.getName());
        model.addAttribute("announcement", new Announcement());

        return "announcements/form-page";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("announcementId") int announcementId, Authentication auth, Model model) {
        Announcement announcement = announcementService.findAnnouncementById(announcementId);
        if (!auth.getName().equals(announcement.getAuthor().getUserName())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("announcement", announcement);

        return "announcements/form-page";
    }

    @PostMapping("/save")
    public String createAnnouncement(@Valid @ModelAttribute("announcement") Announcement announcement, BindingResult bindingResult,
                                     Authentication auth, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            return "announcements/form-page";
        }

        announcementService.saveAnnouncement(announcement, auth.getName());

        redirectAttributes.addFlashAttribute("successAlert", "Announcement has been saved successfully");

        return "redirect:/announcement/listForUser";
    }
}
