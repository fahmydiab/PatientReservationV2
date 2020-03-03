package com.doctor.reservation.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.doctor.reservation.entity.Appointment;
import com.doctor.reservation.entity.Doctor;
import com.doctor.reservation.entity.Patient;
import com.doctor.reservation.repository.AppointmentRepository;
import com.doctor.reservation.repository.DoctorRepository;
import com.doctor.reservation.repository.PatientRepository;

@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientRepository patientRepository;
	
	@MockBean
	private DoctorRepository doctorRepository;
	
	@MockBean
	private AppointmentRepository appointmentRepository;

	Appointment mockAppointment = new Appointment(1005,new Date(),
			new Doctor(1,"fahmy","teeth","Mansura", "Cairo University", null, null, null),
			new Patient(2,"Ahmed",null,"male"),
			"tooth break");
	
	Appointment mockAppointment2 = new Appointment(1006,new Date(),
			new Doctor(1,"fahmy","eyes","Mansura", "Cairo University", null, null, null),
			new Patient(2,"Ahmed",null,"male"),
			"tooth break");

	List<Appointment> mockAppointmentList = Arrays.asList(new Appointment[] {mockAppointment,mockAppointment2});
	
	String exampleAppointmentJson = "{\"appointmentId\":1001,\"dateTime\":\"2020-03-01T22:00:00.000+0000\",\"doctor\":{\"id\":1202,\"name\":\"amr\",\"specialization\":\"teeth\",\"address\":\"cairo\",\"education\":\"cairo university\",\"managerDr\":null,\"team\":[]},\"patient\":{\"name\":\"fahmy\",\"birthDate\":null,\"gender\":\"male\",\"id\":1102},\"briefComplain\":\"tooth removal\"}";
	
	@Test
	public void retrieveAllAppointments() throws Exception {
		Mockito.when(appointmentRepository.findAll()).thenReturn(mockAppointmentList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/appointments").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[{appointmentId:1005},{appointmentId:1006}]";
				
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}


	@Test
	public void deleteAppointment() throws Exception{
		Appointment mockAppointment = new Appointment(1005,new Date(),new Doctor(),new Patient(),"tooth break");

		appointmentRepository.delete(mockAppointment);

		verify(appointmentRepository, times(1)).delete(mockAppointment);
	}

}
