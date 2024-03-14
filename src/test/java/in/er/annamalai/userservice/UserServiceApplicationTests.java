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

import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;
import in.er.annamalai.userservice.util.PasswordHashUtil;
import in.er.annamalai.userservice.web.api.UserController;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
public class UserServiceApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private MockMvc mockMvc;

	@Test
	public void contextLoads() {
		Assertions.assertThat(mockMvc).isNotNull();
		Assertions.assertThat(userController).isNotNull();
	}

	@Test
	public void testCRUDOnUser() throws Exception {
		String email = "test@annamalai.er.in";
		String password = "Test@123";
		String userName = "testUser";
		String name = "Test User";
		User user = createUser(email, password, userName, name);
		Assertions.assertThat(user).isNotNull();
		Long userId = user.getId();
		Assertions.assertThat(userId).isNotNull().isGreaterThan(0);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(password, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);

		user = getUser(userId);
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
		user = updateUser(email, null, userName, name, userId, null);
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
		user = updateUser(email, newPassword, userName, name, userId, password);
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isNotNull().isGreaterThan(0).isEqualTo(userId);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(newPassword, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);

		user = getUser(userId);
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isNotNull().isGreaterThan(0).isEqualTo(userId);
		Assertions.assertThat(user.getEmail()).isNotNull().isEqualTo(email);
		Assertions.assertThat(user.getPassword()).isNotNull();
		Assertions.assertThat(PasswordHashUtil.checkPassword(newPassword, user.getPassword())).isTrue();
		Assertions.assertThat(user.getUserName()).isNotNull().isEqualTo(userName);
		Assertions.assertThat(user.getName()).isNotNull().isEqualTo(name);
		
		deleteUser(userId);
		user = getUser(userId);
		Assertions.assertThat(user).isNull();
	}

	private User createUser(String email, String password, String userName, String name) {
		try {
			String requestBody = objectMapper.writeValueAsString(new User(email, password, userName, name));
			if(requestBody != null) {
				MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody);
				MockHttpServletResponse response = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
				int status = response.getStatus();
				if(response.getStatus() == 200) {
					String responseBody = response.getContentAsString();
					if(responseBody != null && !responseBody.trim().isEmpty()) {
						return objectMapper.readValue(responseBody, User.class);
					}
				} else {
					log.warn("Invalid Response Status - {}", status);
				}
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating User", exception);
		}
		return null;
	}

	private User getUser(long id) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", id)).andReturn().getResponse();
			int status = response.getStatus();
			if(response.getStatus() == 200) {
				String responseBody = response.getContentAsString();
				if(responseBody != null && !responseBody.trim().isEmpty()) {
					return objectMapper.readValue(responseBody, User.class);
				}
			} else {
				log.warn("Invalid Response Status - {}", status);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating User", exception);
		}
		return null;
	}

	private User updateUser(String email, String password, String userName, String name, Long id, String oldPassword) {
		try {
			String requestBody = objectMapper.writeValueAsString(new UpdateUserRequest(email, password, userName, name, oldPassword));
			if(requestBody != null) {
				MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)).andReturn().getResponse();
				int status = response.getStatus();
				if(response.getStatus() == 200) {
					String responseBody = response.getContentAsString();
					if(responseBody != null && !responseBody.trim().isEmpty()) {
						return objectMapper.readValue(responseBody, User.class);
					}
				} else {
					log.warn("Invalid Response Status - {}", status);
				}
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating User", exception);
		}
		return null;
	}

	private void deleteUser(Long id) {
		try {
			MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", id)).andReturn().getResponse();
			int status = response.getStatus();
			if(response.getStatus() != 200) {
				log.warn("Invalid Response Status - {}", status);
			}
		} catch (Exception exception) {
			log.error("Exception Occured while Creating User", exception);
		}
	}

}
