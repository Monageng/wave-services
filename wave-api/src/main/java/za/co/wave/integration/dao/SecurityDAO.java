package za.co.wave.integration.dao;

import java.net.UnknownHostException;
import java.util.Iterator;

import org.bson.Document;
import org.codehaus.jettison.json.JSONObject;

import za.co.wave.integration.constants.Constants;
import za.co.wave.integration.convertor.UserProfileConvertor;
import za.co.wave.integration.vo.UserProfile;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SecurityDAO {

	private MongoClient getMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(Constants.MONGO_DB_HOSTNAME,
				Constants.MONGO_DB_PORT);
		return mongoClient;
	}

	public UserProfile fundByUserName(String username) {
		UserProfile userProfile = null;
		try {
			MongoDatabase db = getMongoClient().getDatabase(
					Constants.MONGO_DB_WAVE);
			MongoCollection<Document> col = db
					.getCollection(Constants.MONGO_DB_TABLE_PROFILE);

			// DBObject dbObject = new BasicDBObject("_id", username);
			// Class query;
			// DBObject resultObject = col.find(query);
			// String result = resultObject.get(YOUR_COLOUM_NAME);

			System.out.println("USername : " + username);
			Document document = new Document("userCredential.username",
					username);
			FindIterable<Document> cursor = col.find(document);
			System.out.println("Cursor >>> " + cursor);
			//
			if (cursor != null && cursor.iterator() != null) {
				Iterator<Document> iter = cursor.iterator();
				System.out.println("Ite +++++ " + iter.hasNext());
				while (iter.hasNext()) {
					Document d = iter.next();
					System.out.println("D : " + d.toJson());
					JSONObject jsonObject = new JSONObject(d.toJson());
					System.out.println("jsonObject : " + jsonObject.toString());
					userProfile = UserProfileConvertor
							.buildUserProfile(jsonObject);
					// list.add(UserProfileConvertor.buildUserProfile(jsonObject));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userProfile;
	}

	public boolean authenticate(String username, String password)
			throws Exception {
		boolean authenticated = false;
		MongoDatabase db = getMongoClient()
				.getDatabase(Constants.MONGO_DB_WAVE);
		MongoCollection<Document> col = db
				.getCollection(Constants.MONGO_DB_TABLE_PROFILE);

		Document document = new Document();
		document.put("userCredential.username", username);
		document.put("userCredential.password", password);
		FindIterable<Document> cursor = col.find(document);

		if (cursor != null && cursor.iterator() != null) {
			Iterator<Document> iter = cursor.iterator();
			System.out.println("Ite +++++ " + iter.hasNext());
			while (iter.hasNext()) {
				Document d = iter.next();
				System.out.println("D : " + d.toJson());
				JSONObject jsonObject = new JSONObject(d.toJson());
				System.out.println("jsonObject : " + jsonObject.toString());
				authenticated = true;
			}

		}
		return authenticated;
	}

}
