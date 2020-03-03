package com.doctor.reservation.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.doctor.reservation.entity.Doctor;
import com.doctor.reservation.repository.AppointmentRepository;
import com.doctor.reservation.repository.DoctorRepository;

@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorRepository doctorRepository;
	
	@MockBean
	private AppointmentRepository appointmentRepository;

	Doctor mockDoctor = new Doctor(2005,"Ali","eyes","Cairo","Alex university",null, null, null);
	Doctor mockDoctor2 = new Doctor(2105,"Mohamed","skin","Zagazig","Zagazig university",null, null, null);

	List<Doctor> mockDoctorList = Arrays.asList(new Doctor[] {mockDoctor,mockDoctor2});
	
	String exampleDoctorJson = "{\"id\":1,\"name\":\"mohamed omar \",\"specialization\":\"teeth\",\"address\":\"mansura\",\"education\":\"mansura university\",\"managerDr\":null,\"team\":[]}";
	
	@Test
	public void retrieveAllDoctors() throws Exception {
		Mockito.when(doctorRepository.findAll()).thenReturn(mockDoctorList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[{id:2005,name:Ali,specialization:eyes},{id:2105,name:Mohamed,specialization:skin}]";
		
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
	
	@Test
	public void deleteDoctor() throws Exception{
		Doctor mockDoctor = new Doctor(545, "Aya", "eyes","Zagazig","Alex university",null, null, null);

		doctorRepository.delete(mockDoctor);

		verify(doctorRepository, times(1)).delete(mockDoctor);
	}

}
