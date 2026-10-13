package org.fluent.usersservice.io

import org.fluent.core.api.BasicErrorResponse
import org.fluent.core.exceptions.AbstractHttpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class GlobalHttpExceptionsHandler {

    /**
     * Handles all unhandled exceptions that don't have specific handlers.
     *
     * This is a catch-all handler for any exception not explicitly handled by other methods.
     * It returns a 500 Internal Server Error response with the exception class name and message.
     * This ensures that even unexpected errors return a properly formatted response.
     *
     * @param ex The caught exception
     * @return ResponseEntity with HTTP 500 status and error details
     */
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<BasicErrorResponse> {
        val errorResponse = BasicErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "${ex::class}; ${ex.message}",
        )

        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * Handles custom project-specific HTTP exceptions.
     *
     * Processes exceptions that extend [AbstractHttpException], which contain
     * their own HTTP status codes and messages. This allows different custom exceptions
     * to define their own response behavior while maintaining consistent formatting.
     *
     * @param ex The custom HTTP exception containing status code and message
     * @return ResponseEntity with the exception's status code and message
     */
    @ExceptionHandler(AbstractHttpException::class)
    fun handleCustomExceptions(ex: AbstractHttpException): ResponseEntity<BasicErrorResponse> {
        val errorResponse = BasicErrorResponse(
            status = ex.statusCode,
            message = ex.message,
        )

        return ResponseEntity(errorResponse, HttpStatus.valueOf(ex.statusCode))
    }

    /**
     * Handles validation exceptions from method argument validation.
     *
     * Processes [MethodArgumentNotValidException] which occurs when validation on
     * a method argument annotated with `@Valid` fails. Returns a user-friendly message
     * in Russian guiding the user to check their input data, without exposing
     * specific validation details that might leak implementation information.
     *
     * @param ex The validation exception
     * @return ResponseEntity with HTTP 400 Bad Request and a generic validation message
     */
    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleValidationException(ex: HandlerMethodValidationException): ResponseEntity<BasicErrorResponse> {
        val errorResponse = BasicErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Произошла ошибка при валидации входных параметров запроса.",
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}