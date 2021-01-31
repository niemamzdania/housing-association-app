package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showProfile")
    public String showUser(Model model, Authentication auth) {

        User user = userService.findUserByUsername(auth.getName());
        model.addAttribute("user", user);

        return "users/show-profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(Authentication auth, RedirectAttributes redirectAttributes) {

        Set<String> roles = userService.findRolesByUsername(auth.getName());

        if (roles.contains("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("isAdminProfile", true);
        }

        return "redirect:/user/editUser?username=" + auth.getName();
    }

    @GetMapping("/showProfilePhoto")
    public void showProfilePhoto(HttpServletResponse response, Authentication auth) throws IOException {

        InputStream photo = userService.findPhotoByUsername(auth.getName());

        IOUtils.copy(photo, response.getOutputStream());
    }

    @PostMapping("/savePhoto")
    public String savePhoto(@RequestParam MultipartFile photo, Authentication auth) throws IOException {

        userService.changeProfilePhoto(photo, auth.getName());

        return "redirect:/user/showProfile";
    }

    @GetMapping("/editUser")
    @PreAuthorize("hasRole('ADMIN') or #username .equals(authentication.name)")
    public String editUser(Model model, Authentication auth,
                           @RequestParam("username") String username) {

        Set<String> roles = userService.findRolesByUsername(auth.getName());

        if (roles.contains("ROLE_ADMIN")) {
            List<String> usernames = userService.findAllUsernames();
            model.addAttribute("usernames", usernames);
        }

        User user = userService.findUserByUsername(username);

        if (!model.containsAttribute("userObject"))
            model.addAttribute("userObject", user);
        if (!model.containsAttribute("userDetails"))
            model.addAttribute("userDetails", user.getUserDetail());
        if (!model.containsAttribute("userAddress"))
            model.addAttribute("userAddress", user.getUserDetail().getAddress());

        return "users/edit-user";
    }

    @PostMapping("/saveUserData")
    @PreAuthorize("hasRole('ADMIN') or #username.equals(authentication.name)")
    public String saveUserData(@Valid @ModelAttribute("userObject") User userForm,
                               BindingResult bindingResultForUser,
                               @RequestParam("username") String username,
                               RedirectAttributes redirectAttributes
    ) {

        for (ObjectError error : bindingResultForUser.getAllErrors()) {
            if (!Objects.equals(error.getCode(), "UniqueUserField")) {
                redirectAttributes.addFlashAttribute
                        ("org.springframework.validation.BindingResult.userObject", bindingResultForUser);
                redirectAttributes.addFlashAttribute
                        ("userObject", userForm);

                return "redirect:/user/editUser?username=" + username;
            }
        }

        userService.saveUserData(userForm, username);

        return "redirect:/user/editUser?username=" + userForm.getUserName();
    }

    @PostMapping("/savePersonalData")
    @PreAuthorize("hasRole('ADMIN') or #username.equals(authentication.name)")
    public String savePersonalData(@Valid @ModelAttribute("userDetails") UserDetail userDetailForm,
                                   BindingResult bindingResultForPersonalData,
                                   @Valid @ModelAttribute("userAddress") Address userAddressForm,
                                   BindingResult bindingResultForUserAddress,
                                   @RequestParam("username") String username,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResultForPersonalData.hasErrors() || bindingResultForUserAddress.hasErrors()) {
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.userDetails", bindingResultForPersonalData);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.userAddress", bindingResultForUserAddress);
            redirectAttributes.addFlashAttribute("userDetails", userDetailForm);
            redirectAttributes.addFlashAttribute("userAddress", userAddressForm);
            return "redirect:/user/editUser?username=" + username;
        }

        userService.saveUserPersonalData(username, userDetailForm, userAddressForm);

        return "redirect:/user/editUser?username=" + username;
    }
}
