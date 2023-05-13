package MovieCrawler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class MainApp {

	public static void main(String[] args) throws Exception {
		WebDriver psdriver;
		WebDriver naverdriver;
		
		//Firebase fb= new Firebase("D:\\google-services.json");
		Firebase fb=new Firebase("/Users/ohsg0/wedin-project-firebase-adminsdk-1rovf-38a4f16782.json");
		/*
		 * 드라이버설정
		 * 파이어폭스 설치 및 https://github.com/mozilla/geckodriver/releases/tag/v0.26. 에서 자기컴에 맞는 드라이버 설치 후 경로 설정 
		 */
		/*
		String driverPath = "/Users/choijinhap/geckodriver";
		System.setProperty("webdriver.gecko.driver", driverPath);
		
	
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		//firefoxOptions.addArguments("--headless");
		firefoxOptions.addArguments("--no-sandbox");//	driver = new ChromeDriver(chromeOptions);
		naverdriver = new FirefoxDriver(firefoxOptions);
		firefoxOptions.addArguments("--headless");
		psdriver=new FirefoxDriver(firefoxOptions);
		psdriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		psdriver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
		naverdriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		naverdriver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
		 */

		/*
		 * 네이버시리즈 청불 컨텐츠 떄문에 아이디와 패스워드필요 
		 * 
		 * */
		String naverID="ohsg0315";
		String naverPW="Aiden121728!";
		String driverPath = "/Users/ohsg0/geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", driverPath);
		naverdriver = new FirefoxDriver();
		NaverCrawler naver = new NaverCrawler(50,naverdriver,naverID,naverPW);
		naver.crawl();
//		fb.push(naver);
//		fb.end();
//		naverdriver.quit();
	
		/*
		 * 생성자 인자는 크롤링할 개수
		 * 네이버는 사이트 자체에 100위까지 밖에 없음
		 * 예스24,웨이브는 500위까지 테스트완료
		 * 플레이스토어는 200위까지 밖에 없음.
		 */
		
		/*
		NaverCrawler naver = new NaverCrawler(100,naverdriver,naverID,naverPW);

		Yes24Crawler yes24 = new Yes24Crawler(100);

		WavveCrawler wavve = new WavveCrawler(100);
		PlaystoreCrawler ps = new PlaystoreCrawler(100, psdriver);
		*/
		/* 
		 * 크롤러들 공통 변수
		 * 
		 * String[] title; 
		 * String[] price; 
		 * 
		 * --NaverCrawler만 가지고 있음--
		 * String[] imgURI; 
		 * String[] linkURL; 
		 * String[] content; 
		 * String[] type;
		 */
		

	 	//naver.crawl();

	 	/*
		yes24.crawl();
		wavve.crawl();
		ps.crawl();
		*/
		// 테스트 
		
		  for (int i = 0; i < 100; i++) { 
			  System.out.println(i);
			  System.out.println(naver.getTitle()[i] + " " + naver.getPrice()[i]);
		  }	
		/*
			System.out.println(yes24.getTitle()[i] + " " + yes24.getPrice()[i]);
		  System.out.println(wavve.getTitle()[i] + " " + wavve.getPrice()[i]);
		  System.out.println(ps.getTitle()[i] + " " + ps.getPrice()[i]);
		  System.out.println("======"); }
		*/
		/*
		naverdriver.quit();
		psdriver.quit();
		*/
	}

}
