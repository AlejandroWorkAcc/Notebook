package ru.dolinini.notebook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.dolinini.notebook.model.NotebookEntry;
import ru.dolinini.notebook.model.User;
import ru.dolinini.notebook.repos.NotebookRepo;
import ru.dolinini.notebook.repos.UserRepo;
import ru.dolinini.notebook.security.SecurityUser;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/notebook")
public class NoteController {

    private final UserRepo userRepo;
    private final NotebookRepo notebookRepo;
    private final String redirectToNotes="redirect:/notebook/notes";

    @Autowired
    public NoteController(UserRepo userRepo, NotebookRepo notebookRepo) {
        this.userRepo = userRepo;
        this.notebookRepo = notebookRepo;
    }

    @GetMapping("/notes")
    @PreAuthorize("hasAnyAuthority('permission:readnotes', 'permission:read', 'permission:write')")
    public String findAllNotes (@RequestParam (value = "searched", required = false) String searched, Model model) {
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= userRepo.findByFirstname(userDetails.getUsername()).orElseThrow();
        Iterable<NotebookEntry>list=notebookRepo.findAllByUserId(user.getId());
        if (searched!=null && !searched.isEmpty()) {
            List<NotebookEntry>filteredList=new ArrayList<>();
            list.forEach(filteredList::add);
            filteredList=filteredList.stream()
                                     .filter(s->s.getTitle().contains(searched) || s.getContent().contains(searched))
                                     .collect(Collectors.toList());
            model.addAttribute("searched", searched);
            model.addAttribute("list", filteredList);
        }
        else {

            model.addAttribute("list", list);
        }
        return "/notebook/main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('permission:writenotes', 'permission:read', 'permission:write')")
    public String addNote (@ModelAttribute("entry") NotebookEntry entry) {
        return "/notebook/createnote";
    }
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('permission:writenotes', 'permission:read', 'permission:write')")
    public String postNewNote (@ModelAttribute("entry") @Valid NotebookEntry entry, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/notebook/createnote";
        }
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= userRepo.findByFirstname(userDetails.getUsername()).orElseThrow();
        if(entry.getContent().isEmpty() || entry.getContent().isBlank()) {
            entry.setContent("no content");
        }
        if(entry.getTitle().isEmpty() || entry.getTitle().isBlank()) {
            entry.setTitle("");
        }
        entry.setUser(user);
        entry.setDateOfCreation(new Date());
        notebookRepo.save(entry);
        return redirectToNotes;
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyAuthority('permission:writenotes', 'permission:read', 'permission:write')")
    public String editNote (@PathVariable(value="id") Long id, Model model) {
        if(!notebookRepo.existsById(id)) {
            return redirectToNotes;
        }
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= userRepo.findByFirstname(userDetails.getUsername()).orElseThrow();

        if (!user.getId().equals(notebookRepo.findById(id).get().getUser().getId())) {
            return redirectToNotes;
        }
        NotebookEntry entry=notebookRepo.findById(id).orElseThrow();
        model.addAttribute("entry", entry);
        return "/notebook/editnote";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAnyAuthority('permission:writenotes', 'permission:read', 'permission:write')")
    public String postEditedNote (@ModelAttribute("entry") @Valid NotebookEntry entry, BindingResult bindingResult, @PathVariable(value="id") Long id) {

        if(!notebookRepo.existsById(id)) {
            return redirectToNotes;
        }

        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= userRepo.findByFirstname(userDetails.getUsername()).orElseThrow();

        if (!user.getId().equals(notebookRepo.findById(id).get().getUser().getId())) {
            return redirectToNotes;
        }

        if (bindingResult.hasErrors()) {
            return "/notebook/editnote";
        }

        NotebookEntry updatedEntry=notebookRepo.findById(id).orElseThrow();
        updatedEntry.setTitle(entry.getTitle());

        if(entry.getContent().isEmpty() || entry.getContent().isBlank()) {
            updatedEntry.setContent("no content");
        } else {
            updatedEntry.setContent(entry.getContent());
        }
        if(entry.getTitle().isEmpty() || entry.getTitle().isBlank()) {
            updatedEntry.setTitle("");
        } else {
            updatedEntry.setTitle(entry.getTitle());
        }
        updatedEntry.setDateOfCreation(new Date());
        notebookRepo.save(updatedEntry);

        return redirectToNotes;
    }


    @PostMapping("/{id}/remove")
    @PreAuthorize("hasAnyAuthority('permission:writenotes', 'permission:read', 'permission:write')")
    public String removeNoteById (@PathVariable(value="id") Long id) {

        if(!notebookRepo.existsById(id)) {
            return redirectToNotes;
        }

        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= userRepo.findByFirstname(userDetails.getUsername()).orElseThrow();

        if (!user.getId().equals(notebookRepo.findById(id).get().getUser().getId())) {
            return redirectToNotes;
        }

        notebookRepo.deleteById(id);
        return redirectToNotes;
    }

}
