package com.bookxchange.customExceptions;

public class BooksExceptions extends RuntimeException{


    public BooksExceptions() {
        super();
    }

    public BooksExceptions(String message){
        super(message);
    }

}
