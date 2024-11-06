package com.yh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.model.dao.MovieDao;
import com.yh.model.dto.Movie;

// bean으로 등록 할수 있도록 Service를 선언한다.
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieDao dao;

	public MovieServiceImpl(MovieDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Movie> getAllMovies() {
		return dao.selectAll();
	}

	@Override
	public Movie getMovieById(int id) {
		return dao.selectOne(id);
	}

	@Override
	public int insert(Movie movie) {
		return dao.insert(movie);
	}

	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public int update(Movie movie) {
		return dao.update(movie);
	}

}
