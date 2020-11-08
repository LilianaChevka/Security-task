package com.hometask.task_security.service;

import com.hometask.task_security.model.Email;
import com.hometask.task_security.repository.EmailRepo;
import com.hometask.task_security.repository.HibernateRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailRepoImpl implements EmailRepo {

    HibernateRepo hibernateRepo;

    @Transactional
    public void save(Email email) {
        hibernateRepo.save(email);
    }

    @Transactional(readOnly = true)
    public Email findById(Long id) {
        return hibernateRepo.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Email> findAllEmails() {
        return hibernateRepo.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        hibernateRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Email> findEmailsForSending() {
        LocalDate time = LocalDate.now();
        List<Email> emailList = findAllEmails();
        List<Email> result = new ArrayList<>();
        for (Email email : emailList) {
            if (time.equals(email.getDate())) {
                result.add(email);
            }
        }
        return result;
    }
}