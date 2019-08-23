package retail.management.ObjectClassLayer;

import java.util.List;

public class TasksListObject {
    private List<Task> TaskList;

    public TasksListObject() {
    }

    public TasksListObject(List<Task> taskList) {
        this.TaskList = taskList;
    }

    public List<Task> getTaskList() {
        return TaskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.TaskList = taskList;
    }
}
