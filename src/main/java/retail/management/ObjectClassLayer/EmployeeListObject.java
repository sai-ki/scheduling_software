package retail.management.ObjectClassLayer;

import java.util.List;

public class EmployeeListObject {
    private List<Employee> employeeList;

    public EmployeeListObject() {
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
