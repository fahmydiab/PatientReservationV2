package com.doctor.reservation.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.doctor.reservation.entity.Doctor;
import com.doctor.reservation.repository.DoctorRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DoctorController.class)
public class DoctorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorRepository doctorRepository;

	Doctor mockDoctor = new Doctor(2005,"Ali","eyes","Zagazig","Alex university",null, null, null);
	String exampleDoctorJson = "{\"name\":\"Ali\",\"specialization\":eyes,\"address\":\"Zagazig\",\"education\":Alex university\",\"managerDr\":null,\"team\":null}";
	
	@Test
	public void retrieveDoctor() throws Exception {
		Mockito.when(doctorRepository.findById(Mockito.anyInt()).get()).thenReturn(mockDoctor);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/id").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{name:Ali,specialization:eyes,address:Zagazig,education:Alex university,managerDr:null,team:null}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createDoctor() throws Exception {
		Doctor mockDoctor = new Doctor(545, "Aya", "eyes","Zagazig","Alex university",null, null, null);
		Mockito.when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(mockDoctor);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctors").accept(MediaType.APPLICATION_JSON)
				.content(exampleDoctorJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/doctors/545", 
				response.getHeader(HttpHeaders.LOCATION));

	}

}
