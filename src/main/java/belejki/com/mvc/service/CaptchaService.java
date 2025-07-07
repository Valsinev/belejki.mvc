package belejki.com.mvc.service;

public interface CaptchaService {
	boolean isCaptchaValid(String captchaToken);
}
