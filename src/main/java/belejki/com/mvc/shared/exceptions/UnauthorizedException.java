package belejki.com.mvc.shared.exceptions;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String m) {
		super(m);
	}
}
