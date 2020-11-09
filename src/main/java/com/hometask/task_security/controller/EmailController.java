package com.hometask.task_security.controller;

import com.hometask.task_security.model.Email;
import com.hometask.task_security.repository.EmailRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {

    EmailRepo emailRepo;

    @GetMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public String mainPage() {
        return "index";
    }

    @GetMapping("/mails")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    @ResponseStatus(HttpStatus.OK)
    public String showAllEmails(Model model) {
        model.addAttribute("emails", emailRepo.findAllEmails());
        return "mails";
    }

    @GetMapping("/newEmail")
    @PreAuthorize("hasAnyAuthority('edit')")
    @ResponseStatus(HttpStatus.OK)
    public String createEmail(@ModelAttribute("email") Email email) {
        return "newEmail";
    }

    @PostMapping("/newEmail")
    @PreAuthorize("hasAnyAuthority('edit')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewEmail(@ModelAttribute("email") Email email) {
        emailRepo.save(email);
        return "redirect:/index";
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/mails/{id}")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    @ResponseStatus(HttpStatus.OK)
    public String getEmailById(@PathVariable Long id, Model model) {
        model.addAttribute("email", emailRepo.findById(id));
        return "emailById";
    }

    @DeleteMapping("/delete/email/{id}")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteByEmailById(@PathVariable Long id) {
        emailRepo.deleteById(id);
        return "redirect:/index";
    }
}
