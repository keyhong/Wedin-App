package MovieCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Yes24Crawler {
	String yes24URLh = "http://vod.yes24.com/MovieContents/NewList/NewMovie_List.aspx?PageNum=";
	String yes24URLf = "&SortCol=2&SortType=DESC&SearchCol=&SearchWord=&pageSize=&movieType=N&jType=&VIEW_GRD=&PRICE_GRD=&COUNTRY_CD=";

	int size;
	String[] title;
	String[] price;

	public Yes24Crawler(int size) {
		this.size = size;
		this.title = new String[size];
		this.price = new String[size];
	}

	public Elements getMovies(int pagenum) throws Exception {
		Document doc = Jsoup.connect(yes24URLh + pagenum + yes24URLf).get();
		Elements movies = doc.select(".contents tbody tbody tbody");
		return movies;
	}

	public void crawl() throws Exception {
		int idx = 0;
		System.out.println("예스24 크롤링중 ");
		for (int i = 1; i < (size/10)+2; i++) {
			Elements movies = getMovies(i);
			for (int j = 1; j < 20; j += 2) {
				if(idx>=size) {System.out.println(""); return;}
				Element element = movies.get(j);
				title[idx] = element.select(".b_list_t").text();
				price[idx] = element.select(".b_score").text().trim().split(" ")[1].replace("원", "");
				idx++;
				if(idx%(size/10)==0) {
					System.out.print("-");
				}
			}
		}
		System.out.println("");
	}

	public void test() throws Exception {
		Elements movies = getMovies(1);

		for (int i = 1; i < 20; i += 2) {
			Element element = movies.get(i);
			System.out.println("-----");
			System.out.println(element.select(".b_list_t").text());
			System.out.println(element.select(".b_score").text().trim().split(" ")[1].replace("원", ""));
			System.out.println("-----");
		}
	}

	public String[] getTitle() {
		return this.title;
	}

	public String[] getPrice() {
		return this.price;
	}
}
