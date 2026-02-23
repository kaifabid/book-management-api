package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/api/books")
@Validated
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(
            @PathVariable("id") Long bookId,
            @RequestParam("loggedinuser") @NotBlank(message = "loggedinuser is required") String loggedInUser) {
        return bookService.getBookById(bookId, loggedInUser);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookRequestDto requestDto) {
        BookResponseDto createdBook = bookService.addBook(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable("id") Long bookId, @Valid @RequestBody BookRequestDto requestDto) {
        return bookService.updateBook(bookId, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
