package androeed;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FetchTargetText {

	/**
	 * @author mercy
	 * @return ����jsoup����ҳ���ȡָ��ֵ
	 * @param isAttrOrText
	 *            ����ɼ�ָ��attr������attr��ֵ������ɼ�text����Ϊnull
	 * @param rule
	 *            Ϊcssѡ�������ʽ����֧��һ��rule
	 */
	public static Stack<String> func(HttpClient client, String url, String rule, HashMap<String, String> headers,
			String isAttrOrText, String charset) {
		// StringBuilder result = new StringBuilder();
		Stack<String> result = new Stack<>();
		HttpGet get = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				get.setHeader(entry.getKey(), entry.getValue());
			}
		}
		RequestConfig.Builder config = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(5 * 1000);
		// get.setConfig(config);
		if (url.contains("google") || url.contains("apkpure") || url.contains("m=out")) {
			get.setConfig(config.setProxy(new HttpHost("127.0.0.1", 8087)).build());
		} else {
			get.setConfig(config.build());
		}
		System.out.println("--------------------");
		HttpResponse response = null;
		String html = null;
		try {
			response = client.execute(get);
			System.out.println("++++++++++++++++++++++");
			html = EntityUtils.toString(response.getEntity(), charset);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("��ȡ" + url + "����");
			e.printStackTrace();
		} finally {
			get.abort();
			System.out.println("*****************");
		}
		Document doc = null;
		if (html != null) {
			doc = Jsoup.parse(html);
			Elements elements = doc.select(rule);
			System.out.println("���ڲɼ�" + url + "��" + rule);
			// if (isAttrOrText != null) {
			// result.append(element.attr(isAttrOrText));
			// } else {
			// result.append(element.text());
			// }
			if (isAttrOrText != null) {
				for (Element element : elements) {
					result.push(element.attr(isAttrOrText));
				}
			} else {
				for (Element element : elements) {
					result.push(element.text());
				}
			}
		}
		System.out.println("///////////////////////");
		// CrawlerLib.printResult(html, false);
		return result;
	}

}
