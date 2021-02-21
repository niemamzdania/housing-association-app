package com.przemkeapp.housingassociationapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.przemkeapp.housingassociationapp.Entity.Announcement;
import com.przemkeapp.housingassociationapp.Entity.Comment;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.AnnouncementService;
import com.przemkeapp.housingassociationapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final UserService userService;
    private final AnnouncementService announcementService;
    private final ObjectMapper mapper;

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

    @GetMapping("/{id}")
    public String showAnnouncement(@PathVariable int id, Authentication auth, Model model) {
        User user = userService.findUserByUsername(auth.getName());
        Announcement announcement = announcementService.findAnnouncementById(id);
        if (!user.getCommunity().getId().equals(announcement.getAuthor().getCommunity().getId())) {
            return "redirect:/access-denied";
        }
        model.addAttribute("announcement", announcement);
        model.addAttribute("comment", new Comment());
        return "announcements/show-one";
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

    @GetMapping("/delete/{id}")
    public String deleteAnnouncement(@PathVariable("id") int id, Authentication auth,
                                     HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Announcement announcement = announcementService.findAnnouncementById(id);
        if (!announcement.getAuthor().getUserName().equals(auth.getName()) && !request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/access-denied";
        }

        announcementService.deleteAnnouncementById(id);

        redirectAttributes.addFlashAttribute("successAlert", "Announcement has been deleted.");

        return "redirect:/";
    }

    @PostMapping("/saveComment")
    public String saveComment(@Valid Comment comment, HttpServletRequest request,
                              Authentication auth, RedirectAttributes redirectAttributes) {

        int announcementId = Integer.parseInt(request.getParameter("announcementId"));
        announcementService.saveComment(announcementId, comment, auth.getName());

        redirectAttributes.addFlashAttribute("successAlert", "Comment has been added.");

        return "redirect:/announcement/" + announcementId;
    }
}
