package com.spring.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.example.model.Student;
import com.spring.example.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;

	public Student addStudent(Student student) {
		return studentRepository.save(student);	
	}
	
	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
	public List<Student> getAllStudentsGrade(String grade){
		return studentRepository.findAllByGrade(grade);
	}
}
