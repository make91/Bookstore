package com.example.make91.Bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {

	@RequestMapping("/index")
	public String index() {

		return "index";

	}
}
