package com.example.make91.Bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.make91.Bookstore.model.Book;
import com.example.make91.Bookstore.model.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository repository) {
		return (args) -> {
			repository.save(new Book("The Girl on the Train", " Paula Hawkins", 2015, "9781594633669", 10));
			repository.save(new Book("Into the Water", " Paula Hawkins", 2017, "9780735211209", 20));
			System.out.println("===========LIST OF BOOKS==============");
			for (Book book : repository.findAll()) {
				System.out.println(book.toString());
			}
			System.out.println("=============BOOK WITH ID=1===========");
			System.out.println(repository.findOne(1L).toString());
			System.out.println("======================================");
		};
	}
}
