package com.yh.model.dao;

import com.yh.model.dto.EmailVerifications;

public interface EmailVerificationsDao {
	EmailVerifications findById(long id);

	EmailVerifications getEmailVerificationByToken(long userId);

	int saveEmailVerification(EmailVerifications emailVerification);

	int deleteEmailVerification(long id);
}
