package com.rdhxb.beautySalonExproler.beautySalon.controller;

import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import com.rdhxb.beautySalonExproler.beautySalon.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalonViewController {

    private final SalonService salonService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/salons")
    public String salons(Model model) {
        List<Salon> salons = salonService.getSalons();
        model.addAttribute("salons", salons);
        return "salons";
    }

    @GetMapping("/districts")
    public String districts(@RequestParam(required = false) String district, Model model) {
        List<Salon> salons = salonService.getSalonsByDistrict(district);

        model.addAttribute("salons", salons);
        model.addAttribute("selectedDistrict", district);
        return "districts";
    }

    @GetMapping("/salons/{id}")
    public String salonDetails(@PathVariable Long id, Model model) {
        Salon salon = salonService.getSalonById(id);
        if (salon == null) return "redirect:/salons";
        model.addAttribute("salon", salon);
        return "salon-details";
    }

    @PostMapping("/salons/{id}/district")
    public String changeDistrict(@PathVariable Long id, @RequestParam String district) {
        salonService.changeDistrict(district, id);
        return "redirect:/salons/" + id;
    }

    @PostMapping("/salons/{id}/delete")
    public String deleteSalon(@PathVariable Long id) {
        salonService.deleteSalon(id);
        return "redirect:/salons";
    }

    @GetMapping("/salons/top10")
    public String topSalons(Model model) {
        List<Salon> salons = salonService.getBestSalons();
        model.addAttribute("salons", salons);
        model.addAttribute("pageTitle", "Top 10 salonów");
        return "salons";
    }

    @GetMapping("/salons/open")
    public String openSalons(Model model) {
        List<Salon> salons = salonService.getOpenSalons();
        model.addAttribute("salons", salons);
        model.addAttribute("pageTitle", "Otwarte teraz");
        return "salons";
    }
}