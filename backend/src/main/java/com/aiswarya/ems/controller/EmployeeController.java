package com.aiswarya.ems.controller;

import com.aiswarya.ems.dto.ApiResponse;
import com.aiswarya.ems.dto.EmployeeDTO;
import com.aiswarya.ems.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create a new employee")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO emp) {

        EmployeeDTO saved = employeeService.saveEmployee(emp);

        return new ApiResponse<>(
                true,
                "Employee created successfully",
                saved
        );
    }

    @Operation(summary = "Get all employees")
    // ADMIN + USER both can access
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                employees
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id,
                                      @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/page")
    public Page<EmployeeDTO> getEmployees(@RequestParam(defaultValue = "") String search,Pageable pageable) {
        // Spring automatically reads: ?page=0&size=5&sort=name
        // and creates Pageable object
        // Call service layer to get paginated employees
        return employeeService.getEmployees(pageable, search);
    }
    // Search employees by name
    @GetMapping("/search/name")
    public List<EmployeeDTO> searchEmployeesByName(@RequestParam String name) {
        // Request example: /employees/search/name?name=Aishu
        return employeeService.searchByName(name);
    }

    // Search employees by department
    @GetMapping("/search/department")
    public List<EmployeeDTO> searchEmployeesByDepartment(@RequestParam String department) {
        // Request example: /employees/search/department?department=IT
        return employeeService.searchByDepartment(department);
    }
}