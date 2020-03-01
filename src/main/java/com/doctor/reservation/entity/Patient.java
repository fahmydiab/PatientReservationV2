package com.doctor.reservation.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "patient")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	private int patientId;

	@Column(name = "name")
	@NotNull
	@Size(min = 3, message = "Name should have at least 3 characters")
	private String name;

	@Column(name = "birth_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Past
	private Date birthDate;

	@Column(name = "gender")
	@NotNull
	private String gender;

	@OneToMany(mappedBy = "patient",
			cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Appointment> appointments = new ArrayList<>();

	public int getId() {
		return patientId;
	}

	public void setId(int id) {
		this.patientId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Patient(int id, String name, Date birthDate, String gender) {
		super();
		this.patientId = id;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
	}


	public Patient() {
		super();
	}

	@Override
	public String toString() {
		return "Patient [id=" + patientId + ", name=" + name + ", birthDate=" + birthDate + ", gender=" + gender + "]";
	}

	public void add(Appointment tempAppointment) {
		if(appointments==null) {
			appointments=new ArrayList<>();
		}
		appointments.add(tempAppointment);
		tempAppointment.setPatient(this);
	}
}
