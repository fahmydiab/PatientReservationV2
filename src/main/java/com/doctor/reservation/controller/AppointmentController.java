package com.doctor.reservation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import com.doctor.reservation.entity.Appointment;
import com.doctor.reservation.entity.Doctor;
import com.doctor.reservation.entity.Patient;
import com.doctor.reservation.repository.AppointmentRepository;
import com.doctor.reservation.repository.DoctorRepository;
import com.doctor.reservation.repository.PatientRepository;

@RestController()
public class AppointmentController {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	// To retrieve all appointments
	@GetMapping("/appointments")
	public List<Appointment> retrieveAllAppointments() {

		return appointmentRepository.findAll();
	}

	// To find an appointment by id
	@GetMapping("/appointments/{id}")
	public EntityModel<Appointment> retrieveAppointment(@PathVariable int id) {

		Optional<Appointment> appointment = appointmentRepository.findById(id);
		if (!appointment.isPresent()) {
			throw new EntityNotFoundException("id-" + id);
		}

		EntityModel<Appointment> resource = new EntityModel<Appointment>(appointment.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllAppointments());
		resource.add(linkTo.withRel("all-appointments"));
		// HATEOAS

		return resource;
		
	}

	@DeleteMapping("/appointments/{id}")
	public void deleteAppointment(@PathVariable int id) {
		appointmentRepository.deleteById(id);
	}

	@PutMapping("/appointments/{id}")
	public ResponseEntity<Object> updateDoctor(@RequestBody Appointment appointment, @PathVariable int id) {

		Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);

		if (!appointmentOptional.isPresent())
			return ResponseEntity.notFound().build();

		appointment.setAppointmentId(id);

		appointmentRepository.save(appointment);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/appointments")
	public ResponseEntity<Object> createPatient(@Valid @RequestBody Appointment appointment) {

		System.out.println("111111111111111111");
		Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).get();
		doctor.add(appointment);
		
		Patient patient = patientRepository.findById(appointment.getPatient().getId()).get();
		patient.add(appointment);
		
		System.out.println("2222222222");
		Appointment savedAppointment = appointmentRepository.save(appointment);

		System.out.println("3333333333333");
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(savedAppointment.getAppointmentId()).toUri();

		System.out.println("44444444444444444");
		return ResponseEntity.created(uri).build();
	}
	

	
	
}
