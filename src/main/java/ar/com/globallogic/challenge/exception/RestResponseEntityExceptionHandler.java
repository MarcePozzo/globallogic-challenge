package ar.com.globallogic.challenge.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.globallogic.challenge.dto.ErrorDto;
import ar.com.globallogic.challenge.dto.ResponseErrorDto;
import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String DEFAULT_MESSAGE = "Se produjo un error al procesar su solicitud.";

	private final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(value = { ExpiredJwtException.class })
	protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		String detail = "Su sesion ha expirado";
		return this.getErrorResponse(ex, request, HttpStatus.UNAUTHORIZED, detail);
	}

	@ExceptionHandler(value = { BusinessException.class })
	protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		String detail = ex.getMessage();
		return this.getErrorResponse(ex, request, HttpStatus.UNPROCESSABLE_ENTITY, detail);
	}

	@ExceptionHandler(value = { ValidationException.class })
	protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
		String detail = ex.getErrores().stream().collect(Collectors.joining("\n"));
		return this.getErrorResponse(ex, request, HttpStatus.UNPROCESSABLE_ENTITY, detail);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleWebApplicationException(Exception ex, WebRequest request) {
		String detail = DEFAULT_MESSAGE;
		return this.getErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, detail);
	}

	private ResponseEntity<Object> getErrorResponse(Exception ex, WebRequest request, HttpStatus httpStatus,
			String detail) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setCodigo(httpStatus.value());
		errorDto.setTimestamp(System.currentTimeMillis());
		errorDto.setDetail(detail);

		ResponseErrorDto error = new ResponseErrorDto();
		error.getError().add(errorDto);

		HttpHeaders headers = new HttpHeaders();

		Map<String, Object> responseBodyError = new HashMap<>();
		responseBodyError.put("error", error);

		log.error("\nRestResponseEntityExceptionHandler\n", ex);

		return handleExceptionInternal(ex, responseBodyError, headers, httpStatus, request);
	}
}
