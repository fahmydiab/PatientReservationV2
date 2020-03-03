package com.doctor.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctor.reservation.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
