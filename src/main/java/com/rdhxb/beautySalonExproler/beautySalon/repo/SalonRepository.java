package com.rdhxb.beautySalonExproler.beautySalon.repo;

import com.rdhxb.beautySalonExproler.beautySalon.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon,Long> {
    List<Salon> findByDistrict(String district);

    List<Salon> findByIsOpenTrue(boolean isOpen);
}
