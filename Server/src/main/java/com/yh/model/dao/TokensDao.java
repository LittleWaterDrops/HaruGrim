package com.yh.model.dao;

import java.util.List;

import com.yh.model.dto.Tokens;

public interface TokensDao {
	Tokens findById(long id);

	List<Tokens> findByUserId(long userId);

	int createToken(Tokens token);

	int deleteToken(long id);
}
