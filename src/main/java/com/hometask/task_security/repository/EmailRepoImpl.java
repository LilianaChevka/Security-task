package com.hometask.task_security.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometask.task_security.model.Email;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class EmailRepoImpl implements EmailRepo {
    private final static String JSON = "listOfEmails.json";

    private final ObjectMapper mapper;

    public EmailRepoImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void save(List<Email> email) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter(JSON, true)))) {
            mapper.writeValue(out, email);
        }
    }

    @Override
    public List<Email> findAllEmails() throws IOException {
        return mapper.readValue(new File(JSON), new TypeReference<>() {
        });
    }

    @Override
    public List<Email> findEmailsForSending() throws IOException {
        LocalDateTime time = LocalDateTime.now();
        List<Email> emailList = findAllEmails();
        List<Email> result = new ArrayList<>();

        for (Email email : emailList) {
            if (time.equals(email.getDate())) {
                result.add(email);
            }
        }
        return result;
    }

    @Override
    public void deleteSentEmails() throws IOException {
        LocalDateTime time = LocalDateTime.now();
        List<Email> emailList = findAllEmails();
        List<Email> resultList = new ArrayList<>();

        for (Email email : emailList) {
            if (!time.equals(email.getDate())) {
                resultList.add(email);
            }
        }
        mapper.writeValue(new File(JSON), resultList);
    }

    @Override
    public void deleteEmailByDate(LocalDateTime time) throws IOException {
        List<Email> emails = findAllEmails();
        emails.removeIf(email -> email.getDate().equals(time));
    }

    @Override
    public void createEmail(Email email) throws IOException {
        List<Email> list = new ArrayList<>();
        list.add(new Email(email.getRecipient(), email.getSubject(), email.getBody(), email.getDate()));
        save(list);
    }
}
