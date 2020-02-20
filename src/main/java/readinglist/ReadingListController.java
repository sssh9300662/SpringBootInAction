package readinglist;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {

	private static final Logger logger = LoggerFactory.getLogger(ReadingListController.class);

	private ReadingListRepository readingListRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository) {
		this.readingListRepository = readingListRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String readersBooks(Reader reader, Model model) {
		logger.info("Start get reader {}'s reading list", reader.getUsername());
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
		}
		return "readingList";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addToReadingList(Reader reader, Book book) {
		logger.info("Start insert book for reader {}", reader.getUsername());
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/readingList";
	}

}
