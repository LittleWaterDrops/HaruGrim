package com.yh.model.service;

import org.springframework.stereotype.Service;

import com.yh.model.dao.EmailVerificationsDao;
import com.yh.model.dto.EmailVerifications;

@Service
public class EmailVerificationsServiceImpl implements EmailVerificationsService {

	private final EmailVerificationsDao emailVerificationsDao;

	// 생성자 주입
	public EmailVerificationsServiceImpl(EmailVerificationsDao emailVerificationsDao) {
		this.emailVerificationsDao = emailVerificationsDao;
	}

	@Override
	public EmailVerifications getEmailVerificationById(long id) {
		return emailVerificationsDao.findById(id);
	}

	@Override
	public EmailVerifications getEmailVerificationByUserId(long userId) {
		return emailVerificationsDao.getEmailVerificationByToken(userId);
	}

	@Override
	public EmailVerifications saveEmailVerification(EmailVerifications emailVerification) {
		emailVerificationsDao.saveEmailVerification(emailVerification);
		return emailVerification;
	}

	@Override
	public void deleteEmailVerification(long id) {
		emailVerificationsDao.deleteEmailVerification(id);
	}
}
