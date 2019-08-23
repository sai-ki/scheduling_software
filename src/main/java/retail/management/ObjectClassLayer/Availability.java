package retail.management.ObjectClassLayer;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "availability")
public class Availability {
    @Id
    @Column(length = 10)
    private String day;
    @ManyToMany(mappedBy = "availability")
 @JsonIgnore
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
