package androeed;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.http.client.HttpClient;

import com.Apkpure;
import com.MultiDownloading;

import getPic.MultiDownload;

public class FetchAndroeed {

	static HttpClient client = CrawlerLib.getInstanceClient(true);
	public final static String GAMELISTURL = "http://www.androeed.ru/files/novie_igry_dlya_android.html";
	private static Stack<String> gameListTemp = FetchTargetText.func(client, GAMELISTURL, "div[onclick]", null,
			"onclick", "windows-1251");
	private static Set<String> games = new HashSet<>();

	public static void main(String[] args) {
		func();
		// try {
		// Document doc =
		// Jsoup.connect("http://www.androeed.ru/files/novie_igry_dlya_android.html")
		// .userAgent(
		// "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Chrome/51.0.2704.103 Safari/537.36")
		// .get();
		// System.out.println(doc.title());
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static void func() {
		HashMap<String, String> map = new HashMap<>();
		map.put("Referer", "http://www.androeed.ru/files/novie_igry_dlya_android.html");
		games.addAll(gameListTemp);
		// map.put("Cookie", "lang=english");
		// map.put("User-Agent",
		// "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Chrome/51.0.2704.103 Safari/537.36");
		while (true) {
			// Stack<String> gameListBefore = getGameListTemp();
			try {
				System.out.println("休眠5分钟");
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Stack<String> gameListAfter = FetchTargetText.func(client, GAMELISTURL, "div[onclick]", map, "onclick",
					"windows-1251");
			// Stack<String> gamelistbeforeClone = new Stack<>();
			// gamelistbeforeClone.addAll(gameListBefore);
			// Stack<String> gamelistafterClone = new Stack<>();
			// gamelistafterClone.addAll(gameListBefore);
			// while (!gamelistafterClone.isEmpty()) {
			// CrawlerLib.printResult("gamelistafter: " +
			// gamelistafterClone.pop(), true);
			// }
			// while (!gamelistbeforeClone.isEmpty()) {
			// CrawlerLib.printResult("gamelistbefore: " +
			// gamelistbeforeClone.pop(), true);
			// }
			Stack<String> gameList = new Stack<>();
			while (!gameListAfter.isEmpty()) {
				String temp = gameListAfter.pop();
				if (games.add(temp)) {
					gameList.add(temp);
				}
			}
			// setGameListTemp(gameListAfter);
			System.out.println(gameList.size());
			System.out.println("现在时间： " + new Date());
			if (gameList.isEmpty()) {
				System.out.println("这一时间段并没有更新");
			} else {
				System.out.println("发现" + gameList.size() + "个更新了！");
			}
			// if (gameList.size() > 10) {
			// while (!gameList.isEmpty()) {
			// CrawlerLib.printResult("gamelist: " + gameList.pop(), true);
			// }
			// }
			assert (gameList.size() < 10);
			while (!gameList.isEmpty()) {
				Stack<String> gpUrlStack = FetchTargetText.func(client, controlString(gameList.pop()),
						"a.attach_link_google_play", map, "href", "windows-1251");
				String gpUrl = null;
				if (gpUrlStack.isEmpty()) {
					continue;
				} else {
					gpUrl = gpUrlStack.pop();
				}
				Stack<String> googlePlayStack = FetchTargetText.func(client, gpUrl, "a.get_attach", map, "href",
						"windows-1251");
				String googlePlay = null;
				if (!googlePlayStack.isEmpty()) {
					googlePlay = googlePlayStack.pop();
				} else {
					continue;
				}
				String packageName = googlePlay.substring(googlePlay.indexOf("=") + 1, googlePlay.length());
				// System.out.println(packageName);
				Apkpure pure = new Apkpure(packageName);
				String downloadLink = null;
				try {
					downloadLink = pure.GetDownloadlink();
				} catch (Exception e) {
					System.out.println(packageName + "为付费游戏！");
				}
				List<String> screenShots = pure.GetScreenShotLinks();
				String iconLink = pure.GetIconLink();
				if (downloadLink != null) {
					new Thread(new MultiDownloading(downloadLink, packageName + pure.getSuffix())).start();
				}
				new Thread(new MultiDownload(0, iconLink, packageName, pure)).start();
				// for (String string : screenShots) {
				// new Thread(new MultiDownload(1, string, packageName,
				// pure)).start();
				// }
				for (int i = 0; i < screenShots.size() && i < 5; i++) {
					new Thread(new MultiDownload(1, screenShots.get(i), packageName, pure)).start();

				}
			}
		}
	}

	public static String controlString(String str) {
		int left = str.indexOf("'") + 1;
		int right = str.lastIndexOf("'");
		str = "http://www.androeed.ru" + str.substring(left, right);
		return str;
	}

	public static Stack<String> getGameListTemp() {
		return gameListTemp;
	}

	public static void setGameListTemp(Stack<String> gameListTemp) {
		FetchAndroeed.gameListTemp = gameListTemp;
	}

	public static Set<String> getGames() {
		return games;
	}

	public static void setGames(Set<String> games) {
		FetchAndroeed.games = games;
	}
}
