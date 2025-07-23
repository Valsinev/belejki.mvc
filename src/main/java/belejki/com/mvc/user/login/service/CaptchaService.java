package belejki.com.mvc.user.login.service;

public interface CaptchaService {
	boolean isCaptchaValid(String captchaToken);
}
