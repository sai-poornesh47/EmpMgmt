package com.cts.empMgmt.Service;

import com.cts.empMgmt.Model.Employee;
import com.cts.empMgmt.Repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmpService {
    private final EmpRepository repo;

    @Autowired
    public EmpService(EmpRepository repo) {
        this.repo = repo;
    }

    public List<Employee> getAll() {
        List<Employee> employees = repo.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees currently exist in the system.");
        }
        return employees;
    }

    public Optional<Employee> getById(Long id) {
        return repo.findById(id);
    }

    public Employee create(Employee employee) {
        employee.setId(null);
        return repo.save(employee);
    }

    public Optional<Employee> update(Long id, Employee employee) {
        return repo.findById(id).map(existing -> {
            existing.setName(employee.getName());
            existing.setDepartment(employee.getDepartment());
            existing.setEmail(employee.getEmail());
            existing.setSalary(employee.getSalary());
            return repo.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Employee> findByDepartment(String department) {

        try {
            return repo.findByDepartment(department);
        } catch (NoSuchMethodError | AbstractMethodError e) {
            return repo.findAll().stream()
                    .filter(e1 -> e1.getDepartment() != null && e1.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        }
    }
}
