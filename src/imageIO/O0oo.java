package imageIO;

import java.io.File;

public class O0oo {

	public static void main(String[] args) {
		K k = new K();
		FileAccept fa = new FileAccept();
		fa.setName("png"); // �趨��Ҫ�����ͼƬ��ʽ���˴�Ϊpng
		// String tmpDirString = "D:\\workspace\\FetchMzw\\com.bulkypix.gus";
		File dir = new File(args[0]);// �趨��Ҫ�����Ŀ¼��Ϊ�ռ�Ϊ��ǰĿ¼
		// File dir = new File(tmpDirString);
		// System.out.println(dir.getAbsolutePath());
		File srcImage[] = dir.listFiles(fa);// �õ���ǰĿ¼��������ָ����չ����β���ļ�����
		// k.imageConverter(srcImage);
		for (int i = 0; i < srcImage.length; i++) {
			String sr = srcImage[i].getAbsolutePath();
			k.imageConverter(srcImage[i], sr.substring(0, sr.length() - 3));
			;// ����Щ����ת�����˷���ʵ����K��java
		}
		// TODO �Զ����ɵķ������
		for (int i = 0; i < srcImage.length; i++) {
			File file = srcImage[i];
			if (!file.getName().equals("icon.png")) {
				file.delete();
			}
		}

	}

}
