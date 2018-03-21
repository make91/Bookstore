package com.example.make91.Bookstore.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.make91.Bookstore.domain.Book;
import com.example.make91.Bookstore.domain.BookRepository;

@Controller
public class BookController {
	@Autowired
	private BookRepository repository;
	
	@RequestMapping(value="/login")
   	public String login() {	
        	return "login";
    }

    @RequestMapping(value =  "")
	public String indexRedirect() {
		return "redirect:/booklist";
	}

	@RequestMapping(value = { "/booklist", "/index" })
	public String bookList(Model model) {
      model.addAttribute("books", repository.findAll());
      return "index";
	}

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public @ResponseBody List<Book> bookListRest() {
		return (List<Book>) repository.findAll();
	}
	
	@RequestMapping(value="/book/{id}", method = RequestMethod.GET)
    public @ResponseBody Book findBookRest(@PathVariable("id") Long bookId) {	
    	return repository.findOne(bookId);
    }

	@RequestMapping(value = "/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		return "addbook";
	}
	
	@RequestMapping(value = "/books", method = RequestMethod.POST)
	public @ResponseBody Book addBookRest(@RequestBody Book book) {
    	return repository.save(book);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')") //only admin can delete books
	@RequestMapping(value = "/api/books/{id}", method = RequestMethod.DELETE)
	public void deleteBook(@PathVariable("id") Long bookId, Model model) {
		repository.delete(bookId);
        System.out.println("Deleted book with Id " + bookId);
	}
}
