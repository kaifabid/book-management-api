package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.Entity.Book;
import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.exception.BookAccessDeniedException;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repo.Bookrepo;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private Bookrepo bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBookById_ShouldThrowForbidden_WhenLoggedInUserIsNotAuthor() {
        Book book = new Book(1L, "Clean Code", "robert");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.getBookById(1L, "alice"))
                .isInstanceOf(BookAccessDeniedException.class);
    }

    @Test
    void getBookById_ShouldReturnBook_WhenLoggedInUserIsAuthor() {
        Book book = new Book(1L, "Clean Code", "robert");
        BookResponseDto responseDto = new BookResponseDto(1L, "Clean Code", "robert");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.getBookById(1L, "robert");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAuthor()).isEqualTo("robert");
    }

    @Test
    void deleteBook_ShouldThrowNotFound_WhenBookDoesNotExist() {
        when(bookRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBook(100L))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void addBook_ShouldSaveAndMapResponse() {
        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setName("DDD");
        requestDto.setAuthor("eric");

        Book mapped = new Book(null, "DDD", "eric");
        Book saved = new Book(10L, "DDD", "eric");
        BookResponseDto response = new BookResponseDto(10L, "DDD", "eric");

        when(bookMapper.toEntity(requestDto)).thenReturn(mapped);
        when(bookRepository.save(mapped)).thenReturn(saved);
        when(bookMapper.toResponseDto(saved)).thenReturn(response);

        BookResponseDto result = bookService.addBook(requestDto);

        assertThat(result.getId()).isEqualTo(10L);
        verify(bookRepository).save(any(Book.class));
    }
}
