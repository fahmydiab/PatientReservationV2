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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import com.doctor.reservation.entity.Patient;
import com.doctor.reservation.repository.PatientRepository;

@RestController()
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	// To retrieve all patients
	@GetMapping("/patients")
	public List<Patient> retrieveAllPatients() {
		System.out.println("_+_=-===----------------------");

		return patientRepository.findAll();
	}

	// To find a patient by id
	@GetMapping("/patients/{id}")
	public EntityModel<Patient> retrievePatient(@PathVariable int id) {
		Optional<Patient> patient = patientRepository.findById(id);
		if (!patient.isPresent()) {
			throw new EntityNotFoundException("id-" + id);
		}

		EntityModel<Patient> resource = new EntityModel<Patient>(patient.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllPatients());
		resource.add(linkTo.withRel("all-patients"));
		// HATEOAS

		return resource;
	}
	@DeleteMapping("/patients/{id}")
	public void deletePatient(@PathVariable int id) {
		patientRepository.deleteById(id);
	}

	

	@PostMapping("/patients")
	public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient) {

		Patient savedPatient = patientRepository.save(patient);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedPatient.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
