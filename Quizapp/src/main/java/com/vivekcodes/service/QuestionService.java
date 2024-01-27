package com.vivekcodes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vivekcodes.Question;
import com.vivekcodes.dao.QuestionDao;

@Service
public class QuestionService {
	@Autowired
	QuestionDao questiondao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		// TODO Auto-generated method stub
		try {
		return new ResponseEntity<>( questiondao.findAll(),HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);//this is for returning an empty array if there is an aerror 
	}



	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		// TODO Auto-generated method stub
		try {
		return new ResponseEntity<>( questiondao.findByCategory(category),HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}



	public ResponseEntity<String> addQuestion(Question question) {
		// TODO Auto-generated method stub
		questiondao.save(question);
		return new ResponseEntity<>("success",HttpStatus.CREATED);//since we are getting the data on the server, "created" status code is used
	}



	public ResponseEntity<String> deleteQuestion(Integer questionid) {
		// TODO Auto-generated method stub
		questiondao.deleteById(questionid);
		return  new ResponseEntity<>("successfully deleted",HttpStatus.OK);
	}

}
