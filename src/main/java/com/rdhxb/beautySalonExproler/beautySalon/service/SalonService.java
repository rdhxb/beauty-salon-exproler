package com.rdhxb.beautySalonExproler.beautySalon.service;

import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import com.rdhxb.beautySalonExproler.beautySalon.repo.SalonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SalonService {

    private final SalonRepository salonRepo;


    public void saveData(List<Salon> salonList){
        salonRepo.saveAll(salonList);
    }


    public List<Salon> getSalons(){
       return salonRepo.findAll();
    }


}
