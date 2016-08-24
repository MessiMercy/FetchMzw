package imageIO;

import java.io.File;

public class O0oo {

	public static void main(String[] args) {
		K k = new K();
		FileAccept fa = new FileAccept();
		fa.setName("png"); // 设定需要处理的图片格式，此处为png
		// String tmpDirString = "D:\\workspace\\FetchMzw\\com.bulkypix.gus";
		File dir = new File(args[0]);// 设定需要处理的目录，为空即为当前目录
		// File dir = new File(tmpDirString);
		// System.out.println(dir.getAbsolutePath());
		File srcImage[] = dir.listFiles(fa);// 得到当前目录下所有以指定扩展名结尾的文件对象
		// k.imageConverter(srcImage);
		for (int i = 0; i < srcImage.length; i++) {
			String sr = srcImage[i].getAbsolutePath();
			k.imageConverter(srcImage[i], sr.substring(0, sr.length() - 3));
			;// 将这些对象转换，此方法实现在K。java
		}
		// TODO 自动生成的方法存根
		for (int i = 0; i < srcImage.length; i++) {
			File file = srcImage[i];
			if (!file.getName().equals("icon.png")) {
				file.delete();
			}
		}

	}

}
