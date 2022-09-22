package com.yigit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

//Define all the REST methods in this class
@RestController
public class BookResource {

    //get the BookService
    //Auto connect the Bookservice instance to bookservice class
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.findAllBooks();
    }

    @GetMapping("/books/{bookId}")
    public Book retriveBook(@PathVariable int bookId){

        Book book =bookService.searchBook(bookId);

        if(book == null){
            throw new BookNotFoundException("Book not exsits for ID" +bookId);
        }

        return book;
    }

    @PostMapping("/books") //Add data
    public ResponseEntity<Object> createBook(@RequestBody Book book){

        Book saveBook = bookService.addBook(book);

        URI newBookLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{bookId}")
                .buildAndExpand(saveBook.getBookId())
                .toUri();

        return ResponseEntity.created(newBookLocation).build();
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable int id) {
        bookService.searchBook(id);
        if (book.getBookId() != id) {
            throw new BookNotFoundException("Book not exsits for ID" +id);
        }

        return bookService.updateBook(book);
    }

}
