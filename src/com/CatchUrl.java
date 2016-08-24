package com;

import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CatchUrl {
	int i = 0;// 设置i，保证如果获取url失败，只重新执行一次

	public Stack<String> article(String url) {
		Document doc;
		System.out.println("得到游戏列表");
		Stack<String> myStack = new Stack<>();
		try {
			doc = Jsoup.connect(url).timeout(60000).get();
			i++;// 此时i=1，表示url已获取成功
			Elements ListDiv = doc.getElementsByAttributeValue("class", "gamelist pt10 pb20 pl10");
			for (Element element : ListDiv) {
				// Elements links = element.getElementsByAttributeValue(
				// "itemprop", "softwareVersion");
				Elements links = element.getElementsByTag("a");
				for (Element link : links) {
					String linkText = link.attr("href");
					if (!linkText.equals("") && !myStack.contains(linkText)) {
						myStack.push(linkText);
					}
				}
			}
			i--;// 获取成功后将i归位
			System.out.println("得到成功");
		} catch (Exception e) {
			e.printStackTrace();
			if (i == 0) {
				System.out.println("get last url error");
				article(url);
			} else {
				System.out.println("重找了一次还是找不到");
				return null;
			}
		}
		return myStack;

	}

	public static Stack<String> CheckAvailableUrlToDownload(Stack<String> oldStack, Stack<String> newStack) {
		System.out.println("检查新旧页面的区别");
		Stack<String> result = new Stack<>();
		while (!newStack.isEmpty()) {
			String temp = newStack.pop();
			if (!oldStack.contains(temp)) {
				result.push(temp);
			}
		}
		System.out.println("新旧页面区别检查完成");
		// 对比两个stack，新stack pop一个出来与老stack
		// pop出一个对比，如果不相等则老stack再pop，直至新旧pop出来的相等，然后双方同时pop，老stack为空时，新stack剩下的即为更新的。
		// 然而不知以上方法为何总是有错，新方法是新stack pop一个看是否在旧stack里，如果不在，即是新货
		return result;

	}

}
