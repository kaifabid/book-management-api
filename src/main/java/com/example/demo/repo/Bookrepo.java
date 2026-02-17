package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Entity.Book;

public interface Bookrepo extends CrudRepository<Book, Long> {
	
	Optional<Book> findById(Long bid);

}
