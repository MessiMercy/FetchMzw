package com;

import java.io.IOException;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CatchSecondUrl {
	int i = 0;
	private String fileName;

	public Stack<String> mySecondUrl(String url) {
		setFileName(url.substring(24, url.length() - 5));
		System.out.println("开始找其二级url");
		Stack<String> myStack = new Stack<>();
		if (fileName.contains("mzw") || fileName.contains("muzhiwan")) {
			System.out.println(url + "是合作游戏，跳过！");
			return myStack;
		}// 如果是拇指玩的合作包即忽略下载
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			i++;
			String temp = doc.select("p[itemprop$=description]").first().text();
			int i = temp.indexOf(")") + 2;
			int j = temp.indexOf("开发的");
			String b = temp.substring(i, j);// 得到厂商名字
			if (isChinese(b)) {
				System.out.println(url + "是国产游戏，跳过！");
				return myStack;
			}
			Elements ListDiv = doc.getElementsByAttributeValue("class",
					"detail_dbtn detail_way_t");
			for (Element element : ListDiv) {
				// Elements links = element.getElementsByAttributeValue(
				// "itemprop", "softwareVersion");
				Elements links = element.getElementsByTag("a");
				for (Element link : links) {
					String linkText = link.attr("href");
					if (linkText.contains("downloadstat")
							|| linkText.contains("letv")) {
						myStack.push(urlControl(linkText));
					}
				}
			}
			i--;
			System.out.println("二级url已找到");
		} catch (IOException e) {
			e.printStackTrace();
			if (i == 0) {
				mySecondUrl(url);
			} else {
				System.out.println("get second url error");
				return null;
			}
		}
		return myStack;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String urlControl(String source) {
		if (source.contains("gpk")) {
			return source;
		}

		return "http://www.muzhiwan.com" + source;

	}

	private boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);// 返回该char的类型，如是Unicode，阿拉伯数字等等
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	private boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

}
