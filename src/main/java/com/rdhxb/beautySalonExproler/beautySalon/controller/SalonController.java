package com.rdhxb.beautySalonExproler.beautySalon.controller;

import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import com.rdhxb.beautySalonExproler.beautySalon.service.SalonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;


}
