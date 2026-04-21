package com.aiswarya.ems.service;

import com.aiswarya.ems.dto.EmployeeDTO;
import com.aiswarya.ems.entity.Employee;
import com.aiswarya.ems.exception.ResourceNotFoundException;
import com.aiswarya.ems.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    // Logger object for this class
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {

        // Log before saving employee
        logger.info("Saving new employee with email: {}", dto.getEmail());

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);

        // Log after saving
        logger.info("Employee saved successfully with id: {}", savedEmployee.getId());

        return mapToDTO(savedEmployee);
    }

    // GET ALL
    public List<EmployeeDTO> getAllEmployees() {
        // Log fetching employees
        logger.info("Fetching all employees");
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public EmployeeDTO getEmployeeById(Long id) {
        logger.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        return mapToDTO(employee);
    }

    // UPDATE
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        System.out.println("===== UPDATE API CALLED =====");
        System.out.println("ID: " + id);
        System.out.println("DTO name: " + dto.getName());
        System.out.println("DTO department: " + dto.getDepartment());
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        if (dto.getName() != null) {
            existingEmployee.setName(dto.getName());
        }
        if (dto.getDepartment() != null) {
            existingEmployee.setDepartment(dto.getDepartment());
        }
        if (dto.getEmail() != null) {
            existingEmployee.setEmail(dto.getEmail());
        }

        if (dto.getSalary() != null) {
            existingEmployee.setSalary(dto.getSalary());
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return mapToDTO(updatedEmployee);
    }

    // DELETE
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    // Method to get paginated + sorted employees
    public Page<EmployeeDTO> getEmployees(Pageable pageable, String search) {
        // Fetch employees from database with pagination and sorting
        Page<Employee> employeePage;
        if (search != null && !search.isEmpty()) {
            employeePage = employeeRepository
                    .findByNameContainingIgnoreCase(search, pageable);
        } else {
            employeePage = employeeRepository.findAll(pageable);
        }
        // Convert Entity page to DTO page
        // map() converts each Employee -> EmployeeDTO
        return employeePage.map(this::mapToDTO);
    }
    // Search employees by name
    public List<EmployeeDTO> searchByName(String name) {

        // Call repository to get employees from DB
        List<Employee> employees = employeeRepository.findByName(name);

        // Convert Entity list to DTO list
        return employees.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Search employees by department
    public List<EmployeeDTO> searchByDepartment(String department) {

        // Fetch employees from database
        List<Employee> employees = employeeRepository.findByDepartment(department);

        // Convert Entity -> DTO
        return employees.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ===== Mapping Methods =====

    private Employee mapToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }
}