package com;

public class MultiDownloading implements Runnable {
	String downloadUrl;
	String fileName;

	// public static int workingThreadCount = 0;

	public MultiDownloading(String url, String fileName) {
		downloadUrl = url;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		// workingThreadCount++;
		DownloadSomething ds = new DownloadSomething();
		System.out.println("��ʼ����" + downloadUrl);
		if (downloadUrl.contains("apkpure")) {
			ds.downloadSomethingPure(downloadUrl, fileName);
		} else {
			ds.DownloadZip(downloadUrl, fileName);
		}
		System.out.println("����" + downloadUrl + "���");
		// workingThreadCount--;

	}

}
