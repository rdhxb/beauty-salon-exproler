package com.rdhxb.beautySalonExproler.beautySalon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salons")
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String district;
    private String phone;
    private String website;
    private Double rating;
    private Integer reviewCount;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(length = 1000)
    private String weeklyDescription;
}