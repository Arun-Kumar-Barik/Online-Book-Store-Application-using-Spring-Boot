package com.demo.bookStoreApplication;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service   
public class BookService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookRepo;//Interface which extends JPA repository for performing CRUD operations.
	
	public List<Book> getAllBook()
	{
		logger.info("Fetching all books");
		Iterable<Book> iterable = bookRepo.findAll();
		List<Book> Books = (List<Book>)iterable;
		return Books;
	}
	public Book getBookById(long id)
	{
		logger.info("Fetching book with id: {}", id);
		Book e =null;
		Optional<Book> o = bookRepo.findById(id);
		e=o.get();
		
		return e;
		
	}
	
	public Book updateBook(long id,Book b)
	{
		logger.info("Updating book with id: {}", id);
		b.setId(id);
		return bookRepo.save(b);//save the book record to the table
		
	}
	
	public void deleteBook(long id)
	{
		logger.warn("Deleting book with id: {}", id);
		bookRepo.deleteById(id);//delete a book by its id
	}
	
	public Book addBook(Book e)
	{
		logger.info("Adding new book: {}", e.getTitle());
		return bookRepo.save(e);//add a book
	}
}
