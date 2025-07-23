package belejki.com.mvc.shared.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
	private String message;
	private int status;
	private long timeStamp;
}
