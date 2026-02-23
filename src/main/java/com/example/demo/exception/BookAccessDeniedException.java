package com.example.demo.exception;

public class BookAccessDeniedException extends RuntimeException {

    public BookAccessDeniedException(Long bookId, String username) {
        super("User '" + username + "' is not authorized to access book with id: " + bookId);
    }
}
