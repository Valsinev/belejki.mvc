package belejki.com.mvc.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {
	private final RecaptchaValidationService recaptchaValidationService;

	@Autowired
	public CaptchaServiceImpl(RecaptchaValidationService recaptchaValidationService) {
		this.recaptchaValidationService = recaptchaValidationService;
	}

	@Override
	public boolean isCaptchaValid(String captchaToken) {
		return recaptchaValidationService.isCaptchaValid(captchaToken);
	}
}
