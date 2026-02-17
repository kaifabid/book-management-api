package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Book;
import com.example.demo.repo.Bookrepo;

/**
 * Service Layer for Book Operations
 * 
 * This class contains business logic related to Book entity.
 * It interacts with Bookrepo (Repository layer).
 */

@Service
public class BookService {

    // Injecting Book Repository
    @Autowired
    private Bookrepo br;

    /**
     * Fetch all books from database
     * @return List<Book>
     */
    public List<Book> getAllBooks() {
        return (List<Book>) br.findAll();
    }

    /**
     * Fetch single book by ID
     * @param bid - Book ID
     * @return Book object
     */
    public Book getBookById(Long bid) {
        return br.findById(bid)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bid));
    }

    /**
     * Add new book to database
     * @param book - Book object
     * @return success message
     */
    public String addBook(Book book) {
        br.save(book);
        return "Book added successfully";
    }

    /**
     * Update existing book
     * @param bid - Book ID
     * @param book - Updated book data
     * @return success message
     */
    public String updateBook(Long bid, Book book) {

        Book bookDb = br.findById(bid)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bid));

        if (book.getName() != null) {
            bookDb.setName(book.getName());
        }

        if (book.getAuthor() != null) {
            bookDb.setAuthor(book.getAuthor());
        }

        br.save(bookDb);

        return "Book updated successfully";
    }

    /**
     * Delete book by ID
     * @param bid - Book ID
     * @return success message
     */
    public String deleteBook(Long bid) {
        br.deleteById(bid);
        return "Book deleted successfully";
    }
}
