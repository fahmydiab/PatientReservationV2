package com.doctor.reservation.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"doctor_id" , "datetime","patient_id"})})
public class Appointment {
	
	@Id
	@GeneratedValue
	private int appointmentId;
	
	@NotNull
	private Date dateTime;
	
	@ManyToOne(cascade =  {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	
	@ManyToOne(cascade =  {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@NotNull
	@Size(min = 5, message = "Name should have at least 5 characters")
	private String briefComplain;

	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	public String getBriefComplain() {
		return briefComplain;
	}

	public void setBriefComplain(String briefComplain) {
		this.briefComplain = briefComplain;
	}


	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}


	public Appointment(int appointmentId, @NotNull Date dateTime, 
			Doctor doctor, Patient patient,
			@NotNull @Size(min = 5, message = "Name should have at least 5 characters") String briefComplain) {
		super();
		this.appointmentId = appointmentId;
		this.dateTime = dateTime;
		this.doctor = doctor;
		this.patient = patient;
		this.briefComplain = briefComplain;
	}

	public Appointment() {
		super();
	}

	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", dateTime=" + dateTime 
				+ ", doctor=" + doctor
				+ ", patient=" + patient 
				+ ", briefComplain=" + briefComplain + "]";
	}

	
	
	
}
