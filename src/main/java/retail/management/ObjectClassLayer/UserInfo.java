package retail.management.ObjectClassLayer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Entity
public class UserInfo {
    @Id
    @Column(nullable = false, length = 20)
    private String username;
    @Column( nullable = false, length = 20)
    private String id;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String roles = "";
    private String permissions = "";
   @Column(nullable=false)
    private String email;
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    @ManyToMany
    @JsonBackReference
    private List<Availability> availability;

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }



    public UserInfo() {
    }

    public UserInfo( String username,String id, String name, String password, String roles, String permissions, String department, String email, boolean active,List<Availability>availability) {
        this.username = username;
        this.id=id;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.department=department;
        this.email=email;
        this.active=active;
        if (name.isEmpty()||name==null){
            this.name=username;}
        else{
            this.name=name;
        }
        this.availability=availability;

    }

    public String getUsername() {
        return username;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

    public void setEditedUserDetails(String password, String roles, String permissions, boolean active){
        this.password=password;
        this.roles=roles;
        this.permissions=permissions;
        this.active=active;
    }

}
