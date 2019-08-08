package com.university.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.university.exception.ResourceNotFoundException;
import com.university.model.Student;
import com.university.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/")
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	// Create a new Student
	@PostMapping("/add")
	public Student createStudent(@Valid @RequestBody Student student) {
		return studentRepository.save(student);
	}

	// Get a Single Student
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable(value = "id") Long studentId) {
		return studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentRepository));
	}

	// Update a Student
	@PutMapping("/update/{id}")
	public Student updateStudent(@PathVariable(value = "id") Long studentId,
			@Valid @RequestBody Student studentDetails) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
		student.setCity(studentDetails.getCity());
		student.setDepartmentId(student.getDepartmentId());
		student.setName(studentDetails.getName());
		student.setRank(studentDetails.getRank());
		student.setSection(studentDetails.getSection());
		student.setYear(studentDetails.getYear());
		Student updatedStudent = studentRepository.save(student);
		return updatedStudent;
	}

	// Delete a Student
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
		studentRepository.delete(student);
		return ResponseEntity.ok().build();
	}

}