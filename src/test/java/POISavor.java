import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minidev.json.JSONArray;

import com.jayway.jsonpath.JsonPath;

public class POISavor {
	String stringUrlPrefix = "https://api2.pickat.com/v2/main/places?sort=popular&has_coupon=0&has_parking=1&aoi_id=";
	String stringUrlPostfix = "&limit=20";

	public static void main(String args[]) {
		POISavor sv = new POISavor();

		String[] aois = { "117987", "117961", "117994", "117936", "118087", "118012", "118028", "117996", "117965", "118108", "118036",
				"117982", "118058", "118054", "117922", "117978", "117918", "118006", "117910", "117950", "118111", "118073", "117929",
				"118019", "117933", "118093", "118017", "118118", "118101", "117976", "117958", "117937", "117901", "117927", "118113",
				"118205", "118158", "118211", "118176", "118147", "118208", "118168", "118180", "118214", "118148", "118226", "118149",
				"118128", "118187", "118189", "118173", "118216", "118164", "118224", "118206", "118156", "118207", "118153", "118169" };
		String filename = "/Users/horanghi/pois/poi_sample";
		String next = null;
		int idx = 0;
		boolean inprogress = true;
		for (String ao : aois) {
			try {
				do {
					try {

						// request over http
						String jsonStr = null;// = sv.httpRequest(ao, next);

						// get next key
						next = sv.getNextKey(jsonStr);

						// write & save contents
						sv.writeFile(filename + idx + ".json", jsonStr);

						idx++;

						if (next == null) {
							break;
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						inprogress = false;
					}

					Thread.sleep(500);

				} while (inprogress);

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

	}

	private String getNextKey(String jsonStr) {
		try {
			return JsonPath.read(jsonStr, "$.paging.next");
		} catch (Exception ex) {
		}

		return null;
	}

	public String httpRequest(String aoiCode, String nextCursor) throws IOException {
		StringBuffer sb = new StringBuffer(stringUrlPrefix);
		if (aoiCode != null && !"".equals(aoiCode)) {
			sb.append(aoiCode);
		}
		sb.append("&cursor=");
		if (nextCursor != null && !"".equals(nextCursor)) {
			sb.append(nextCursor);
		}
		sb.append(stringUrlPostfix);
		System.out.println(sb.toString());
		URL url = new URL(sb.toString());
		URLConnection uc = url.openConnection();

		uc.setRequestProperty("User-Agent", "Natemobile/2000");
		uc.setRequestProperty(
				"Authorization",
				"Bearer eyJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCIsICJraWQiOiAiMSJ9.eyJpc3MiOiAiMSIsICJpYXQiOiAxMzk4ODQ3MzUxLCAianRpIjogInhvTzJjRy1ZSmdTLWlBNWVfOUlOM3c9PSIsICJzdWIiOiAiMTAwMDA5IiwgImF1ZCI6ICJ1cm46cGlja2F0OmNsYWltczpjb25zdW1lcl9rZXkifQ.--4sdrJ_ooZAIdZhshGxAQ6Mw5svQ87326nfH2sTuSs");
		InputStreamReader inputStreamReader = null;
		BufferedReader in = null;
		try {
			inputStreamReader = new InputStreamReader(uc.getInputStream(), "UTF8");
			in = new BufferedReader(inputStreamReader);
			StringBuilder builder = new StringBuilder();
			int ch;
			while ((ch = in.read()) != -1) {
				builder.append((char) ch);
			}
			return builder.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null)
				in.close();
			if (inputStreamReader != null)
				inputStreamReader.close();
		}

		return null;

	}

	public void writeFile(String filename, String contents) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filename));
			out.write(contents);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
}