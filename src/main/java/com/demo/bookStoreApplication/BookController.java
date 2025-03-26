package com.demo.bookStoreApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.Book;
import com.demo.exceptions.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
public class BookController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    
	@Autowired
	public BookService bookService;
	
	//handler to Add a new book
	@PostMapping("/books")
	public Book addBook(@RequestBody Book emp)
	{
		logger.info("Adding new book: {}", emp.getTitle());
		return bookService.addBook(emp);
	}
	
	
	//handler to Retrieve all books.
	@RequestMapping(path="/books",method=RequestMethod.GET)
	public List<Book> getBooks()
	{
		logger.info("Fetching book list");
		return bookService.getAllBook();
	}

	
	//handler to Retrieve a specific book by ID.
	@RequestMapping(path="/books/{id}" ,method=RequestMethod.GET)
	public Book getBook(@PathVariable("id") int id)
	{
		logger.info("Fetching book with id: {}", id);
		try {
			return bookService.getBookById(id);
		} catch (Exception e) {
			logger.error("Book not found with id: {}", id);
			throw new BookNotFoundException("Book with id "+id+" not found!");
		}
		
	}
	
	
	//handler to Update an existing book.
	@RequestMapping(path="/books/{id}",method=RequestMethod.PUT)
	public Book updateBook(@PathVariable("id")int id,@RequestBody Book emp)
	{
		logger.info("Updating book with id: {}", id);
		try {
			
		return bookService.updateBook(id, emp);
		} catch (Exception e) {
			logger.error("Book with id {} not found, cannot update", id);
			throw new BookNotFoundException("Book With "+id+" unavailable to update");
		}
	}
	

	//handler to Delete a book by ID.
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable("id")int id)
	{
		logger.warn("Deleting book with id: {}", id);
		try {
			
			bookService.deleteBook(id);
		} catch (Exception e) {
			logger.error("Book with id {} not found, cannot delete", id);
			throw new BookNotFoundException("Book With "+id+" not available to delete");
		}
	}
	
	
	

}
