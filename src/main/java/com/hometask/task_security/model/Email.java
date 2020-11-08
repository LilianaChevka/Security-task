package com.hometask.task_security.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String recipient;
    private String subject;
    private String body;

   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime date;

    public Email(String recipient, String subject, String body, LocalDateTime date) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.date = date;

    }
}