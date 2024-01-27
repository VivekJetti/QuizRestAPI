package com.vivekcodes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekcodes.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
