package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Apkpure {
	private int retryTimes = 0;// ��icon����ʧ�ܵ�ʱ���������Դ�����
	private static final String apkdownloadLinkUrl = "http://apkpure.com/store/apps/details?id=";
	private String html;
	static CloseableHttpClient httpClient = getInstanceClient(true);
	private String suffix = ".apk";
	private Document doc;
	private String packageName;

	public Apkpure(String packageName) {
		this.packageName = packageName;
		this.GetHtml();
	}

	public String getHtml() {
		return html;
	}

	private static CloseableHttpClient getInstanceClient(boolean isProxy) {
		CloseableHttpClient httpClient;
		HttpHost proxy = new HttpHost("127.0.0.1", 8087);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		if (isProxy) {
			httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
		} else {
			httpClient = HttpClients.custom()
					.setUserAgent(
							"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
					.build();
		}
		return httpClient;
	}

	public void GetHtml() {
		try {

			String url = apkdownloadLinkUrl + packageName;
			// CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity);
			doc = Jsoup.parse(html);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String GetIconLink() {
		return doc.select("img[itemprop=image]").attr("src").replace("130", "256");
	}

	public void DownloadVerifyCode(String url, boolean isIcon, String imgUrl) {
		HttpGet getimg = null;
		if (isIcon) {
			getimg = new HttpGet(GetIconLink());
		} else {
			getimg = new HttpGet(imgUrl);
		}
		getimg.setConfig(RequestConfig.custom().setProxy(new HttpHost("127.0.0.1", 8087)).build());
		// httpClient.get
		try {
			HttpResponse response = httpClient.execute(getimg);
			System.out.println("״̬��Ϊ�� " + response.getStatusLine().getStatusCode());
			InputStream in = response.getEntity().getContent();
			File dir = new File(url);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File verifyCode = null;
			if (isIcon) {
				verifyCode = new File(url + "\\icon.png");
			} else {
				verifyCode = new File(dir, new Random().nextInt(10000) + ".png");
			}
			if (!verifyCode.exists()) {
				verifyCode.createNewFile();
				// verifyCode.deleteOnExit();
			}
			FileOutputStream fo = new FileOutputStream(verifyCode);
			byte[] tmpBuf = new byte[1024];
			int bufLen = 0;
			// long downloadedSize = 0;
			System.out.println("��ʼ����" + url + "��ͼƬ");
			while ((bufLen = in.read(tmpBuf)) > 0) {
				fo.write(tmpBuf, 0, bufLen);
				// downloadedSize += bufLen;
			}
			System.out.println(url + "icon" + "��ͼƬ�������");
			fo.close();
		} catch (ClientProtocolException e) {
			System.out.println(url + "��������");
			retryTimes++;
			if (retryTimes <= 2) {
				System.out.println("��������" + url + "��" + retryTimes + "��");
				DownloadVerifyCode(url, isIcon, imgUrl);
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			retryTimes++;
			if (retryTimes <= 2) {
				System.out.println("��������" + url + "��" + retryTimes + "��");
				DownloadVerifyCode(url, isIcon, imgUrl);
			}
		} finally {
			getimg.abort();
		}

	}

	public List<String> GetScreenShotLinks() {
		List<String> imagelist = new ArrayList<>();
		Elements elements = doc.select("a[class=acolorbox]");
		for (Element element : elements) {
			imagelist.add(element.attr("href").replace("500", "900"));
		}
		return imagelist;
	}

	public void downloadSomething(String url, String fileName) {
		HttpGet getimg = new HttpGet(url);
		// httpClient.get
		try {
			HttpResponse response = httpClient.execute(getimg);
			System.out.println(fileName + "��״̬��Ϊ�� " + response.getStatusLine().getStatusCode());
			InputStream in = response.getEntity().getContent();
			File dir = new File("download");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File verifyCode = new File("download" + "\\" + fileName);
			if (!verifyCode.exists()) {
				verifyCode.createNewFile();
				// verifyCode.deleteOnExit();
			}
			FileOutputStream fo = new FileOutputStream(verifyCode);
			byte[] tmpBuf = new byte[1024];
			int bufLen = 0;
			// long downloadedSize = 0;
			System.out.println("��ʼ����" + url + "");
			while ((bufLen = in.read(tmpBuf)) > 0) {
				fo.write(tmpBuf, 0, bufLen);
				// downloadedSize += bufLen;
			}
			System.out.println(url + "�������");
			fo.close();
		} catch (ClientProtocolException e) {
			System.out.println(url + "��������");
			retryTimes++;
			if (retryTimes <= 2) {
				System.out.println("��������" + url + "��" + retryTimes + "��");
				downloadSomething(url, fileName);
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			retryTimes++;
			if (retryTimes <= 2) {
				System.out.println("��������" + url + "��" + retryTimes + "��");
				downloadSomething(url, fileName);
			}
		} finally {
			getimg.abort();
		}
	}

	public String GetDownloadlink() throws NullPointerException {
		// return doc.select("a[class=down ga]").attr("href");
		Element downloadElement = doc.select("a.da").first();
		if (downloadElement.hasText()
				&& (downloadElement.text().contains("xapk") || downloadElement.text().contains("XAPK"))) {
			setSuffix(".xapk");
		}
		String downloadPage = "https://apkpure.com" + downloadElement.attr("href");
		System.out.println(downloadPage);
		Document document = null;
		HttpGet get = null;
		String downloadLink = null;
		try {
			get = new HttpGet(downloadPage);
			get.setHeader("referer", apkdownloadLinkUrl);
			HttpResponse response = httpClient.execute(get);
			String html = EntityUtils.toString(response.getEntity());
			document = Jsoup.parse(html);
			// document = Jsoup.connect(downloadPage).header("referer",
			// apkdownloadLinkUrl).timeout(1000)
			// .userAgent(
			// "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML,
			// like Gecko) Chrome/51.0.2704.103 Safari/537.36")
			// .get();
			downloadLink = document.select("a.ga").first().attr("href");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			get.abort();
		}
		return downloadLink;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
