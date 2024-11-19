package com.yh.model.service;

import java.util.List;

import com.yh.model.dto.Tokens;

public interface TokensService {
	Tokens getTokenById(long id);

	List<Tokens> getTokensByUserId(long userId);

	Tokens saveToken(Tokens token);

	void deleteToken(long id);
}
