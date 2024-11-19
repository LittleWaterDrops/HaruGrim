package com.yh.model.service;

import com.yh.model.dto.EmailVerifications;

public interface EmailVerificationsService {
	EmailVerifications getEmailVerificationById(long id);

	EmailVerifications getEmailVerificationByUserId(long userId);

	EmailVerifications saveEmailVerification(EmailVerifications emailVerification);

	void deleteEmailVerification(long id);
}
