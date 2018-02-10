package com.example.make91.Bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.make91.Bookstore.domain.Book;
import com.example.make91.Bookstore.domain.BookRepository;
import com.example.make91.Bookstore.domain.User;
import com.example.make91.Bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository brepository, UserRepository urepository) {
		return (args) -> {
			brepository.save(new Book("The Girl on the Train", " Paula Hawkins", 2015, "9781594633669", 10));
			brepository.save(new Book("Into the Water", " Paula Hawkins", 2017, "9780735211209", 20));
			System.out.println("===========LIST OF BOOKS==============");
			for (Book book : brepository.findAll()) {
				System.out.println(book.toString());
			}
			System.out.println("=============BOOK WITH ID=1===========");
			System.out.println(brepository.findOne(1L).toString());
			System.out.println("======================================");
			
			// Create users: make91/asdf1 admin/Admin1234!
			User user1 = new User("make91", "$2a$10$v3Yqcay/P7.w0kEVcVvJ/uCObI8QcDNMxSEKkzvmTBJyOtdvTBjl6", "make91@m.com", "USER");
			User user2 = new User("admin", "$2a$10$Fciq2HqH/zJuGVMxX0dTHeKcV1zUa2izRu0Og1jzx3LyXdY4fLG06", "admin@m.com", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			for (User user : urepository.findAll()) {
				System.out.println("Added user " + user.getUsername());
			}
		};
	}
}
