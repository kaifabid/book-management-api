package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/v1/api/books")
public class BookController {
     
    @Autowired
    BookService bs;
     
    @GetMapping()
    public List<Book> getAllbooks(){
        return bs.getAllBooks();
    } 
      
    @GetMapping("/{id}")
    public Book getbooks(@PathVariable("id") Long bid) {
        return bs.getBookById(bid);
    }
      
    @PostMapping()
    public String addbooks(@RequestBody Book b) {
        return bs.addBook(b);
    }
      
    @PutMapping("/{id}")
    public String updatebooks(@PathVariable("id") Long bid, @RequestBody Book b){
        return bs.updateBook(bid, b);
    }
        
    @DeleteMapping("/{id}")
    public String deletedbooks(@PathVariable("id") Long bid) {
        bs.deleteBook(bid);
        return "Book deleted successfully";
    }
}
