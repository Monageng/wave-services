package za.co.wave.integration.convertor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import za.co.wave.integration.vo.UserCredential;
import za.co.wave.integration.vo.UserProfile;

public class UserProfileConvertor {

	
	public static UserProfile buildUserProfile(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		UserProfile profile = new UserProfile();
		profile.setAge(jsonObject.getInt("age"));
		profile.setCellNumber(jsonObject.getString("cellNumber"));
		if (!jsonObject.isNull("dateOfBirth") ) {
			profile.setDateOfBirth(jsonObject.getString("dateOfBirth"));
		}
		if (!jsonObject.isNull("_id"))  {
			String id = (String) jsonObject.get("_id");
			profile.set_id(id);
		}
		
		profile.setEmailAddress(jsonObject.getString("emailAddress"));
		profile.setName(jsonObject.getString("name"));
		profile.setSurname(jsonObject.getString("surname"));
		
		if (!jsonObject.isNull("userCredential")) {
			profile.setUserCredential(buildUserCredential(jsonObject.getJSONObject("userCredential")));
		}
		
		
		return profile;
	}

	private static UserCredential buildUserCredential(JSONObject jsonObject) throws JSONException {
		UserCredential credential = new UserCredential();
		credential.setUsername(jsonObject.getString("username"));
		credential.setPassword(jsonObject.getString("password"));
		
		return credential;
	}

}
