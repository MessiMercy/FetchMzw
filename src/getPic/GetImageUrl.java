package getPic;

import java.util.Iterator;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetImageUrl {

	LinkedList<String> url = new LinkedList<>();

	// @SuppressWarnings("rawtypes")
	public void getUrl(String pageUrl) {
		try {
			Document doc = Jsoup.connect(pageUrl).timeout(60000)
					.userAgent("Mozilla").get();
			// String iconUrl = null;
			Elements links = doc.select("img[itemprop$=screenshot]");
			String iconUrl = doc.select("div.game_icon").select("img").first()
					.attr("src");
			url.add(iconUrl);
			int i = 0;
			for (Iterator<Element> iterator = links.iterator(); iterator
					.hasNext();) {
				Element element = iterator.next();
				String temp = element.attr("src");
				if (temp.contains("_m")) {
					url.push(stringControl(temp));
				}
				i++;
				if (i >= 7) {
					break;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(pageUrl + " 404,Õ¯÷∑Œ¥’“µΩ");
		}
	}

	private String stringControl(String temp) {
		String reslut = temp.replace("_m", "");
		return reslut;
	}
}
