package com.spring.example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.example.model.Student;
import com.spring.example.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentSpringTestApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	String exampleCourseJson = "{\"id\":1,\"name\":\"Kishaalyan\",\"grade\":\"K1\",\"age\":4,\"address\":\"090018\"}";

	
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/student/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Hello, World")));
	}
	
	@Test
	public void shouldReturnCustomMessage() throws Exception {
		this.mockMvc.perform(get("/student/Rajeevan")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Hello, Rajeevan! Welcome")));
	}
	
	@Test
	public void shouldAddStudent() throws Exception {
		Student student = new Student(1, "Rajeevan", "13A", 33, "090018");
		
		Mockito.when(studentService.addStudent(Mockito.any(Student.class))).thenReturn(student);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/student/add")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleCourseJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		//assertEquals(null, null);
	}
	
	@Test
	public void shouldGetAllStudents() throws Exception {
		Student student1 = new Student(1, "Rajeevan", "13A", 33, "090018");
		Student student2 = new Student(2, "Jivviya", "13A", 33, "090018");
		Student student3 = new Student(3, "Kishaalyan", "K1", 4, "090018");
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		
		
		Mockito.when(studentService.getAllStudents()).thenReturn(studentList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/all")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsoString = mapper.writeValueAsString(studentList);
		JSONAssert.assertEquals(jsoString, response.getContentAsString(),false);
	}
	
	@Test
	public void shouldGetAllStudentsForGrade() throws Exception {
		Student student1 = new Student(1, "Rajeevan", "13A", 33, "090018");
		Student student2 = new Student(2, "Jivviya", "13A", 33, "090018");
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(student1);
		studentList.add(student2);
		
		Mockito.when(studentService.getAllStudentsGrade(Mockito.anyString())).thenReturn(studentList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/all/13A")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		ObjectMapper mapper = new ObjectMapper();
		String expectedResult = mapper.writeValueAsString(studentList);
		
		JSONAssert.assertEquals(expectedResult, response.getContentAsString(), false);
	}

}
