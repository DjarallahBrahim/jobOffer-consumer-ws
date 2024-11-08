package com.joboffer.consumer.ws.emailnotification.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "job_offer")
@Data
public class JobOffer implements Serializable {

    private static final long serialVersionUID = new Random().nextLong();

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id = UUID.randomUUID().toString();

    private String name;

    @Column(columnDefinition = "json")
    private String skills; // Stored as JSON array

    private String description;

    private String email;

    private Double salary;

    // Getters and setters
}
