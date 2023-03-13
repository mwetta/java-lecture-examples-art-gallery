package com.launchcode.artgallery.controllers;

import com.launchcode.artgallery.data.ArtworkRepository;
import com.launchcode.artgallery.models.Artwork;
import com.launchcode.artgallery.models.Style;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    // TODO: Replace data layer with repository
    @Autowired
    private ArtworkRepository artworkRepository;

    // Corresponds to http://localhost:8080/artworks
    @GetMapping("")
    public String displayArtworksPage(Model model) {
        System.out.println("\n*** Artworks page content requested");
        model.addAttribute("artworks", artworkRepository.findAll());
        return "artworks/index";
    }

    // Corresponds to http://localhost:8080/artworks/add
    @GetMapping("/add")
    public String displayAddArtForm(Model model) {
        System.out.println("\n*** GET request submitted for add-art-form content");
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("styles", Style.values());
        return "artworks/add-art-form";
    }

    @PostMapping("/add")
    public String processAddArtForm(@ModelAttribute @Valid Artwork artwork, Errors errors, Model model) {
        System.out.println("\n*** POST request submitted to add " + artwork.getTitle() + " to art collection");
        if (errors.hasErrors()) {
            model.addAttribute("styles", Style.values());
            return "artworks/add-art-form";
        } else {
            artworkRepository.save(artwork);
            return "redirect:/artworks";
        }
    }

    // Corresponds to http://localhost:8080/artworks/delete
    @GetMapping("/delete")
    public String displayDeleteArtForm(Model model) {
        System.out.println("\n*** GET request submitted for delete-art-form content");
        model.addAttribute("artworks", artworkRepository.findAll());
        return "artworks/delete-art-form";
    }

    @PostMapping("/delete")
    public String processDeleteArtForm(@RequestParam(required = false) int[] artworkIds) {
        for (int id : artworkIds) {
            artworkRepository.deleteById(id);
        }
        return "redirect:/artworks";
    }

}