package com.rdhxb.beautySalonExproler.beautySalon.service;

import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import com.rdhxb.beautySalonExproler.beautySalon.repo.SalonRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Salon getSalonById(Long id){
        return salonRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Salon with id: " + id + " not found !"));
    }

    public List<Salon> getSalonsByDistrict(String district){
        return salonRepo.findByDistrict(district);
    }

    public List<Salon> getOpenSalons(){
        return salonRepo.findByIsOpenTrue(true);
    }

    public List<Salon> getBestSalons(){
        return  getSalons().stream().sorted((s1,s2) -> Double.compare(s2.getRating(), s1.getRating())).limit(10).toList();
    }

    @Transactional
    public void changeDistrict(String district, Long id){
        Salon salon = getSalonById(id);
        salon.setDistrict(district);
        salonRepo.save(salon);
    }

    @Transactional
    public void deleteSalon(Long id){
        Salon salon = getSalonById(id);
        salonRepo.delete(salon);
    }



}
