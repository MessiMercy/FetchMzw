package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class RunApkpure {

	public static void main(String[] args) {
		Stack<String> packageStack = readFromTxt();
		while (!packageStack.isEmpty()) {
			try {
				Thread.sleep(5 * 1000);
				System.out.println("����5����");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String packageName = packageStack.pop();
			Apkpure ap = new Apkpure(packageName);
			String downloadLink = ap.GetDownloadlink();
			// ap.downloadSomething(downloadLink, packageName + ap.getSuffix());
			new Thread(new MultiDownloading(downloadLink, packageName + ap.getSuffix())).start();
		}

	}

	public static Stack<String> readFromTxt() {
		Stack<String> result = new Stack<>();
		File f = new File("package.txt");
		if (!f.exists()) {
			System.out.println("������package.txt��ȡ,�Ѱ�������package.txt�У�һ������һ��,�����п���");
			try {
				f.createNewFile();
				Thread.sleep(15 * 1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.exit(0);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				result.push(temp);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("��ȡ��" + result.size() + "������");
		return result;
	}
}
