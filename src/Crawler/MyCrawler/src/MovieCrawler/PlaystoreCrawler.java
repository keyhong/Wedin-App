package MovieCrawler;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class PlaystoreCrawler {
	String URL = "https://play.google.com/store/movies/top";

	int size;
	String[] title;
	String[] price;
	WebDriver driver;
	Actions action;

	public PlaystoreCrawler(int size, WebDriver driver) {
		this.size = size;
		this.title = new String[size];
		this.price = new String[size];
		this.driver = driver;
		action = new Actions(driver);

	}

	public void crawl() throws Exception {
	System.out.println("플레이스토어 크롤링중 ");
		driver.get(URL);
		Thread.sleep(3000);
		for (int i = 0; i < 24; i++) {
			action.sendKeys(Keys.PAGE_DOWN).build().perform();
			action.sendKeys(Keys.PAGE_DOWN).build().perform();
			action.sendKeys(Keys.PAGE_DOWN).build().perform();
			action.sendKeys(Keys.PAGE_DOWN).build().perform();
			Thread.sleep(300);
		}
		List<WebElement> prices = driver.findElements(By.className("VfPpfd"));
		List<WebElement> titles = driver.findElements(By.className("WsMG1c"));
		for (int j = 0; j < size; j++) {
			price[j] = prices.get(j).getText().replaceAll("[^0-9]", "");
			title[j] = titles.get(j).getText();
			// driver.close();
			if(j%(size/10)==0) {
				System.out.print("-");
			}
		}
	}

	public String[] getTitle() {
		return title;
	}

	public String[] getPrice() {
		return price;
	}
}
