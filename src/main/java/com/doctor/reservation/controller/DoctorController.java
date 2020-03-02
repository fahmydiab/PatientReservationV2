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
import com.doctor.reservation.repository.AppointmentRepository;
import com.doctor.reservation.repository.DoctorRepository;

@RestController()
public class DoctorController {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	// To retrieve all patients
	@GetMapping("/doctors")
	public List<Doctor> retrieveAllDoctors() {
		return doctorRepository.findAll();
	}

	// To find a patient by id
	@GetMapping("/doctors/{id}")
	public EntityModel<Doctor> retrievePatient(@PathVariable int id) {
		Optional<Doctor> doctor = doctorRepository.findById(id);
		if (!doctor.isPresent()) {
			throw new EntityNotFoundException("id-" + id);
		}

		EntityModel<Doctor> resource = new EntityModel<Doctor>(doctor.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllDoctors());
		resource.add(linkTo.withRel("all-doctors"));
		// HATEOAS

		return resource;
	}

	@DeleteMapping("/doctors/{id}")
	public void deleteDoctor(@PathVariable int id) {
		doctorRepository.deleteById(id);
	}

	@PutMapping("/doctors/{id}")
	public ResponseEntity<Object> updateDoctor(@RequestBody Doctor doctor, @PathVariable int id) {
		System.out.println("111111111111111");

		Optional<Doctor> doctorOptional = doctorRepository.findById(id);
		System.out.println("222222222222222");

		if (!doctorOptional.isPresent())
			return ResponseEntity.notFound().build();

		doctor.setId(id);

		Doctor managerDr = doctorRepository.findById(doctor.getManagerDr().getId()).get();
		if (managerDr != null) {
			managerDr.getTeam().add(doctor);
		}

		doctorRepository.save(doctor);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/doctors")
	public ResponseEntity<Object> createPatient(@Valid @RequestBody Doctor doctor) {

		if (doctor.getManagerDr() != null) {

			Optional<Doctor> managerDr = doctorRepository.findById(doctor.getManagerDr().getId());
			managerDr.get().getTeam().add(doctor);
		}

		Doctor savedDoctor = doctorRepository.save(doctor);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedDoctor.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/doctors/{doctorId}/appointments")
	public List<Appointment> retrieveAppointmentByDoctorId(@PathVariable int doctorId) {

		return appointmentRepository.findByDoctorId(doctorId);

	}

}
