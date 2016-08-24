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
			System.out.println("�ҵ�һ������url����ʼ����");
			if (url.contains("gpk")) {
				suffix = ".gpk";
				System.out.println("����һ��GPK����Ҫ����5����ͬ�����ٿ�ʼ����");
				Thread.sleep(300000);
				System.out.println("Ӧ��ͬ�����˰ɣ���ʼ����");
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
			// System.out.println("����һ��gpk��");
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
			System.out.println("��ʼ����:" + fileName);
			while ((length = in.read(buf, 0, buf.length)) != -1) {
				fo.write(buf, 0, length);
			}

			System.out.println(fileName + "�������");
			retry--;// ���������ض���time out���������Լ��ι�������ͨ
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���ص�" + retry + "��" + fileName + "ʧ��");
			if (retry <= 2) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("���Ե�" + retry + "��");
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
			System.out.println(fileName + "��״̬��Ϊ�� " + response.getStatusLine().getStatusCode());
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
			System.out.println("��ʼ����" + fileName + "");
			while ((bufLen = in.read(tmpBuf)) > 0) {
				fo.write(tmpBuf, 0, bufLen);
				// downloadedSize += bufLen;
			}
			System.out.println(fileName + "�������");
			fo.close();
		} catch (ClientProtocolException e) {
			System.out.println(url + "��������");
			retry++;
			if (retry <= 2) {
				System.out.println("��������" + url + "��" + retry + "��");
				downloadSomethingPure(url, fileName);
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			retry++;
			if (retry <= 2) {
				System.out.println("��������" + url + "��" + retry + "��");
				downloadSomethingPure(url, fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retry++;
			if (retry <= 2) {
				System.out.println("��������" + url + "��" + retry + "��");
				downloadSomethingPure(url, fileName);
			}
		} finally {
			getimg.abort();
		}
	}
}
