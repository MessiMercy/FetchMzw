package getPic;

import com.Apkpure;

public class MultiDownload implements Runnable {
	int j = 0;
	String imageUrl;
	String filePath;
	public static int workingThread = 0;
	Apkpure pure;

	public MultiDownload(int j, String imageUrl, String filePath, Apkpure pure) {
		this.j = j;
		this.imageUrl = imageUrl;
		this.filePath = filePath;
		this.pure = pure;
	}

	@Override
	public void run() {
		workingThread++;
		if (pure != null && j == 1) {
			pure.DownloadVerifyCode(filePath, false, imageUrl);
		} else {
			pure.DownloadVerifyCode(filePath, true, null);
		}
		workingThread--;
	}
}
