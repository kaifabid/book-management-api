package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Employee;
import com.example.demo.client.EmployeeClient;
import com.example.demo.dto.BookDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/books")
public class BookController {
     
    @Autowired
    BookService bs;
    
    
    @Autowired
    private EmployeeClient employeeClient; 
    
  
     
    @GetMapping("/book")
    public List<Book> getAllbooks(){
        return bs.getAllBooks();
    } 
      
    @GetMapping("/book/{id}")
    public ResponseEntity<Map<String, Object>> getbooks(@PathVariable("id") Long bid, @RequestParam(value = "user", required = false) String loggedinuser) {
//    	try {
    		return ResponseEntity.ok(Map.of("data", bs.getBookById(bid,loggedinuser)));
//    	}catch(CustomException e) {
//   		 return new ResponseEntity<>(Map.of("exception", "un error: "+e.getMessage()), HttpStatus.UNAUTHORIZED);
//    	}
//    	catch(Exception e) {
//    		 return new ResponseEntity<>(Map.of("exception", "error: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//    	}
    	  
    }
       
    //@PostMapping("/{id}")
   @PostMapping("/addbooks")
    public String addbooks(@Valid @RequestBody BookDTO b ){

        return bs.addBook(b);

    }
    
    
       
    @PutMapping("/{id}")
    public String updatebooks(@PathVariable("id") Long bid, @RequestBody Book b){
    	try {
        return  bs.updateBook(bid, b);
    	}catch(CustomException e) {
    		return "";
    	}
    	 
    	
    } 
    //getEmployee
    
    @GetMapping("/book/employee/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(employeeClient.getEmployee(id),HttpStatus.OK);
    }
        
    @DeleteMapping("/{id}")
    public String deletedbooks(@PathVariable("id") Long bid) {
        bs.deleteBook(bid);
        return "Book deleted successfully";
    }
    
    
    @GetMapping("/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author){
    	
        return bs.getBooksByAuthor(author);
          
    }
    @GetMapping("/category-report")
    public List<Object[]> categoryReport(){

     return bs.getCategoryReport();
    } 
    
} 
