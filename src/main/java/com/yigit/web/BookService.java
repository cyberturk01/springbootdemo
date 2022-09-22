package com.yigit.web;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BookService {
    //Create a list to store books
    private static List<Book> books = new ArrayList<>();
    private static int bookCount =3;

    //Add books to the list
    static {
        books.add(new Book(1,"Harry potter",new Date()));
        books.add(new Book(2,"Lord of the Rings",new Date()));
        books.add(new Book(3,"Song of Ice and fire",new Date()));
    }

    public List<Book> findAllBooks(){
        return books;
    }

    public Book addBook(Book book){
        if(book.getBookId() == null){
            book.setBookId(bookCount++);
        }

        books.add(book);
        return book;

    }
    public Book searchBook(int id){
        for(Book book: books){
            if(book.getBookId() ==id){
                return book;
            }
        }
        return  null;
    }

    public Book updateBook(Book book){
        Book oldBook = searchBook(book.getBookId());
        oldBook.setBookName(book.getBookName());
        return oldBook;
    }
}
