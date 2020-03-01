package com.doctor.reservation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointent_id")
	private Integer id;
	
	@Column(name="date_time")
	@NotNull
	private Date dateTime;
	
	@OneToOne
	@NotNull
	@JoinColumn(name="patient_id" ,insertable=false, updatable=false)
	private Patient patient;
	
	@OneToOne
	@NotNull
	@JoinColumn(name="doctor_id" ,insertable=false, updatable=false)
	private Doctor doctor;
	
	@Column(name = "brief_complain")
	@NotNull
	private String briefComplain;

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", dateTime=" + dateTime + ", patient=" + patient + ", doctor=" + doctor
				+ ", briefComplain=" + briefComplain + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getBriefComplain() {
		return briefComplain;
	}

	public void setBriefComplain(String briefComplain) {
		this.briefComplain = briefComplain;
	}

	public Appointment(Integer id, Date dateTime, Patient patient, Doctor doctor, String briefComplain) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.patient = patient;
		this.doctor = doctor;
		this.briefComplain = briefComplain;
	}

	public Appointment() {
		super();
	}
	
	
	
	

}
