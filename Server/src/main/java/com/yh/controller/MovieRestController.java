package com.yh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.model.dto.Movie;
import com.yh.model.dto.User;
import com.yh.model.service.MovieService;
import com.yh.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/movieapi")
@CrossOrigin("*")
public class MovieRestController {

	@Autowired
	private MovieService movieservice;
	
	@Autowired
	private UserService userservice;
	
	//로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		User loginUser = userservice.getUserById(user.getId());
		
		// 사용자가 존재하고 비밀번호가 맞는지 확인
		if(loginUser != null && loginUser.getPass().equals(user.getPass())) {
			session.setAttribute("loginUser", loginUser);
			return ResponseEntity.ok("로그인 성공");
		} else {
			return new ResponseEntity<>("로그인 정보 불일치", HttpStatus.UNAUTHORIZED);
		}
	}
	
	//로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
	    session.invalidate(); // 세션 무효화
	    return ResponseEntity.ok("로그아웃 성공");
	}

	// 전체 조회
	@GetMapping("/movie")
	public ResponseEntity<?> list() throws Exception {
		List<Movie> movies = movieservice.getAllMovies();
		return ResponseEntity.ok(movies);
	}

	// 아이디 조회
	@GetMapping("/movie/{id}")
	public ResponseEntity<?> selectId(@PathVariable("id") int id) throws Exception {
		Movie movie = movieservice.getMovieById(id);
		return new ResponseEntity<>(movie, movie != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	// 삽입
	@PostMapping("/movie")
	public ResponseEntity<?> insert(@RequestBody Movie movie) throws Exception {
		int result = movieservice.insert(movie);
		return new ResponseEntity<>(result, result == 1 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
	}

	// 삭제
	@DeleteMapping("/movie/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws Exception {
		int result = movieservice.delete(id);
		return new ResponseEntity<>(result, result == 1 ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
	}

	// 수정
	@PutMapping("/movie/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Movie movie) {
		movie.setId(id);
		int result = movieservice.update(movie);
		return new ResponseEntity<>(result, result == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	// 검색
	
	
	// 유저 추가
	@PostMapping("user")
	public ResponseEntity<?> insertUser(@RequestBody User user) {	
		int result = userservice.insertUser(user);
		return new ResponseEntity<>(result, result == 1 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
	}
	// 유저 검색
	@GetMapping("user/{id}")
	public ResponseEntity<?> selectUserId(@PathVariable("id") String id) {
		User user = userservice.getUserById(id);
		return new ResponseEntity<>(user, user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
