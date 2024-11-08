package com.joboffer.consumer.ws.emailnotification.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Random;

@Entity
@Table(name = "candidate")
@Data
public class Candidate implements Serializable {

    private static final long serialVersionUID = new Random().nextLong();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    @Column(columnDefinition = "json")
    private String skills; // Stored as JSON array

    // Getters and setters
}