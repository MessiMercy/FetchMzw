package imageIO;

import java.io.File;
import java.io.FilenameFilter;

public class FileAccept implements FilenameFilter {
	// ʵ���ļ����ӿ����趨��չ��
	// @Override
	String extendsName;

	public void setName(String s) {
		extendsName = "." + s;
	}

	public boolean accept(File dir, String name) {
		// TODO �Զ����ɵķ������
		return name.endsWith(extendsName);
	}

}
