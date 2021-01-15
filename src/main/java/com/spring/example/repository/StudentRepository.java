package com.spring.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.example.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	List<Student> findAllByGrade(String grade);

}