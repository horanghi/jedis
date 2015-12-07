import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.spatial.model.Point;

public class PlaceDBSavor {

	public static void main(String args[]) {
		PlaceDBSavor sv = new PlaceDBSavor();
		JedisPool pPool = new JedisPool(new GenericObjectPoolConfig(), "172.19.114.202", 19004, 30000);
		//
		Jedis geodis = pPool.getResource();

		// 1. select 1000 건 단위로 poi 가져오기
		// 2. write file
		String key = "sap1";
		long totalCount = geodis.gpcard(key);

		String filename = "/Users/horanghi/placeDB/placeDB";
		long incre = 100000;
		
		int fileidx = 0;
		for (int idx = 0; idx < totalCount;) {
			StringBuffer sb = new StringBuffer();
			List<Point<String>> result;
			if ((idx + incre) > totalCount) {
				result = geodis.gprange(key, idx, totalCount);
			} else {
				result = geodis.gprange(key, idx, (idx + incre));
				System.out.println(".");
			}
			idx += incre;

			for (Point<String> p : result) {
				if(p.getScore() != null){
					sb.append("{ \"value\":"+ p.getValue() +", \"score\":"+ p.getScore() + "}\n");
				}else{
					sb.append("{ \"value\":"+ p.getValue() +", \"score\": -1 }\n");
				}
				
			}
			try {
				sv.writeFile(filename + (fileidx++) + ".json", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		pPool.returnResource(geodis);

		//

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