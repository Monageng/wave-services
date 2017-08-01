package za.co.wave.integration.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import za.co.wave.integration.dao.UserProfileDao;
import za.co.wave.integration.vo.UserProfile;

@Path("/UserProfileService")
public class UserProfileService {

	UserProfileDao dao = new UserProfileDao();

	@Path("/getAllProfiles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		List<UserProfile> list = dao.getAllUserProfiles();
		return Response.ok(list).build();
	}

	@Path("/createUserProfile")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUserProfile(UserProfile userProfile) {
		try {
			dao.insertUserProfile(userProfile);
			return Response.ok("User Profile created successfully").build();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("E " + e.getMessage());
			return Response.status(500)
					.entity(e.getMessage()).build();
		}
	}

	@Path("/deleteAllProfiles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAllProfiles() {
		
		try {
			dao.deleteAllProfiles();
			return Response.ok("All profiles deleted successfully ").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity(e.getMessage()).build();
		}
		
	}

	@Path("fundByUserName")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fundByUserName(@QueryParam("username") String username) {
		try {
			UserProfile profile = dao.fundByUserName(username);
			return Response.ok(profile).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity(e.getMessage()).build();
		}
	}
}
