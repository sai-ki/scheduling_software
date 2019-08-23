package retail.management.ObjectClassLayer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Set;

public class Employee {
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    private String employeeId;
    private String employeeName;
    private String employeeUsername;
    private String role;
    private String email;
    private boolean active;
    private String departmentName;
    private List<Availability> employeeAvailability;

    public List<Availability> getEmployeeAvailability() {
        return employeeAvailability;
    }

    public void setEmployeeAvailability(List<Availability> employeeAvailability) {
        this.employeeAvailability = employeeAvailability;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Employee() {
    }

    public Employee(String employee_id, String employee_name, String employee_username, String role, String email, boolean active, String departmentName, List<Availability>employeeAvailability) {
        this.employeeId = employee_id;
        this.employeeName = employee_name;
        this.employeeUsername = employee_username;
        this.role = role;
        this.email = email;
        this.active = active;
        this.departmentName=departmentName;
        this.employeeAvailability=employeeAvailability;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
