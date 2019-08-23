package retail.management.ObjectClassLayer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "availability")
public class Availability {
    @Id
    @Column(length = 10)
    private String day;
    @ManyToMany(mappedBy = "availability", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonBackReference
    private List<UserInfo>userInfos;

    public String getDay() {
        return day;
    }

    public void setDay(String day,List<UserInfo>userInfos) {
        this.day = day;
        this.userInfos=userInfos;
    }

    public Availability() {
    }

    public Availability(String day) {
        this.day = day;
    }


    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }


}
