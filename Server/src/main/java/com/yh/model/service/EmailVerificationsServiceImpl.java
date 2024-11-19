package com.yh.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.model.dao.EmailVerificationsDao;
import com.yh.model.dto.EmailVerifications;

@Service
public class EmailVerificationsServiceImpl implements EmailVerificationsService {

	@Autowired
	private EmailVerificationsDao emailVerificationsDao;

	@Override
	public EmailVerifications getEmailVerificationById(long id) {
		return emailVerificationsDao.findById(id);
	}

	@Override
	public EmailVerifications getEmailVerificationByUserId(long userId) {
		return emailVerificationsDao.findByUserId(userId);
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
