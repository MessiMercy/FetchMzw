package getPic;

import com.Apkpure;

public class StartDownload {

	public void DownloadPic(String pageage) {
		// TODO �Զ����ɵķ������
		GetImageUrl gu = new GetImageUrl();
		Apkpure apkIcon = new Apkpure(pageage);
		// apkIcon.GetHtml();
		String iconUrl = apkIcon.GetIconLink();
		System.out.println("icon url is " + iconUrl);
		if (pageage.contains("mzw") || pageage.contains("muzhiwan")) {
			return;
		}
		try {
			gu.getUrl("http://www.muzhiwan.com/" + pageage + ".html");
			// Thread[] singleThread = new Thread[4];
			// while (GetImageUrl.url.size() > 1) {
			// int i = 0;
			// String st = GetImageUrl.url.pop();
			// if (isIcon != 1) {
			// singleThread[i] = new Thread(new MultiDownload(1, st,
			// pageage));
			// singleThread[i].start();
			// i++;
			// // GetImage.main(1, st);
			// }
			// }
			while (!gu.url.isEmpty()) {
				String url = gu.url.pop();
				if (url.contains("124")) {
					// System.out.println(111);
					if (iconUrl == null || iconUrl.length() <= 5) {
						iconUrl = url;
					}
					new Thread(new MultiDownload(0, iconUrl, pageage, null)).start();
				} else {
					new Thread(new MultiDownload(1, url, pageage, null)).start();
				}
			}
			// String iconUrl = GetImageUrl.url.pop();
			// // GetImage.main(0, iconUrl);
			// Thread myThread1 = new Thread(
			// new MultiDownload(0, iconUrl, pageage));
			// myThread1.start();
			Thread.sleep(5000);
			// int i = 0;
			// while (i < 20) {
			// Thread.sleep(20000);// ����������ͼƬ���߳̽�������ִ��imageio
			// if (MultiDownload.workingThread == 0) {
			// System.out.println("��������ͼƬ�Ѿ�������ɣ���ʼת��ͼƬ");
			// imageIO.O0oo.main(new String[] { pageage, "" });
			// System.out.println("converting image finished");
			// break;
			// }
			// i++;
			// }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���������");
		}
	}
}
