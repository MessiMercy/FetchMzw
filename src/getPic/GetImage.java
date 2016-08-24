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
 * @说明 从网络获取图片到本地
 * @author 崔素强
 * @version 1.0
 * @since
 */
public class GetImage {
	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(int j, String imageUrl, String filePath) {
		String url = imageUrl;
		byte[] btImg = getImageFromNetByUrl(url);
		if (null != btImg && btImg.length > 0) {
			System.out.println("读取到：" + btImg.length + " 字节");
			Random random = new Random();
			int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
			String fileName;
			if (j == 0) {
				fileName = "icon.png";
			} else {
				fileName = rannum + ".jpg";
			}
			System.out.println("开始下载" + rannum);
			File fffFile = new File(filePath);
			if (!fffFile.exists()) {
				fffFile.mkdir();
			}
			writeImageToDisk(btImg, fileName, filePath);
			System.out.println(rannum + "下载完成");
		} else {
			System.out.println("没有从该连接获得内容");
		}
	}

	/**
	 * 将图片写入到磁盘
	 * 
	 * @param img
	 *            图片数据流
	 * @param fileName
	 *            文件保存时的名称
	 */
	public static void writeImageToDisk(byte[] img, String fileName,
			String filePath) {
		try {
			File file = new File(filePath + "\\" + fileName);
			System.out.println("开始写" + fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			System.out.println(fileName + "写完了");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl
	 *            网络连接地址
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return outStream.toByteArray();
	}
}