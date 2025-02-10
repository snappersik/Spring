//package com.aptproject.springlibraryproject.library.exception;
//
//import com.aptproject.springlibraryproject.library.constants.Errors;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import org.springframework.security.core.AuthenticationException;
//import java.nio.file.AccessDeniedException;
//
//@RestControllerAdvice
//public class ExceptionTranslator {
//
//    @ExceptionHandler(MyDeleteException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDTO handleMyDeleteException(MyDeleteException ex) {
//        return proceedFieldsErrors(ex, Errors.REST.DELETE_ERROR_MESSAGE, ex.getMessage());
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorDTO handleAuthException(AuthenticationException ex) {
//        return proceedFieldsErrors(ex, Errors.REST.AUTH_ERROR_MESSAGE, ex.getMessage());
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorDTO handleAccessDenied(AccessDeniedException ex) {
//        return proceedFieldsErrors(ex, Errors.REST.ACCESS_DENIED_ERROR_MESSAGE, ex.getMessage());
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDTO handleNotFoundException(NotFoundException ex) {
//        return proceedFieldsErrors(ex, Errors.REST.NOT_FOUND_ERROR_MESSAGE, ex.getMessage());
//    }
//}