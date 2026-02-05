package com.cts.empMgmt.Repository;

import com.cts.empMgmt.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Long> {
    // finding employees by department
    List<Employee> findByDepartment(String department);
}
