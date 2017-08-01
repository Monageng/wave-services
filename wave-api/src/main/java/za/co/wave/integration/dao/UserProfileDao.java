package za.co.wave.integration.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

import za.co.wave.integration.constants.Constants;
import za.co.wave.integration.convertor.UserProfileConvertor;
import za.co.wave.integration.vo.UserProfile;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class UserProfileDao {
	
	private MongoClient getMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(Constants.MONGO_DB_HOSTNAME, Constants.MONGO_DB_PORT);
		return mongoClient;
	}

	public void insertUserProfile(UserProfile userProfile) throws Exception {
		
			MongoDatabase db = getMongoClient().getDatabase(Constants.MONGO_DB_WAVE);
			MongoCollection<Document> col =  db.getCollection(Constants.MONGO_DB_TABLE_PROFILE);
            
			UserProfile dbUserProfile = fundByUserName(userProfile.getUserCredential().getUsername());
			if (dbUserProfile!= null) {
				throw new Exception("Username [" + userProfile.getUserCredential().getUsername() + "] has already exists, please select different username" );
			}
			System.out.println("user " + userProfile.getDateOfBirth());
			ObjectMapper mapper = new ObjectMapper();
			userProfile.set_id(UUID.randomUUID().toString());
			
			String jsonString = mapper.writeValueAsString(userProfile);
			System.out.println("JSON for Create : " + jsonString);
			col.insertOne(Document.parse(jsonString));
		
		
	}

	public List<UserProfile> getAllUserProfiles() {
		List<UserProfile> list = new ArrayList<UserProfile>();
		try {
			MongoDatabase db = getMongoClient().getDatabase(Constants.MONGO_DB_WAVE);
			MongoCollection<Document> col =  db.getCollection(Constants.MONGO_DB_TABLE_PROFILE);
			
			 FindIterable<Document> cursor = col.find();

			 if (cursor != null && cursor.iterator() != null) {
				 Iterator<Document> iter = cursor.iterator();
				 
				 while (iter.hasNext()) {
					 Document d = iter.next(); 
					 System.out.println("D : " + d.toJson());
					 JSONObject jsonObject = new JSONObject(d.toJson());
					 System.out.println("jsonObject : " + jsonObject.toString());
					 list.add(UserProfileConvertor.buildUserProfile(jsonObject));
				 }
				 	 
			 }
			 
//			while (cursor.iterator().hasNext()) {
//				Document d = cursor.iterator().next();
//				System.out.println("D " + d);
//				String json = d.toJson();
//				System.out.println("JSON STring : " + json);
//				ObjectMapper mapper = new ObjectMapper();
//				UserProfile userProfile = new UserProfile();
//				
////				mapper.readValue(json, UserProfile.class);
//				list.add(userProfile);
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public UserProfile fundByUserName(String username) {
		UserProfile userProfile = null;
		try {
			MongoDatabase db = getMongoClient().getDatabase(Constants.MONGO_DB_WAVE);
			MongoCollection<Document> col =  db.getCollection(Constants.MONGO_DB_TABLE_PROFILE);
			
//			 DBObject dbObject  = new BasicDBObject("_id", username);
//			 Class query;
//			DBObject resultObject   = col.find(query);
//			    String result =  resultObject.get(YOUR_COLOUM_NAME);
			 
			System.out.println("USername : " + username);
			Document document = new Document("userCredential.username", username);
			 FindIterable<Document> cursor = col.find(document);
			 System.out.println("Cursor >>> " + cursor);
//
			 if (cursor != null && cursor.iterator() != null) {
				 Iterator<Document> iter = cursor.iterator();
				 System.out.println("Ite +++++ " + iter.hasNext()   );
				 while (iter.hasNext()) {
					 Document d = iter.next(); 
					 System.out.println("D : " + d.toJson());
					 JSONObject jsonObject = new JSONObject(d.toJson());
					 System.out.println("jsonObject : " + jsonObject.toString());
					 userProfile = UserProfileConvertor.buildUserProfile(jsonObject);
//					 list.add(UserProfileConvertor.buildUserProfile(jsonObject));
				 }
				 	 
			 }
			 
//			while (cursor.iterator().hasNext()) {
//				Document d = cursor.iterator().next();
//				System.out.println("D " + d);
//				String json = d.toJson();
//				System.out.println("JSON STring : " + json);
//				ObjectMapper mapper = new ObjectMapper();
//				UserProfile userProfile = new UserProfile();
//				
////				mapper.readValue(json, UserProfile.class);
//				list.add(userProfile);
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userProfile;
	}

	public void deleteAllProfiles() {
		// TODO Auto-generated method stub
		try {
			MongoDatabase  db = getMongoClient().getDatabase(Constants.MONGO_DB_WAVE);
			MongoCollection<Document> col =  db.getCollection(Constants.MONGO_DB_TABLE_PROFILE);
			col.drop();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
