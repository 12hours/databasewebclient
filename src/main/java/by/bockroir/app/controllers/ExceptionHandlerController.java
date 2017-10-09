package by.bockroir.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleConflict(ResourceNotFoundException ex, WebRequest request,
                                                    HttpServletRequest req) {
        log.debug("Resolving ResourceNotFoundException at " + req.getServletPath());

        if (req.getServletPath().matches("\\/api\\/children\\/\\d*\\/region")) {
            String bodyOfResponse = "";
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.NO_CONTENT, request);
        } else {
            String bodyOfResponse = "{'404':'not found'}";
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }
    }
}