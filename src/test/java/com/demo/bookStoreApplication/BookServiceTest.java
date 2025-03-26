package com.demo.bookStoreApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.entities.Book;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        testBook = new Book("The Game", "Triple H", new BigDecimal("29.99"), LocalDate.of(2023, 1, 1));
        testBook.setId(1L);

        Book book2 = new Book("Hajime no Ippo", "Hikomaru George", new BigDecimal("19.99"), LocalDate.of(2022, 6, 15));
        book2.setId(2L);

        bookList = Arrays.asList(testBook, book2);
    }

    @Test
    public void testGetAllBook() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(bookList);

        // Act
        List<Book> result = bookService.getAllBook();

        // Assert
        assertEquals(2, result.size());
        assertEquals(testBook.getTitle(), result.get(0).getTitle());
    }

    @Test
    public void testGetBookById_Success() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Book result = bookService.getBookById(1L);

        assertEquals(testBook.getId(), result.getId());
        assertEquals(testBook.getTitle(), result.getTitle());
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> bookService.getBookById(99L));
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book("Death Note", "Tsugumi Ohba", new BigDecimal("39.99"), LocalDate.of(2024, 3, 15));
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);

        Book result = bookService.addBook(newBook);

        // Assert
        assertEquals(newBook.getTitle(), result.getTitle());
        assertEquals(newBook.getAuthor(), result.getAuthor());
        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book("The Glory", "Arun", new BigDecimal("49.99"), LocalDate.of(2024, 3, 21));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);

        assertEquals(1L, updatedBook.getId());
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getAuthor(), result.getAuthor());
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}