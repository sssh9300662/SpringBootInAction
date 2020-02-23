package readinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = ReadingListApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServerWebTests {

	private static FirefoxDriver browser;

	@Value("${local.server.port}")
	private int port;

	@BeforeAll
	public static void setBrowser() throws IOException {
		String canonicalPath = new File(".").getCanonicalPath();
		System.setProperty("webdriver.gecko.driver", canonicalPath + "\\src\\test\\java\\geckodriver.exe");
		browser = new FirefoxDriver();
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterAll
	public static void closeBrowser() {
		browser.quit();
	}

	@Test
	public void addBookToEmptyList() throws InterruptedException {
		String baseUrl = "https://localhost:" + port;
		browser.get(baseUrl);

		browser.findElementByName("username").sendKeys("craig");
		browser.findElementByName("password").sendKeys("password");
		browser.findElementByTagName("form").submit();
		//Wait until page is loaded
		(new WebDriverWait(browser, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().equals("Reading List");
			}
		});

		String currentUrl = browser.getCurrentUrl();
		assertEquals(baseUrl + "/readingList", currentUrl);
		assertEquals("You have no books in your book list", browser.findElementByTagName("div").getText());

		browser.findElementByName("title").sendKeys("BOOK TITLE");
		browser.findElementByName("author").sendKeys("BOOK AUTHOR");
		browser.findElementByName("isbn").sendKeys("1234567890");
		browser.findElementByName("description").sendKeys("DESCRIPTION");
		browser.findElementById("book-form").submit();

		WebElement dl = browser.findElementByCssSelector("dt.bookHeadline");
		assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)", dl.getText());
		WebElement dt = browser.findElementByCssSelector("dd.bookDescription");
		assertEquals("DESCRIPTION", dt.getText());
	}

}
