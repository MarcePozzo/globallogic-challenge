package ar.com.globallogic.challenge.exception;

import java.util.HashSet;
import java.util.Set;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -8087205918386267047L;

	private Set<String> errores = new HashSet<String>();

	public ValidationException(Set<String> errores) {
		this.errores = errores;
	}

	public Set<String> getErrores() {
		return errores;
	}

	public void setErrores(Set<String> errores) {
		this.errores = errores;
	}

}
