package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Book;
import com.example.demo.dto.BookDTO;

import jakarta.validation.Valid;

public interface Bookrepo extends CrudRepository<Book, Long> {
	
	Optional<Book> findById(Long bid);

	void save(@Valid BookDTO b);

	
	//Search Books by Author
	@Query("SELECT b FROM Book b WHERE b.author = :author")
	List<Book> findBooksByAuthor(@Param("author") String author);

	List<Book> findByAuthor(String author);
 
	List<Book> findByAuthorIgnoreCase(String author); 
	
	//Only Available Books
	//@Query(" SELECT b FROM Book b WHERE b.available = true")
	//List<Book> findAvailableBooks();
	
	@Query("SELECT b.author, COUNT(b.author) FROM Book b GROUP BY b.author")
	//List<Object[]> getGroupedData();

	List<Object[]> getCategoryWiseCount();
	
}
   

