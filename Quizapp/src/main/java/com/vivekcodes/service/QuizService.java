package com.vivekcodes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vivekcodes.Question;
import com.vivekcodes.QuestionWrapper;
import com.vivekcodes.Quiz;
import com.vivekcodes.Response;
import com.vivekcodes.dao.QuestionDao;
import com.vivekcodes.dao.QuizDao;

@Service
public class QuizService {
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;
	
	public ResponseEntity<String> createQuiz(String category, int numOfQues, String title) {
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numOfQues);//getting questions from the database
		Quiz quiz=new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);//we are adding the questions we got from our database to the quiz table
		quizDao.save(quiz);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
		
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		
		Optional<Quiz> quiz =quizDao.findById(id);
		List<Question> questionsFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for (Question q: questionsFromDB) {
			QuestionWrapper qw =new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
			questionsForUser.add(qw);
			
		}
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
		
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		// TODO Auto-generated method stub
		Quiz quiz = quizDao.findById(id).orElse(null);
		 if (quiz == null) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }

		    List<Question> questions = quiz.getQuestions();
		    int right = 0;

		    for (Response response : responses) {
		        Optional<Question> questionOptional = questions.stream()
		                .filter(q -> q.getId().equals(response.getId()))
		                .findFirst();

		        if (questionOptional.isPresent()) {
		            Question question = questionOptional.get();
		            if (response.getResponse().equals(question.getRightAnswer())) {
		                right++;
		            }
		        }
		    }
		
		    
		  
		    return new ResponseEntity<>(right,HttpStatus.OK);
	}

}
