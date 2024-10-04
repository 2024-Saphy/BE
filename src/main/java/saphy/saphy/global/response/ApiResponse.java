package saphy.saphy.global.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;

@Getter
public class ApiResponse<T> {
	private Status status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Metadata metadata; // 주로 결과의 개수를 담는다.

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<T> results; // 여러 개의 결과를 반환할 때 사용한다.

	public ApiResponse(List<T> results) {
		this.status = new Status(ErrorCode.REQUEST_OK);
		this.metadata = new Metadata(results.size());
		this.results = results;
	}

	public ApiResponse(Page<T> results) {
		this.status = new Status(ErrorCode.REQUEST_OK);
		this.metadata = new Metadata(results.getContent().size(), results.getPageable(), false);
		this.results = results.getContent();
	}

	public ApiResponse(Slice<T> results) {
		this.status = new Status(ErrorCode.REQUEST_OK);
		this.metadata = new Metadata(results.getContent().size(), results.getPageable(), results.hasNext());
		this.results = results.getContent();
	}

	public ApiResponse(T data) {
		this.status = new Status(ErrorCode.REQUEST_OK);
		this.metadata = new Metadata(1);
		this.results = List.of(data);
	}

	public ApiResponse(ErrorCode errorCode) {
		this.status = new Status(errorCode);
	}

	public ApiResponse(SaphyException exception) {
		this.status = new Status(exception.getErrorCode());
	}

	@Getter
	@AllArgsConstructor
	private static class Metadata {
		private int resultCount = 0;
		private Pageable pageable;
		private boolean hasNext;

		public Metadata(int resultCount) {
			this.resultCount = resultCount;
		}
	}

	@Getter
	private static class Status {
		private int code;
		private String message;

		public Status(ErrorCode errorCode) {
			this.code = errorCode.getStatus().value();
			this.message = errorCode.getMessage();
		}
	}
}
