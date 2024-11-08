package com.joboffer.consumer.ws.emailnotification.entity;


import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "processed_event")
@Data
public class ProcessedEvent implements Serializable {

    private static final long serialVersionUID = new Random().nextLong();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String messageId;

    private LocalDateTime timestamp = LocalDateTime.now();

}
