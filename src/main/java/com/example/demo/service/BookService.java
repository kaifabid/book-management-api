package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Book;
import com.example.demo.Entity.Employee;
import com.example.demo.client.EmployeeClient;
import com.example.demo.dto.BookDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.repo.Bookrepo;

import jakarta.validation.Valid;

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
    
    
    
    
    @Autowired
    private EmployeeClient employeeClient;

    public Employee getEmployee(Long id){
        return (Employee) employeeClient.getEmployee(id);
    }


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
     * @param loggedinuser 
     * @return Book object 
     */
    public Book getBookById(Long bid, String loggedinuser) {
    	
    	//try {
    		Optional<Book> opb = br.findById((bid));
    		if(!opb.isPresent()) { 
    			throw new RuntimeException("Book not found with id: " + bid);
    		}
    		String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    		if(loggedinuser!=null && !opb.get().getAuthor().equalsIgnoreCase(loggedinuser)) {
    			throw new UnauthorizedException("Unauthorised access");
    		}
    		
    		return opb.get();
//    	}catch(Exception ex) {
//    		System.out.print("Exception occurred.");
//    	}
    	//return null;
        //return br.findById(bid).orElseThrow(() -> new RuntimeException("Book not found with id: " + bid));
    }
     
    /**
     * Add new book to database
     * @param b - Book object
     * @return success message
     */
    public String addBook(@Valid BookDTO b) {
        br.save(b);
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
                .orElseThrow(() -> new CustomException("Book not found with id: " + bid));

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
    
 // Search Books By Author
    public List<Book> getBooksByAuthor(String author){
    	//List<Object[]> result = br.getGroupedData();
    	//for(Object[] r: result) 
    	//{
    		//System.out.print((String)r[0] + ", count "+ (Long)r[1]);
    	//}
    	return br.findByAuthor(author);
        //return br.findBooksByAuthor(author);
    }

	public List<Object[]> getCategoryReport() {
		// TODO Auto-generated method stu 
		return br.getCategoryWiseCount();
			
	} 
}






















