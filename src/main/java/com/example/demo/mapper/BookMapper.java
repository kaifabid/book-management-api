package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.Entity.Book;
import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDto dto) {
        Book book = new Book();
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        return book;
    }

    public BookResponseDto toResponseDto(Book book) {
        return new BookResponseDto(book.getId(), book.getName(), book.getAuthor());
    }
}
