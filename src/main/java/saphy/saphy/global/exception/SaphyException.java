package saphy.saphy.global.exception;

import lombok.Getter;

@Getter
public class SaphyException extends RuntimeException {

	private final ErrorCode errorCode;

	private SaphyException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public static SaphyException from(ErrorCode errorCode) {
		return new SaphyException(errorCode);
	}
}