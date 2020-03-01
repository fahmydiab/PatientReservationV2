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
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import com.doctor.reservation.entity.Appointment;
import com.doctor.reservation.entity.Doctor;
import com.doctor.reservation.repository.AppointmentRepository;
import com.doctor.reservation.repository.DoctorRepository;

@RestController()
public class AppointmentController {

	@Autowired
	private AppointmentRepository appointmentRepository;

	// To retrieve all patients
	@GetMapping("/appointments")
	public List<Appointment> retrieveAllAppointments() {

		return appointmentRepository.findAll();
	}

	// To find a patient by id
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

	

	@PostMapping("/appointments")
	public ResponseEntity<Object> createPatient(@Valid @RequestBody Appointment appointment) {

		Appointment savedAppointment = appointmentRepository.save(appointment);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedAppointment.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
