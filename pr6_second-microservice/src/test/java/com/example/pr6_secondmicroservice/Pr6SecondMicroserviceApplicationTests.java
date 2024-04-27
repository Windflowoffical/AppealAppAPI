package com.example.pr6_secondmicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class Pr6SecondMicroserviceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void testGetAllAppeal() throws Exception {
		mockMvc.perform(get("/appeals/get_all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("""
                        [
                        	{
                        		"id": 1,
                        		"description": "Памагите пазалуйста, умалаяю вас",
                        		"user": {
                        			"id": 1,
                        			"nickname": "Windflaw"
                        		},
                        		"status": "ACCEPTED_FOR_WORK"
                        	},
                        	{
                        		"id": 2,
                        		"description": "Need help!",
                        		"user": {
                        			"id": 2,
                        			"nickname": "Valikov Kirill"
                        		},
                        		"status": "ACCEPTED_FOR_WORK"
                        	}
                        ]
                        """));
	}

	@Test
	void testGetAppealById() throws Exception {
		mockMvc.perform(get("/appeals/" + 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("""
                        {
                         	"id": 1,
                         	"description": "Памагите пазалуйста, умалаяю вас",
                         	"user": {
                         		"id": 1,
                         		"nickname": "Windflaw"
                         	},
                         	"status": "ACCEPTED_FOR_WORK"
                         }
                        """));
	}

	@Test
	void testUpdateStatus() throws Exception {
		mockMvc.perform(put("/appeals/" + 1).contentType(MediaType.APPLICATION_JSON)
						.content("""
                        {
                        	"id": 1,
                        	"description": "Памагите пазалуйста, умалаяю вас",
                        	"user": {"id": 1, "nickname": "Windflaw"},
                        	"status": "ACCEPTED_FOR_WORK"
                        }
                        """))
				.andExpect(status().isOk())
				.andExpect(content().string("Статус заявки успешно обновлён"));
	}

//	@Test
//	void testGetAllAppealsByUserId() throws Exception {
//
//	}

}
