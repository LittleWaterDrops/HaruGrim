package com.yh.model.service;

import java.util.List;

import com.yh.model.dto.Movie;

public interface MovieService {

	// 전체 조회
	List<Movie> getAllMovies();
	
	// 아이디로 조회
	Movie getMovieById(int id);
	
	// 삽입
	int insert(Movie movie);
	
	// 삭제
	int delete(int id);
	
	// 수정
	int update(Movie movie);
}
