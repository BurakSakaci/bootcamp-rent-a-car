package com.turkcell.rentACar.core.exceptions;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(
			MethodArgumentNotValidException argumentNotValidException) {
		Map<String, String> validationErrors = new HashMap<String, String>();
		for (FieldError fieldError : argumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(validationErrors, "Validation.Error");
		return errorDataResult;
	}

	@ExceptionHandler({ BusinessException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleBusinessException(BusinessException businessException) {
		Map<String, String> businessErrors = new HashMap<String, String>();
		businessErrors.put(businessException.getMessage(), "Business.Exception");

		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(businessErrors, "Business.Error");
		return errorDataResult;
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(
			MethodArgumentTypeMismatchException argumentTypeMismatchException) {
		Map<String, String> typeMismatchErrors = new HashMap<String, String>();
		typeMismatchErrors.put(argumentTypeMismatchException.getMessage(), "Hatalı Giriş");

		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(typeMismatchErrors, "TypeMismatch.Error");
		return errorDataResult;
	}

	@ExceptionHandler({ DateTimeParseException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(DateTimeParseException dateTimeParseException) {
		Map<String, String> dateTimeParseErrors = new HashMap<String, String>();
		dateTimeParseErrors.put(dateTimeParseException.getMessage(), "Hatalı Tarih Girişi");

		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(dateTimeParseErrors,
				"DateTimeParse.Error");
		return errorDataResult;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(Exception exception) {
		Map<String, String> typeMismatchErrors = new HashMap<String, String>();
		typeMismatchErrors.put(exception.getMessage(), "Bilinmeyen bir hata oluştu");

		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(typeMismatchErrors, "Exception.Error");
		return errorDataResult;
	}

}
