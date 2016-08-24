package imageIO;

import java.io.File;
import java.io.FilenameFilter;

public class FileAccept implements FilenameFilter {
	// 实现文件名接口以设定扩展名
	// @Override
	String extendsName;

	public void setName(String s) {
		extendsName = "." + s;
	}

	public boolean accept(File dir, String name) {
		// TODO 自动生成的方法存根
		return name.endsWith(extendsName);
	}

}
