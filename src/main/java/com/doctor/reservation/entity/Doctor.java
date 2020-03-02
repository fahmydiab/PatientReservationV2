package com.doctor.reservation.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.Nullable;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Doctor {

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@Size(min = 2, message = "Name should have at least 2 characters")
	private String name;

	@NotNull
	private String specialization;

	@NotNull
	private String address;

	@NotNull
	private String education;

	@OneToMany(
			mappedBy ="doctor"
			,fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JsonIgnore
	private List<Appointment> appointments;

	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@Nullable
	private Doctor managerDr;
	
	@OneToMany(mappedBy = "managerDr", fetch = FetchType.LAZY)
	private List<Doctor> team;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Doctor getManagerDr() {
		return managerDr;
	}

	public void setManagerDr(Doctor managerDr) {
		this.managerDr = managerDr;
	}

	public List<Doctor> getTeam() {
		return team;
	}

	public void setTeam(List<Doctor> team) {
		this.team = team;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	

	public Doctor(Integer id, @NotNull @Size(min = 2, message = "Name should have at least 2 characters") String name,
			@NotNull String specialization, @NotNull String address, @NotNull String education,
			List<Appointment> appointments, Doctor managerDr, List<Doctor> team) {
		super();
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.address = address;
		this.education = education;
		this.appointments = appointments;
		this.managerDr = managerDr;
		this.team = team;
	}

	public Doctor() {
		super();
	}

	

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", specialization=" + specialization + ", address=" + address
				+ ", education=" + education + ", appointments=" + appointments + ", managerDr=" + managerDr + ", team="
				+ team + "]";
	}

	public void add(Appointment tempAppointment) {
		if(appointments==null) {
			appointments=new ArrayList<>();
		}
		appointments.add(tempAppointment);
		tempAppointment.setDoctor(this);
	}
	
	public void add(Doctor tempDoctor) {
		if(team==null) {
			team=new ArrayList<>();
		}
		System.out.println("111111111111");
		team.add(tempDoctor);
		System.out.println("222222222");
		tempDoctor.setManagerDr(this);
		System.out.println("333333333");
	}
	
	
	
}
