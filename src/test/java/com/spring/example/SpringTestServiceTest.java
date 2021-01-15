package com.spring.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.example.model.Student;
import com.spring.example.repository.StudentRepository;
import com.spring.example.service.StudentService;

@SpringBootTest
class SpringTestServiceTest {

	@InjectMocks
	private StudentService studentService;
	
	@Mock
	private StudentRepository studentRepository;
	
	@Test
	public void addNewStudent() {
		Student student1 = new Student(1, "Rajeevan", "13A", 33, "090018");
		
		when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student1);
		
		Student studentSrv = studentService.addStudent(student1);
		
		assertEquals(student1, studentSrv);
		verify(studentRepository,times(1)).save(student1);
	}
	
	@Test
	public void getAllStudentsTest() {
		Student student1 = new Student(1, "Rajeevan", "13A", 33, "090018");
		Student student2 = new Student(2, "Jivviya", "13A", 33, "090018");
		Student student3 = new Student(3, "Kishaalyan", "K1", 4, "090018");
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		
		when(studentRepository.findAll()).thenReturn(studentList);
		
		List<Student> studentListSrv = studentService.getAllStudents();
		
		assertEquals(3, studentListSrv.size());
		
		verify(studentRepository,times(1)).findAll();
	}
	
	@Test
	public void getAllStudentsForGrade() {
		Student student1 = new Student(1, "Rajeevan", "13A", 33, "090018");
		Student student2 = new Student(2, "Jivviya", "13A", 33, "090018");
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(student1);
		studentList.add(student2);
		
		when(studentRepository.findAllByGrade(Mockito.anyString())).thenReturn(studentList);
		
		List<Student> studentListSrv = studentService.getAllStudentsGrade("13A");
		
		assertEquals(2, studentListSrv.size());
		verify(studentRepository,times(1)).findAllByGrade("13A");
	}

}
