package in.er.annamalai.userservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.er.annamalai.userservice.entiry.rest.Application;
import in.er.annamalai.userservice.entiry.rest.Client;
import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;
import in.er.annamalai.userservice.util.PasswordHashUtil;
import in.er.annamalai.userservice.web.ApplicationController;
import in.er.annamalai.userservice.web.ClientController;
import in.er.annamalai.userservice.web.UserController;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@Slf4j
public class UserServiceApplicationTests {

	@Autowired
	private ApplicationController applicationController;

	@Autowired
	private UserController userController;

	@Autowired
	private ClientController clientController;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private MockMvc mockMvc;

	@Test
	public void contextLoads() {
		Assertions.assertThat(mockMvc).isNotNull();
		Assertions.assertThat(applicationController).isNotNull();
		Assertions.assertThat(userController).isNotNull();
		Assertions.assertThat(clientController).isNotNull();
	}

	@Test
	public void testCRUDOnApplication() throws Exception {
		String name = "Authorized Application";
		String description = "Authourized Application";
		Long divisionId = 1L;
		Long clientId = null;
		boolean authorised = true;
		Application application = createApplication(name, description, divisionId, clientId, authorised);
		Assertions.assertThat(application).isNotNull();
		Long applicationId = application.getId();
		Assertions.assertThat(applicationId).isNotNull().isGreaterThan(0);
		Assertions.assertThat(application.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(application.getDescription()).isNotNull().isEqualTo(description);
		Assertions.assertThat(application.getDivisionId()).isNotNull().isEqualTo(divisionId);
		Assertions.assertThat(application.getClientId()).isEqualTo(clientId);
		Assertions.assertThat(application.isAuthorised()).isEqualTo(authorised);

		application = getApplication(applicationId, divisionId, true);
		Assertions.assertThat(application).isNotNull();
		Assertions.assertThat(application.getId()).isNotNull().isGreaterThan(0).isEqualTo(applicationId);
		Assertions.assertThat(application.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(application.getDescription()).isNotNull().isEqualTo(description);
		Assertions.assertThat(application.getDivisionId()).isNotNull().isEqualTo(divisionId);
		Assertions.assertThat(application.getClientId()).isEqualTo(clientId);
		Assertions.assertThat(application.isAuthorised()).isEqualTo(authorised);

		name = "Authorized Application Updated";
		description = "Authourized Application Updated";
		clientId = null;
		authorised = false;
		application = updateApplication(name, description, divisionId, clientId, authorised, applicationId);
		Assertions.assertThat(application).isNotNull();
		Assertions.assertThat(application.getId()).isNotNull().isGreaterThan(0).isEqualTo(applicationId);
		Assertions.assertThat(application.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(application.getDescription()).isNotNull().isEqualTo(description);
		Assertions.assertThat(application.getDivisionId()).isNotNull().isEqualTo(divisionId);
		Assertions.assertThat(application.getClientId()).isEqualTo(clientId);
		Assertions.assertThat(application.isAuthorised()).isEqualTo(authorised);

		String clientName = "Client Name";
		String clientDescription = "Client Description";
		Client client = createClient(clientName, clientDescription);

		name = "Authorized Application Updated Again";
		description = "Authourized Application Updated Again";
		clientId = client.getId();
		authorised = true;
		application = updateApplication(name, description, divisionId, clientId, authorised, applicationId);
		Assertions.assertThat(application).isNotNull();
		Assertions.assertThat(application.getId()).isNotNull().isGreaterThan(0).isEqualTo(applicationId);
		Assertions.assertThat(application.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(application.getDescription()).isNotNull().isEqualTo(description);
		Assertions.assertThat(application.getDivisionId()).isNotNull().isEqualTo(divisionId);
		Assertions.assertThat(application.getClientId()).isEqualTo(clientId);
		Assertions.assertThat(application.isAuthorised()).isEqualTo(authorised);

		application = getApplication(applicationId, divisionId, true);
		Assertions.assertThat(application).isNotNull();
		Assertions.assertThat(application.getId()).isNotNull().isGreaterThan(0).isEqualTo(applicationId);
		Assertions.assertThat(application.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(application.getDescription()).isNotNull().isEqualTo(description);
		Assertions.assertThat(application.getDivisionId()).isNotNull().isEqualTo(divisionId);
		Assertions.assertThat(application.getClientId()).isEqualTo(clientId);
		Assertions.assertThat(application.isAuthorised()).isEqualTo(authorised);
		
		deleteApplication(applicationId, divisionId);
		application = getApplication(applicationId, divisionId, false);
		Assertions.assertThat(application).isNull();
	}

	@Test
	public void testCRUDOnClient() throws Exception {
		String name = "Client Name";
		String description = "Client Description";
		Client client = createClient(name, description);
		Assertions.assertThat(client).isNotNull();
		Long clientId = client.getId();
		Assertions.assertThat(clientId).isNotNull().isGreaterThan(0);
		Assertions.assertThat(client.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(client.getDescription()).isNotNull().isEqualTo(description);

		client = getClient(clientId, true);
		Assertions.assertThat(client).isNotNull();
		Assertions.assertThat(client.getId()).isNotNull().isGreaterThan(0).isEqualTo(clientId);
		Assertions.assertThat(client.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(client.getDescription()).isNotNull().isEqualTo(description);

		name = "Client Name Updated";
		description = "Client Name Updated";
		client = updateClient(name, description, clientId);
		Assertions.assertThat(client).isNotNull();
		Assertions.assertThat(client.getId()).isNotNull().isGreaterThan(0).isEqualTo(clientId);
		Assertions.assertThat(client.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(client.getDescription()).isNotNull().isEqualTo(description);

		name = "Authorized Application Updated Again";
		description = "Authourized Application Updated Again";
		client = updateClient(name, description, clientId);
		Assertions.assertThat(client).isNotNull();
		Assertions.assertThat(client.getId()).isNotNull().isGreaterThan(0).isEqualTo(clientId);
		Assertions.assertThat(client.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(client.getDescription()).isNotNull().isEqualTo(description);

		client = getClient(clientId, true);
		Assertions.assertThat(client).isNotNull();
		Assertions.assertThat(client.getId()).isNotNull().isGreaterThan(0).isEqualTo(clientId);
		Assertions.assertThat(client.getName()).isNotNull().isEqualTo(name);
		Assertions.assertThat(client.getDescription()).isNotNull().isEqualTo(description);
		
		deleteClient(clientId);
		client = getClient(clientId, false);
		Assertions.assertThat(client).isNull();
	}

	@Test
	public void testCRUDOnUser() throws Exception {
		Application application = createApplication("Authorized Application", "Authorized Application", 1L, null, true);
		String email = "test@annamalai.er.in";
		String password = "Test@123";
		String userName = "testUser";
		String name = "Test User";
		User user = createUser(email, password, userName, name, application.getId());
		Assertions.assertThat(user).isNotNull();
		Long userId = user.getId();
		Assertions.assertThat(userId).isNotNull().isGreaterThan(0);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(password, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);

		user = getUser(userId, application.getId(), true);
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isNotNull().isGreaterThan(0).isEqualTo(userId);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(password, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);

		email = "newtest@annamalai.er.in";
		userName = "newTestUser";
		name = "New Test User";
		user = updateUser(email, null, userName, name, userId, null, application.getId());
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isNotNull().isGreaterThan(0).isEqualTo(userId);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(password, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);

		email = "newtest1@annamalai.er.in";
		String newPassword = "NewTest@123";
		userName = "newTestUser1";
		name = "New Test User1";
		user = updateUser(email, newPassword, userName, name, userId, password, application.getId());
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isNotNull().isGreaterThan(0).isEqualTo(userId);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(newPassword, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);

		user = getUser(userId, application.getId(), true);
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isNotNull().isGreaterThan(0).isEqualTo(userId);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(newPassword, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);
		
		deleteUser(userId, application.getId());
		user = getUser(userId, application.getId(), false);
		Assertions.assertThat(user).isNull();
	}

	private Application createApplication(String name, String description, Long divisionId, Long clientId, boolean authorised) {
		try {
			String requestBody = objectMapper.writeValueAsString(new Application(name, description, null, clientId, authorised));
			if(requestBody != null) {
				MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/application").contentType(MediaType.APPLICATION_JSON_VALUE).header("x-division-id", divisionId).content(requestBody);
				MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				String responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
				return objectMapper.readValue(responseBody, Application.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating Application", exception);
		}
		return null;
	}

	private Application getApplication(long id, Long divisionId, boolean assertStatus) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}", id).header("x-division-id", divisionId)).andReturn().getResponse();
			String responseBody = null;
			if(assertStatus) {
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
			} else {
				if(response.getStatus() >= 200 && response.getStatus() <= 299) {
					responseBody = response.getContentAsString();
				} else {
					log.warn("Unexpected Response while Fetching Application");
				}
			}
			if(responseBody != null) {
				return objectMapper.readValue(responseBody, Application.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Fetching Application", exception);
		}
		return null;
	}

	private Application updateApplication(String name, String description, Long divisionId, Long clientId, boolean authorised, Long id) {
		try {
			String requestBody = objectMapper.writeValueAsString(new Application(name, description, null, clientId, authorised));
			if(requestBody != null) {
				MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.put("/application/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE).header("x-division-id", divisionId).content(requestBody)).andReturn().getResponse();
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				String responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
				return objectMapper.readValue(responseBody, Application.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Updating Application", exception);
		}
		return null;
	}

	private void deleteApplication(Long id, Long divisionId) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/application/{id}", id).header("x-division-id", divisionId)).andReturn().getResponse();
			Assertions.assertThat(response.getStatus()).isBetween(200, 299);
		} catch (Exception exception) {
			log.error("Exception Occured while Deleting Application", exception);
		}
	}

	private Client createClient(String name, String description) {
		try {
			String requestBody = objectMapper.writeValueAsString(new Client(name, description));
			if(requestBody != null) {
				MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/client").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody);
				MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				String responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
				return objectMapper.readValue(responseBody, Client.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating Client", exception);
		}
		return null;
	}

	private Client getClient(long id, boolean assertStatus) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/{id}", id)).andReturn().getResponse();
			String responseBody = null;
			if(assertStatus) {
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
			} else {
				if(response.getStatus() >= 200 && response.getStatus() <= 299) {
					responseBody = response.getContentAsString();
				} else {
					log.warn("Unexpected Response while Fetching Client");
				}
			}
			if(responseBody != null) {
				return objectMapper.readValue(responseBody, Client.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Fetching Application", exception);
		}
		return null;
	}

	private Client updateClient(String name, String description, Long id) {
		try {
			String requestBody = objectMapper.writeValueAsString(new Client(name, description));
			if(requestBody != null) {
				MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.put("/client/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)).andReturn().getResponse();
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				String responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
				return objectMapper.readValue(responseBody, Client.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Updating Client", exception);
		}
		return null;
	}

	private void deleteClient(Long id) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/client/{id}", id)).andReturn().getResponse();
			Assertions.assertThat(response.getStatus()).isBetween(200, 299);
		} catch (Exception exception) {
			log.error("Exception Occured while Deleting Client", exception);
		}
	}

	private User createUser(String email, String password, String userName, String name, Long applicationId) {
		try {
			String requestBody = objectMapper.writeValueAsString(new User(email, password, userName, name));
			if(requestBody != null) {
				MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).header("x-application-id", applicationId).content(requestBody);
				MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				String responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
				return objectMapper.readValue(responseBody, User.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating User", exception);
		}
		return null;
	}

	private User getUser(long id, Long applicationId, boolean assertStatus) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", id).header("x-application-id", applicationId)).andReturn().getResponse();
			String responseBody = null;
			if(assertStatus) {
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
			} else {
				if(response.getStatus() >= 200 && response.getStatus() <= 299) {
					responseBody = response.getContentAsString();
				} else {
					log.warn("Unexpected Response while Fetching User");
				}
			}
			if(responseBody != null) {
				return objectMapper.readValue(responseBody, User.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Fetching User", exception);
		}
		return null;
	}

	private User updateUser(String email, String password, String userName, String name, Long id, String oldPassword, Long applicationId) {
		try {
			String requestBody = objectMapper.writeValueAsString(new UpdateUserRequest(email, password, userName, name, oldPassword));
			if(requestBody != null) {
				MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE).header("x-application-id", applicationId).content(requestBody)).andReturn().getResponse();
				Assertions.assertThat(response.getStatus()).isBetween(200, 299);
				String responseBody = response.getContentAsString();
				Assertions.assertThat(responseBody).isNotNull().isNotBlank();
				return objectMapper.readValue(responseBody, User.class);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Updating User", exception);
		}
		return null;
	}

	private void deleteUser(Long id, Long applicationId) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", id).header("x-application-id", applicationId)).andReturn().getResponse();
			Assertions.assertThat(response.getStatus()).isBetween(200, 299);
		} catch (Exception exception) {
			log.error("Exception Occured while Deleting User", exception);
		}
	}

}
