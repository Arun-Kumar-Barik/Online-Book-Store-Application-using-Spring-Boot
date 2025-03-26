package com.demo.bookStoreApplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.entities.Book;
import com.demo.exceptions.BookNotFoundException;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book("Hajime no ippo", "Hikomaku george", new BigDecimal("29.99"), LocalDate.of(2003, 1, 1));
        testBook.setId(1L);
    }

    @Test
    public void testGetBooks() {
        when(bookService.getAllBook()).thenReturn(List.of(testBook));

        List<Book> result = bookController.getBooks();

        assertEquals(1, result.size());
        assertEquals(testBook, result.get(0));
        verify(bookService).getAllBook();
    }

    @Test
    public void testGetBook_Success() {
        when(bookService.getBookById(1L)).thenReturn(testBook);

        Book result = bookController.getBook(1);

        assertEquals(testBook, result);
        verify(bookService).getBookById(1L);
    }

    @Test
    public void testGetBook_NotFound() {
        when(bookService.getBookById(99L)).thenThrow(new BookNotFoundException("Book with id 99 not found!"));

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookController.getBook(99));

        assertEquals("Book with id 99 not found!", exception.getMessage());
        verify(bookService).getBookById(99L);
    }

    @Test
    public void testAddBook() {
        when(bookService.addBook(any())).thenReturn(testBook);

        Book result = bookController.addBook(testBook);

        assertEquals(testBook, result);
        verify(bookService).addBook(any());
    }

    @Test
    public void testUpdateBook_Success() {
        when(bookService.updateBook(eq(1L), any())).thenReturn(testBook);

        Book result = bookController.updateBook(1, testBook);

        assertEquals(testBook, result);
        verify(bookService).updateBook(eq(1L), any());
    }

    @Test
    public void testUpdateBook_NotFound() {
        when(bookService.updateBook(eq(99L), any())).thenThrow(new BookNotFoundException("Book with id 99 not found!"));

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookController.updateBook(99, testBook));

        assertEquals("Book With 99 unavailable to update", exception.getMessage());
        verify(bookService).updateBook(eq(99L), any());
    }

    @Test
    public void testDeleteBook_Success() {
        doNothing().when(bookService).deleteBook(1L);

        bookController.deleteBook(1);

        verify(bookService).deleteBook(1L);
    }

    @Test
    public void testDeleteBook_NotFound() {
        doThrow(new BookNotFoundException("Book with id 99 not found!")).when(bookService).deleteBook(99L);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookController.deleteBook(99));

        assertEquals("Book With 99 not available to delete", exception.getMessage());
        verify(bookService).deleteBook(99L);
    }
}
