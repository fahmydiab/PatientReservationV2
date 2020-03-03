package com.doctor.reservation.controller;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

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

import com.doctor.reservation.controller.PatientController;
import com.doctor.reservation.entity.Patient;
import com.doctor.reservation.repository.PatientRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PatientController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientRepository patientRepository;

	Patient mockPatient = new Patient(999, "Raul", null, "male");
	String examplePatientJson = "{\"name\":\"Raul\",\"birthDate\":null,\"gender\":\"male\",\"id\":999}";

	@Test
	public void retrievePatient() throws Exception {
		Mockito.when(patientRepository.findById(Mockito.anyInt()).get()).thenReturn(mockPatient);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients/id").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{name:Raul,birthDate:null,gender:male,id:999}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createPatient() throws Exception {
		Patient mockPatient = new Patient(777, "Aya", new SimpleDateFormat("yyyy-MM-dd").parse("1992-12-05"), "female");
		Mockito.when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(mockPatient);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patients").accept(MediaType.APPLICATION_JSON)
				.content(examplePatientJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		assertEquals("http://localhost/patients/777", 
				response.getHeader(HttpHeaders.LOCATION));

	}

}
