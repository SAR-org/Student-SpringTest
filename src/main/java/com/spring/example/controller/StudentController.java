package com.spring.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.example.model.Student;
import com.spring.example.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/")
	public String defaultWelcome() {
		return "Hello, World";
	}

	@GetMapping("{studentName}")
	public String helloStudent(@PathVariable String studentName) {
		return "Hello, "+studentName+"! Welcome";
	}
	
	@PostMapping("/add")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		Student createdStudent = studentService.addStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
		
	}
	
	@GetMapping("/all")
	public List<Student> getAllStudents(){
		return studentService.getAllStudents();
	}
	
	@GetMapping("/all/{grade}")
	public List<Student> getAllStudentsForGrade(@PathVariable String grade){
		return studentService.getAllStudentsGrade(grade);
	}
	
}
