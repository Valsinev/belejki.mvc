package belejki.com.mvc.exceptions;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String m) {
		super(m);
	}
}
