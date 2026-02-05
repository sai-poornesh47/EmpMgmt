package com.cts.empMgmt.Controller;

import com.cts.empMgmt.Model.Employee;
import com.cts.empMgmt.Service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
public class EmpController {

    private final EmpService empService;

    @Autowired
    public EmpController(EmpService empService) {
        this.empService = empService;
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> list = empService.getAll();
        return ResponseEntity.ok(list);
    }

    // Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return empService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create new employee
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee created = empService.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return empService.update(id, employee)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = empService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Search by department
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> findByDepartment(@RequestParam String department) {
        List<Employee> results = empService.findByDepartment(department);
        return ResponseEntity.ok(results);
    }
}
