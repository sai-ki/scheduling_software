package retail.management.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import retail.management.ObjectClassLayer.*;
import retail.management.ServiceLayer.ServiceImplement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class BusinessController
{
    @Autowired
    private ServiceImplement serviceImplement;
    @Autowired
    private Passwords passwords;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UsernameList usernameList;
    UserInfo user = new UserInfo();
    String date;

    /**
     *sends a page to add user details
     * @param model (adds userInfo.class)
     * @return registration.html page
     */
    @GetMapping("/register")
    public String registration(Model model){
        model.addAttribute("newUser", user);
        return "registration";
    }

    /**
     * saves the received user details from registration page into database and also prevents same usernames
     * @param userinfo
     * @return redirects to "/profile" mapping
     */
    @PostMapping("/save")
    public String saveRegistration(@ModelAttribute UserInfo userinfo){
       if(serviceImplement.existingUser(userinfo.getUsername())){
           return "redirect:/register";
        }
       else {
           if(userinfo.getName()==null||userinfo.getName().isEmpty()){
           userinfo.setName(userinfo.getUsername());}else{}
           serviceImplement.saveToDatabase(userinfo);
           Employee e = new Employee(userinfo.getId(), userinfo.getName(), userinfo.getUsername(), userinfo.getRoles(), userinfo.getEmail(), userinfo.isActive(),userinfo.getDepartment(),userinfo.getAvailability());
           String ss= restTemplate.postForObject("http://192.168.0.18:8080/employee", e, String.class);
           return "redirect:/profile";
       }
    }

    /**
     * home page
     * @return home.html
     */
    @GetMapping("/home")
    public String returnHomePage(){
        return "home";
    }

    /**
     * returns login page
     * @return login.html
     */
    @GetMapping("/login")
    public String loginProcess(){
        return "login";
    }

    /**
     * returns administrator page when clicked on administrator tab in home page
     * @return admin/access.html
     */
    @GetMapping("/admin")
    public String adminAccess(){
        return "admin/access";
    }

    /**
     * used to access the user information and gives functionality to reset passwords and edit information
     * @param model user:current logged-in user, allUsers: all the employees working in the same department
     * @param auth gets logged in username
     * @return profile.html
     */
    @GetMapping("/profile")
    public String profileAccess(Model model, Authentication auth){
       user = serviceImplement.getUserDetails(auth.getName());
       List<UserInfo> allUsers = serviceImplement.getAllUserDetails(user.getDepartment());
       model.addAttribute("user",user);
       model.addAttribute("allemployes",allUsers);
        return "profile";
    }

    /**
     * returns page to resetting password
     * @param model password.class: contains old and new password variables
     * @return passwordReset.html page
     */
    @GetMapping("/PasswordReset")
      public String resetPassword(Model model){
      model.addAttribute("passwords", passwords);
       return "passwordReset";
    }

    /**
     * saves the received new password into database and redirects to profile page
     * @param password: recieves input from user with old and new passwords
     * @param auth used to retrieve logged in user
     * @return "/profile" mapping
     */
    @PostMapping("/SavePassword")
    public String save(@ModelAttribute Passwords password, Authentication auth){
        System.out.println(password.getNewPassword());
        Boolean verified = serviceImplement.verifyPasswords(password.getOldPassword(), auth.getName());
         if(verified)
            serviceImplement.savePasswordToDataBase(password.getNewPassword(),auth.getName());
        return "redirect:/profile";

    }

    /**
     * provides access to edit user info in profile page
     * @param model user.class:to give him acess to set password
     * @param auth used to get the current loggedIn user
     * @return editUserDetails.html page
     */
    @GetMapping("/EditDetails")
    public String editUserDetails(Model model,Authentication auth){
        model.addAttribute("user",serviceImplement.getUserDetails(auth.getName()));
        return "editUserDetails";
    }

     /**
     * saves the received edited user details from profile tab and saves it in database
     * @param updatedUser: User info recievied from the user after updating it
     * @param auth used to get the current loggedin user
     * @return profile.html page
     */
    @PostMapping("/updateAndSave")
    public String UpdateAndSave(@ModelAttribute UserInfo updatedUser,Authentication auth)
    {
        UserInfo databaseUser = serviceImplement.getUserDetails(auth.getName());
        updatedUser.setEditedUserDetails(databaseUser.getPassword(),databaseUser.getRoles(),databaseUser.getPermissions(),databaseUser.isActive());
        serviceImplement.saveToDatabase(updatedUser);
        Employee e = new Employee(updatedUser.getId(), updatedUser.getName(), updatedUser.getUsername(), updatedUser.getRoles(), updatedUser.getEmail(), updatedUser.isActive(),updatedUser.getDepartment(),updatedUser.getAvailability());
        String ss= restTemplate.postForObject("http://192.168.0.18:8080/employee",e, String.class);
        return "redirect:/profile";
    }

    /**
     * accessed when clicked on addJobs tab to get the task assigned by Manager. accessed by manager only
     * @param model: task.class to get task info
     * @return CreateTask1.html page
     */
    @GetMapping("/addJobs")
    public String addNewJobs(Model model){
        model.addAttribute("task", new Task());
        return "CreateTask1";
    }

    /**
     * returning employess who are available to perform the task based on the task info provided by acessing addJobs tab
     * @param model task.class
     * @param task object which is received from the user
     * @param auth
     * @return ListofEmployees.html
     */
    @GetMapping("/viewEmployees")
    public String returnEmployees(Model model, @ModelAttribute Task task, Authentication auth){
       user = serviceImplement.getUserDetails(auth.getName());
       date = serviceImplement.dateConvert(task.getTask_Date());
        EmployeeListObject employeeListObject= restTemplate.getForObject("http://192.168.0.18:8080/availableEmployeesForTask/"+user.getDepartment()+"/"+date, EmployeeListObject.class);
        model.addAttribute("employees", employeeListObject.getEmployeeList());
        model.addAttribute("task",task);
        model.addAttribute("usernameList", usernameList);
        return "admin/ListOfEmployees";
    }

    /**
     * receives the list of employees assigned to a task, sends the list to micro service and redirects to the viewJobs page
     * @param id taskid
     * @param name task name
     * @param date task date
     * @param usernameList: selected users by the manager
     * @param auth used to get te logged in user, manager
     * @return viewjobs.html
     */
    @GetMapping("/SendList/{id}/{name}/{date}")
    public String ViewJobs(@PathVariable(value="id") String id, @PathVariable(value="name")String name, @PathVariable(value="date") Date date, @ModelAttribute UsernameList usernameList, Authentication auth){
        String users=String.join("_",usernameList.getUsernames());
        user= serviceImplement.getUserDetails(auth.getName());
        String date1 = serviceImplement.dateConvert(date);
        String status = restTemplate.getForObject("http://192.168.0.18:8080/addTask/"+id+"/"+name+"/"+date1+"/"+user.getDepartment()+"/"+users, String.class);
        System.out.println(status+" "+"task saved");
        return "redirect:/viewJobs";
    }

    /**
     * retuns all the tasks assigned to employees when clicked on viewJobs tab
     * @param model
     * @param auth
     * @return tasks.html page
     */
    @GetMapping("/viewJobs")
    public String ViewJobs(Model model, Authentication auth){
        TasksListObject tasks = restTemplate.getForObject("http://192.168.0.18:8080/employeeActiveTasks/"+auth.getName(), TasksListObject.class);
        model.addAttribute("tasks",tasks.getTaskList());
        return "tasks";
    }

    /**
     * returns all the employees present in a assigned task.
     * @param model employees: returns list of employees in that task
     * @param taskid present task id
     * @return employeesInSavedTask.html
     */
    @GetMapping("/viewJobEmployees/{taskid}")
    public String ViewEmployeesInJobs(Model model,@PathVariable(value="taskid")String taskid){
        EmployeeListObject employees = restTemplate.getForObject("http://192.168.0.18:8080/employeesInTask/"+taskid,EmployeeListObject.class);
        model.addAttribute("employees",employees.getEmployeeList());
        model.addAttribute("taskid",taskid);
        return "employeesInSavedTask";
    }

    /**
     * used to delete the given task
     * @param taskid
     * @return redirects to "/vieJobs" mapping
     */
    @DeleteMapping("/deleteTask/{taskid}")
    public String DeleteTask(@PathVariable(value = "taskid")String taskid){
        Map<String,String> params=new HashMap<String, String>();
        params.put("taskid",taskid);
       restTemplate.delete("http://192.168.0.18:8080/removeTask/{taskid}",params);
        return "redirect:/viewJobs";
}

    /**
     * mapping to send user info to microservice 2 by post man, not accessed to frontend
    @ResponseBody
    @GetMapping("/postEmployee")
    public String PostEmployee( Authentication auth){
        user = serviceImplement.getUserDetails(auth.getName());
            Employee e = new Employee(user.getId(), user.getName(), user.getUsername(), user.getRoles(), user.getEmail(), user.isActive(),user.getDepartment());
            String ss= restTemplate.postForObject("http://192.168.0.18:8080/employee", e, String.class);
        return "okay";
    }
    //mapping to send List of user info to microservice 2
    @ResponseBody
    @GetMapping("/postAllEmployeesInDept")
    public String PostAllEmployees( Authentication auth){
        user = serviceImplement.getUserDetails(auth.getName());
        List<UserInfo>userInfoList=serviceImplement.getAllUserDetails(user.getDepartment());
        for (UserInfo user1:userInfoList) {
            Employee e = new Employee(user1.getId(), user1.getName(), user1.getUsername(), user1.getRoles(), user1.getEmail(), user1.isActive(),user1.getDepartment());
            String ss= restTemplate.postForObject("http://192.168.0.18:8080/employee", e, String.class);
        }
        return "okay";
        }
    */

}
