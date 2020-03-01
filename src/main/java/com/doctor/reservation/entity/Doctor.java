package com.doctor.reservation.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="doctor_id")
	private Integer id;
	
	@Column(name = "name")
	@NotNull
	@Size(min=2, message="Name should have at least 2 characters")
	private String name;
	
	@Column(name = "speciality" )
	@NotNull
	private String specialty;
	
	@Column(name="address")
	@NotNull
	private String address;
	
	@Column(name = "education")
	@NotNull
	private String education;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "manager_id")
	@JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
	private Doctor managerDr;
	
	@OneToMany(mappedBy = "managerDr",fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Doctor> team;

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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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
	

	public Set<Doctor> getTeam() {
		return team;
	}

	public void setTeam(Set<Doctor> team) {
		this.team = team;
	}

	

	public Doctor(Integer id, String name, String specialty, String address, String education, Doctor managerDr) {
		super();
		this.id = id;
		this.name = name;
		this.specialty = specialty;
		this.address = address;
		this.education = education;
		this.managerDr = managerDr;
	}

	public Doctor() {
		super();
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", specialty=" + specialty + ", address=" + address
				+ ", education=" + education + ", managerDr=" + managerDr + "]";
	}
	
	
	
	

}
