package MovieCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class WavveCrawler {
	String URLh = "https://apis.pooq.co.kr/cf/movie/contents?WeekDay=all&adult=n&broadcastid=161966&came=movie&contenttype=movie&genre=all&limit=";
	String URLf = "&offset=0&orderby=viewtime&page=1&price=all&sptheme=svod&uiparent=FN0&uirank=0&uitype=MN85&apikey=E5F3E0D30947AA5440556471321BB6D9&credential=none&device=pc&drm=wm&partner=pooq&pooqzone=none&region=kor&targetage=auto";

	String detailURL1 = "https://apis.pooq.co.kr/cf/purchase/products?channelid=none&contentid=";
	String detailURL2 = "&contenttype=movie&cpid=CA01&isplayy=none&programid=";
	String detailURL3 = "&recommendvoucher=none&vouchertype=use&apikey=E5F3E0D30947AA5440556471321BB6D9&credential=none&device=pc&drm=wm&partner=pooq&pooqzone=none&region=kor&targetage=auto";

	int size;
	String[] title;
	String[] price;

	public WavveCrawler(int size) {

		this.size = size;
		this.title = new String[size];
		this.price = new String[size];
	}

	public void crawl() throws Exception {
		Document doc = Jsoup.connect(URLh + size + URLf).ignoreContentType(true)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
		JSONParser parser = new JSONParser();
		JSONObject jObject = (JSONObject) parser.parse(doc.text());
		JSONObject ct = (JSONObject) jObject.get("cell_toplist");
		JSONArray cl = (JSONArray) ct.get("celllist");
		System.out.println("웨이브 크롤링");
		for (int i = 0; i < cl.size(); i++) {
			JSONObject tl = (JSONObject) cl.get(i);
			JSONArray ti = (JSONArray) tl.get("title_list");
			JSONObject title_list = (JSONObject) ti.get(0);
			title[i] = title_list.get("text").toString();

			JSONArray el = (JSONArray) tl.get("event_list");

			JSONObject bl = (JSONObject) el.get(0);
			JSONArray bodylist = (JSONArray) bl.get("bodylist");
			String uicode = bodylist.get(3).toString().replace("uicode:", "");
			String detailURL = detailURL1 + uicode + detailURL2 + uicode + detailURL3;

			Document detailDoc = Jsoup.connect(detailURL).ignoreContentType(true)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
			JSONArray da = (JSONArray) parser.parse(detailDoc.text());

			JSONObject product_info = (JSONObject) da.get(3);

			JSONArray pg = (JSONArray) product_info.get("productgroups");
			if (pg.size() > 0) {
				JSONObject product = (JSONObject) pg.get(0);
				JSONArray infos=(JSONArray)product.get("products");
				JSONObject proinfo=(JSONObject)infos.get(0);
				JSONArray con=(JSONArray)proinfo.get("concurrency");
				JSONObject concurrency=(JSONObject)con.get(0);
				price[i]=concurrency.get("amount").toString();
			}
			else {
				price[i]="7900";
			}

			/*
			 * JSONObject ui=(JSONObject) el.get(1); String deurl=ui.get("url").toString();
			 * System.out.println(deurl);
			 */
			if(i%(size/10)==0) {
				System.out.print("-");
			}
		}
		System.out.println(" ");
	}

	public String[] getTitle() {
		return title;
	}

	public String[] getPrice() {
		return price;
	}
}
