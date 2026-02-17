package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_demo")   
public class Book {
	
	@Id
	private Long id;

	
	private String name;

	private String author;   
	
	public Book() {}

	public Book(Long id, String name, String author) {
		this.id = id;
		this.name = name;
		this.author = author;   
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;   
	}

	public void setAuthor(String author) {
		this.author = author;   
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
	}
}
