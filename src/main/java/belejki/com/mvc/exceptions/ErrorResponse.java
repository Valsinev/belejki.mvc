package belejki.com.mvc.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
	private String message;
	private int status;
	private long timeStamp;
}
