package com.yh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.model.dao.TokensDao;
import com.yh.model.dto.Tokens;

@Service
public class TokensServiceImpl implements TokensService {

	@Autowired
	private TokensDao tokensDao;

	@Override
	public Tokens getTokenById(long id) {
		return tokensDao.findById(id);
	}

	@Override
	public List<Tokens> getTokensByUserId(long userId) {
		return tokensDao.findByUserId(userId);
	}

	@Override
	public Tokens saveToken(Tokens token) {
		tokensDao.saveToken(token);
		return token;
	}

	@Override
	public void deleteToken(long id) {
		tokensDao.deleteToken(id);
	}
}
