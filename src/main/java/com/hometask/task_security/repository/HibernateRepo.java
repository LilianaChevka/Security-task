package com.hometask.task_security.repository;

import com.hometask.task_security.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface HibernateRepo extends JpaRepository<Email, Long> {
    }


