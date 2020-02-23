package readinglist;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ReadingListApplication.class) //Specify webEnvironment in argument you can test that on web container env
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class MockMvcWebTests {

	@Autowired
	WebApplicationContext webContext;

	private MockMvc mockMvc;

	private Reader expectedReader;

	@Autowired
	public UserDetailsService userDetailsService = new CustomeUserDetailService();

	@BeforeEach
	public void setupMockMvc() {
		// produce a MockMvc instance, which is assigned to an instance variable for test methods to use.
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity()).build();
		expectedReader = new Reader();
		expectedReader.setUsername("craig");
		expectedReader.setPassword("{noop}password");
		expectedReader.setFullname("Craig Walls");
	}

	@Test
	public void homePage_unauthenticatedUser() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().is3xxRedirection()).andExpect(header().string("Location", "http://localhost/login"));
	}

	@Test
	@WithUserDetails("craig")
	public void homePage() throws Exception {
		mockMvc.perform(get("/readingList")).andExpect(status().isOk()).andExpect(view().name("readingList"))
				.andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader))).andExpect(model().attributeExists("books"))
				.andExpect(model().attribute("books", is(empty())));
	}

	@Test
	@WithUserDetails("craig")
	public void postBook() throws Exception {
		mockMvc.perform(post("/readingList").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("title", "BOOK TITLE")
				.param("author", "BOOK AUTHOR").param("isbn", "1234567890").param("description", "DESCRIPTION"))
				.andExpect(status().is3xxRedirection()).andExpect(header().string("Location", "/readingList"));
		Book expectedBook = new Book();
		expectedBook.setId(1L);
		expectedBook.setReader(expectedReader);
		expectedBook.setTitle("BOOK TITLE");
		expectedBook.setAuthor("BOOK AUTHOR");
		expectedBook.setIsbn("1234567890");
		expectedBook.setDescription("DESCRIPTION");

		mockMvc.perform(get("/readingList")).andExpect(status().isOk()).andExpect(view().name("readingList"))
				.andExpect(model().attributeExists("books")).andExpect(model().attribute("books", hasSize(1)))
				.andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook, "reader"))));

	}
}
