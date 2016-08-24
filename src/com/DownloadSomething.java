package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

public class DownloadSomething {
	int retry = 0;

	public void DownloadZip(String url, String fileName) {
		String suffix = ".apk";
		FileOutputStream fo = null;
		InputStream in = null;
		try {
			retry++;
			System.out.println("找到一条下载url并开始下载");
			if (url.contains("gpk")) {
				suffix = ".gpk";
				System.out.println("发现一个GPK，需要等其5分钟同步，再开始下载");
				Thread.sleep(300000);
				System.out.println("应该同步好了吧，开始下载");
			}
			URL uri = new URL(url);
			// uri.
			// String fileName = String.valueOf(rr.nextInt(10000)) + ".apk";
			// HttpURLConnection huccc = ;
			URLConnection huc = uri.openConnection();
			huc.setConnectTimeout(60000);
			huc.setReadTimeout(60000);
			// HttpURLConnection hucc = (HttpURLConnection) huc;
			// hucc.connect();
			// int resposeCode = hucc.getResponseCode();
			// if (resposeCode==400) {
			// System.out.println("发现一个gpk，");
			// }
			// InputStream in = uri.openStream();
			in = huc.getInputStream();
			File dir = new File(fileName);
			if (!dir.exists()) {
				dir.mkdir();
			}
			fo = new FileOutputStream(new File(fileName + "\\" + fileName + suffix));
			byte[] buf = new byte[1024];
			int length = 0;
			System.out.println("开始下载:" + fileName);
			while ((length = in.read(buf, 0, buf.length)) != -1) {
				fo.write(buf, 0, length);
			}

			System.out.println(fileName + "下载完成");
			retry--;// 如果出问题必定是time out，但是重试几次估计能联通
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("下载第" + retry + "次" + fileName + "失败");
			if (retry <= 2) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("重试第" + retry + "次");
				DownloadZip(url, fileName);
			}
		} finally {
			try {
				fo.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void downloadSomethingPure(String url, String fileName) {
		HttpGet getimg = new HttpGet(url);
		// httpClient.get
		try {
			HttpResponse response = Apkpure.httpClient.execute(getimg);
			System.out.println(fileName + "的状态码为： " + response.getStatusLine().getStatusCode());
			InputStream in = response.getEntity().getContent();
			File dir = new File(fileName.substring(0, fileName.length() - 4));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File verifyCode = new File(fileName.substring(0, fileName.length() - 4) + "\\" + fileName);
			if (!verifyCode.exists()) {
				verifyCode.createNewFile();
				// verifyCode.deleteOnExit();
			}
			FileOutputStream fo = new FileOutputStream(verifyCode);
			byte[] tmpBuf = new byte[1024];
			int bufLen = 0;
			// long downloadedSize = 0;
			System.out.println("开始下载" + fileName + "");
			while ((bufLen = in.read(tmpBuf)) > 0) {
				fo.write(tmpBuf, 0, bufLen);
				// downloadedSize += bufLen;
			}
			System.out.println(fileName + "下载完成");
			fo.close();
		} catch (ClientProtocolException e) {
			System.out.println(url + "出了问题");
			retry++;
			if (retry <= 2) {
				System.out.println("重试下载" + url + "第" + retry + "次");
				downloadSomethingPure(url, fileName);
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			retry++;
			if (retry <= 2) {
				System.out.println("重试下载" + url + "第" + retry + "次");
				downloadSomethingPure(url, fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retry++;
			if (retry <= 2) {
				System.out.println("重试下载" + url + "第" + retry + "次");
				downloadSomethingPure(url, fileName);
			}
		} finally {
			getimg.abort();
		}
	}
}
