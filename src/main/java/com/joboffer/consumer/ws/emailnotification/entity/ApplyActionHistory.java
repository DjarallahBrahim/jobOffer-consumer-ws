package com.joboffer.consumer.ws.emailnotification.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "apply_action_history")
@Data
public class ApplyActionHistory implements Serializable {

    private static final long serialVersionUID = new Random().nextLong();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_offer_id", columnDefinition = "CHAR(36)", nullable = false)
    private String jobOfferId; // Foreign key to JobOffer's ID

    // Optional: Define a relationship to JobOffer if you need it as an entity relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private JobOffer jobOffer;

}