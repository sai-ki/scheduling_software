package retail.management.ObjectClassLayer;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Task {

    private String task_id;
    private String task_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date task_Date;



    public Task() {
    }

    public Task(String task_id, String task_name, Date task_Date) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_Date = task_Date;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Date getTask_Date() {
        return task_Date;
    }

    public void setTask_Date(Date task_Date) {
        this.task_Date = task_Date;
    }

   /* public List<Employee> getTask_employeeList() {
        return task_employeeList;
    }

    public void setTask_employeeList(List<Employee> task_employeeList) {
        this.task_employeeList = task_employeeList;
    }

    public Department getTaskDepartment() {
        return taskDepartment;
    }

    public void setTaskDepartment(Department taskDepartment) {
        this.taskDepartment = taskDepartment;
    }
      public Task(String task_id, String task_name, Date task_Date, List<Employee> task_employeeList, Department taskDepartment) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_Date = task_Date;
        this.task_employeeList = task_employeeList;
        this.taskDepartment = taskDepartment;
    }*/
}
