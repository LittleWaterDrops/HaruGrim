package com.yh.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yh.model.dao.TokensDao;
import com.yh.model.dto.Tokens;

@Service
public class TokensServiceImpl implements TokensService {

	private TokensDao tokensDao;

	public TokensServiceImpl(TokensDao tokensDao) {
		super();
		this.tokensDao = tokensDao;
	}

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
		tokensDao.createToken(token);
		return token;
	}

	@Override
	public void deleteToken(long id) {
		tokensDao.deleteToken(id);
	}
}
