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

		while (true) {// 死循环至手动停止
			Calendar calendar = Calendar.getInstance();
			try {
				System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
				if (calendar.get(Calendar.HOUR_OF_DAY) <= 18 || calendar.get(Calendar.HOUR_OF_DAY) >= 22) {
					// logger.info("-----------现在不是上班时间");
					Thread.sleep(300000);
					continue;
				}
				// logger.info("开始上班，每隔5分钟检测一次");
				Stack<String> oldStack = cUrl.article("http://www.muzhiwan.com/category/12/");// 获得一次游戏列表
				System.out.println(oldStack.isEmpty());
				System.out.println(oldStack.peek());
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String hehe = dateFormat.format(now);
				// System.out.println();
				System.out.println("现在是" + hehe + " ");
				System.out.println("睡眠五分钟");
				Thread.sleep(300000);// 休息5分钟
				System.out.println("睡眠完成");
				Stack<String> newStack = cUrl.article("http://www.muzhiwan.com/category/12/");// 再获得一次5分钟后的列表
				System.out.println(newStack.isEmpty());
				Stack<String> downloadStack = CatchUrl.CheckAvailableUrlToDownload(oldStack, newStack);// 检查更新的存入新列表
				if (downloadStack.isEmpty()) {

					System.out.println("这一段时间并没有新货");
					// logger.info("检查完一次页面，发现并没有新货");
					continue;
				}
				while (!downloadStack.isEmpty()) {
					String DownloadUrl = downloadStack.pop();
					Stack<String> sss = csl.mySecondUrl(DownloadUrl);
					String fileName = DownloadUrl.substring(24, DownloadUrl.length() - 5);
					if (!sss.isEmpty()) {
						StartDownload downloadPic = new StartDownload();
						downloadPic.DownloadPic(fileName);
					} // 截取包名作文件名和文件夹名
					while (!sss.isEmpty()) {
						Thread aaa = new Thread(new MultiDownloading(sss.pop(), fileName));// 新建一个线程下载apk
						aaa.start();
					} // 定时发邮件待添加
				}
				// logger.info("检查完一次页面，发现新货" + downloadStack.size() + "个");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
