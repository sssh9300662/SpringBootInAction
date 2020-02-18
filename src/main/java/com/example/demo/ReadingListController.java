package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/reader")
public class ReadingListController {
	private ReadingListRepository readingListRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository) {
		this.readingListRepository = readingListRepository;
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readersBooks(@PathVariable("reader") String reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
		}
		return "readingList";// as the logical name of the view to render the model. 
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) {
		System.out.println("test");
		book.setReader(reader);
		readingListRepository.save(book);
		System.out.println("test");
		return "redirect:/{reader}";//handle by other controller method
	}

}
