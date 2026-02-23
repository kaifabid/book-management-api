package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Book;
import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.exception.BookAccessDeniedException;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repo.Bookrepo;

@Service
public class BookService {

    private final Bookrepo bookRepository;
    private final BookMapper bookMapper;

    public BookService(Bookrepo bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponseDto)
                .toList();
    }

    public BookResponseDto getBookById(Long bookId, String loggedInUser) {
        Book book = findBookOrThrow(bookId);

        if (!book.getAuthor().equalsIgnoreCase(loggedInUser)) {
            throw new BookAccessDeniedException(bookId, loggedInUser);
        }

        return bookMapper.toResponseDto(book);
    }

    public BookResponseDto addBook(BookRequestDto requestDto) {
        Book book = bookMapper.toEntity(requestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDto(savedBook);
    }

    public BookResponseDto updateBook(Long bookId, BookRequestDto requestDto) {
        Book existingBook = findBookOrThrow(bookId);
        existingBook.setName(requestDto.getName());
        existingBook.setAuthor(requestDto.getAuthor());
        Book savedBook = bookRepository.save(existingBook);
        return bookMapper.toResponseDto(savedBook);
    }

    public void deleteBook(Long bookId) {
        Book existingBook = findBookOrThrow(bookId);
        bookRepository.delete(existingBook);
    }

    private Book findBookOrThrow(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
