package com.example.pr6_firstmicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class Pr6FirstMicroserviceApplicationTests {

	@Autowired
	MockMvc mockMvc;


	@Test
	void testGetAllUsers() throws Exception {
		mockMvc.perform(get("/users/get_all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("""
                        [
                            {
                                "id":1,
                                "nickname":"Windflaw"
                            },
                            {
                                "id":2,
                                "nickname":"Valikov Kirill"
                            },
                            {
                                "id":3,
                                "nickname":"Denis Pak"
                            }
                          
                        ]
                        """));
	}

	@Test
	void testGetUserById() throws Exception {
		mockMvc.perform(get("/users/" + 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("""
                        {
                            "id":1,
                            "nickname":"Windflaw"
                        }
                        """));
	}

//    @Test
//    void testCreateUser() throws Exception {
//        mockMvc.perform(post("/users/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                            "nickname": "Denis Pak"
//                        }
//                        """))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("""
//                        {
//                            "id":3,
//                            "nickname":"Denis Pak"
//                        }
//                        """));
//
//    }

	@Test
	void testCreateAppeal_UserServiceClient() throws Exception {

	}

}
