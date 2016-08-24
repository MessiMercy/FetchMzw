package com;

import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CatchUrl {
	int i = 0;// ����i����֤�����ȡurlʧ�ܣ�ֻ����ִ��һ��

	public Stack<String> article(String url) {
		Document doc;
		System.out.println("�õ���Ϸ�б�");
		Stack<String> myStack = new Stack<>();
		try {
			doc = Jsoup.connect(url).timeout(60000).get();
			i++;// ��ʱi=1����ʾurl�ѻ�ȡ�ɹ�
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
			i--;// ��ȡ�ɹ���i��λ
			System.out.println("�õ��ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			if (i == 0) {
				System.out.println("get last url error");
				article(url);
			} else {
				System.out.println("������һ�λ����Ҳ���");
				return null;
			}
		}
		return myStack;

	}

	public static Stack<String> CheckAvailableUrlToDownload(Stack<String> oldStack, Stack<String> newStack) {
		System.out.println("����¾�ҳ�������");
		Stack<String> result = new Stack<>();
		while (!newStack.isEmpty()) {
			String temp = newStack.pop();
			if (!oldStack.contains(temp)) {
				result.push(temp);
			}
		}
		System.out.println("�¾�ҳ�����������");
		// �Ա�����stack����stack popһ����������stack
		// pop��һ���Աȣ�������������stack��pop��ֱ���¾�pop��������ȣ�Ȼ��˫��ͬʱpop����stackΪ��ʱ����stackʣ�µļ�Ϊ���µġ�
		// Ȼ����֪���Ϸ���Ϊ�������д��·�������stack popһ�����Ƿ��ھ�stack�������ڣ������»�
		return result;

	}

}
