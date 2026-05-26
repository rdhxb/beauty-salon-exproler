package com.rdhxb.beautySalonExproler.beautySalon.controller;

import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import com.rdhxb.beautySalonExproler.beautySalon.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @GetMapping
    public List<Salon> getSalons(){
        return salonService.getSalons();
    }

    @GetMapping("/{id}")
    public Salon getSalonById(@PathVariable Long id){
        return salonService.getSalonById(id);
    }

    @GetMapping("/district/{district}")
    public List<Salon> getSalonsByDistrict(@PathVariable String district){
        return salonService.getSalonsByDistrict(district);
    }

    @GetMapping("/open")
    public List<Salon> getOpenSalons(){
        return salonService.getOpenSalons();
    }

    @GetMapping("/top10")
    public List<Salon> getBest10(){
        return salonService.getBestSalons();
    }

    @PatchMapping("/{id}")
    @Transactional
    public void changeDistrict(@PathVariable Long id, @RequestParam String district){
        salonService.changeDistrict(district, id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteSalon(@PathVariable Long id){
        salonService.deleteSalon(id);
    }


}
