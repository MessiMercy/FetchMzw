package getPic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * @˵�� �������ȡͼƬ������
 * @author ����ǿ
 * @version 1.0
 * @since
 */
public class GetImage {
	/**
	 * ����
	 * 
	 * @param args
	 */
	public static void main(int j, String imageUrl, String filePath) {
		String url = imageUrl;
		byte[] btImg = getImageFromNetByUrl(url);
		if (null != btImg && btImg.length > 0) {
			System.out.println("��ȡ����" + btImg.length + " �ֽ�");
			Random random = new Random();
			int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// ��ȡ5λ�����
			String fileName;
			if (j == 0) {
				fileName = "icon.png";
			} else {
				fileName = rannum + ".jpg";
			}
			System.out.println("��ʼ����" + rannum);
			File fffFile = new File(filePath);
			if (!fffFile.exists()) {
				fffFile.mkdir();
			}
			writeImageToDisk(btImg, fileName, filePath);
			System.out.println(rannum + "�������");
		} else {
			System.out.println("û�дӸ����ӻ������");
		}
	}

	/**
	 * ��ͼƬд�뵽����
	 * 
	 * @param img
	 *            ͼƬ������
	 * @param fileName
	 *            �ļ�����ʱ������
	 */
	public static void writeImageToDisk(byte[] img, String fileName,
			String filePath) {
		try {
			File file = new File(filePath + "\\" + fileName);
			System.out.println("��ʼд" + fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			System.out.println(fileName + "д����");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ݵ�ַ������ݵ��ֽ���
	 * 
	 * @param strUrl
	 *            �������ӵ�ַ
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// ͨ����������ȡͼƬ����
			byte[] btImg = readInputStream(inStream);// �õ�ͼƬ�Ķ���������
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������л�ȡ����
	 * 
	 * @param inStream
	 *            ������
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			inStream.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return outStream.toByteArray();
	}
}