package com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import getPic.StartDownload;

public class MIAN {
	// public static Logger logger = Logger.getLogger(MIAN.class);

	public static void main(String[] args) {
		// PropertyConfigurator.configure("log4j.properties");
		CatchSecondUrl csl = new CatchSecondUrl();
		CatchUrl cUrl = new CatchUrl();

		while (true) {// ��ѭ�����ֶ�ֹͣ
			Calendar calendar = Calendar.getInstance();
			try {
				System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
				if (calendar.get(Calendar.HOUR_OF_DAY) <= 18 || calendar.get(Calendar.HOUR_OF_DAY) >= 22) {
					// logger.info("-----------���ڲ����ϰ�ʱ��");
					Thread.sleep(300000);
					continue;
				}
				// logger.info("��ʼ�ϰ࣬ÿ��5���Ӽ��һ��");
				Stack<String> oldStack = cUrl.article("http://www.muzhiwan.com/category/12/");// ���һ����Ϸ�б�
				System.out.println(oldStack.isEmpty());
				System.out.println(oldStack.peek());
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String hehe = dateFormat.format(now);
				// System.out.println();
				System.out.println("������" + hehe + " ");
				System.out.println("˯�������");
				Thread.sleep(300000);// ��Ϣ5����
				System.out.println("˯�����");
				Stack<String> newStack = cUrl.article("http://www.muzhiwan.com/category/12/");// �ٻ��һ��5���Ӻ���б�
				System.out.println(newStack.isEmpty());
				Stack<String> downloadStack = CatchUrl.CheckAvailableUrlToDownload(oldStack, newStack);// �����µĴ������б�
				if (downloadStack.isEmpty()) {

					System.out.println("��һ��ʱ�䲢û���»�");
					// logger.info("�����һ��ҳ�棬���ֲ�û���»�");
					continue;
				}
				while (!downloadStack.isEmpty()) {
					String DownloadUrl = downloadStack.pop();
					Stack<String> sss = csl.mySecondUrl(DownloadUrl);
					String fileName = DownloadUrl.substring(24, DownloadUrl.length() - 5);
					if (!sss.isEmpty()) {
						StartDownload downloadPic = new StartDownload();
						downloadPic.DownloadPic(fileName);
					} // ��ȡ�������ļ������ļ�����
					while (!sss.isEmpty()) {
						Thread aaa = new Thread(new MultiDownloading(sss.pop(), fileName));// �½�һ���߳�����apk
						aaa.start();
					} // ��ʱ���ʼ������
				}
				// logger.info("�����һ��ҳ�棬�����»�" + downloadStack.size() + "��");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
