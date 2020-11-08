package com.hometask.task_security.repository;


import com.hometask.task_security.model.Email;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

    public interface EmailRepo {


            List<Email> findEmailsForSending();

            void save(Email email);

            Email findById(Long id);

            List<Email> findAllEmails();

            void deleteById(Long id);
        }

