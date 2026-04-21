package com.aiswarya.ems.repository;

import com.aiswarya.ems.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring automatically creates SQL query from method name
    // This will search employees by name
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Employee> findByName(String name);

    // This will search employees by department
    List<Employee> findByDepartment(String department);
}