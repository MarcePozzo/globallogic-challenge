package ar.com.globallogic.challenge.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseErrorDto {

	private List<ErrorDto> error = new ArrayList<ErrorDto>();

	public ResponseErrorDto() {
		super();
	}

	public List<ErrorDto> getError() {
		return error;
	}

	public void setError(List<ErrorDto> error) {
		this.error = error;
	}

}
