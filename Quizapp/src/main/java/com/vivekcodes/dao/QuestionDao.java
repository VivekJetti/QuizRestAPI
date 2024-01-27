package com.vivekcodes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vivekcodes.Question;


@Repository
public interface QuestionDao extends JpaRepository<Question,Integer>{
	List<Question> findByCategory(String category);
	
	@Query(value="SELECT * FROM question q Where q.category =:category ORDER BY RAND() LIMIT :numOfQues",nativeQuery=true)
	List<Question> findRandomQuestionsByCategory(String category, int numOfQues);
	
}
