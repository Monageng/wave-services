package za.co.wave.integration.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import za.co.wave.integration.dao.SecurityDAO;
import za.co.wave.integration.vo.UserCredential;

@Path("/SecurityService")
public class SecurityService {
	
	SecurityDAO dao = new SecurityDAO();
	
	@Path("/authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(UserCredential userCredential) {
		try {
			boolean authenticated = dao.authenticate(userCredential.getUsername(), userCredential.getPassword());
			if (authenticated) {
				return Response.ok("User authenticated").build();
			} else {
				return Response.status(Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
			return Response.status(500)
					.entity(e.getMessage()).build();
		}
	}

}
