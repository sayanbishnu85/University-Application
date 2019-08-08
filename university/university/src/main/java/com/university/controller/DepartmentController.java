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
import com.university.model.Department;
import com.university.repository.DepartmentRepository;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

	@Autowired
	DepartmentRepository departmentRepository;

	// get all department
	@GetMapping("/")
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	// Create a new department
	@PostMapping("/add")
	public Department createDepartment(@Valid @RequestBody Department department) {
		return departmentRepository.save(department);
	}

	// Get a Single department
	@GetMapping("/{id}")
	public Department getDepartmentById(@PathVariable(value = "id") Long departmentId) {
		return departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentRepository));
	}

	// Update a department
	@PutMapping("/update/{id}")
	public Department updateDepartment(@PathVariable(value = "id") Long departmentId,
			@Valid @RequestBody Department departmentDetails) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
		department.setName(departmentDetails.getName());
		department.setCount(departmentDetails.getCount());
		Department updatedDepartment = departmentRepository.save(department);
		return updatedDepartment;
	}

	// Delete a department
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
		departmentRepository.delete(department);
		return ResponseEntity.ok().build();
	}
}