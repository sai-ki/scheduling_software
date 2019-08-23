package retail.management.ServiceLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import retail.management.ObjectClassLayer.UserInfo;
import retail.management.RepositoryLayer.SqlDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ServiceImplement {
    @Autowired
    private SqlDatabase sqlDatabase;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * retuns userInfo based on username
     * @param name
     * @return
     */
    public UserInfo getUserDetails(String name) {
        return sqlDatabase.findByUsername(name);
    }

    /**
     * saves the user to database
     * @param userInfo
     */
    public void saveToDatabase(UserInfo userInfo) {
        if(userInfo.getPassword().startsWith("$")) { }
        else{userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));}
        sqlDatabase.save(userInfo);
    }

    /**
     * retuns all the users in a departmnet
     * @param department
     * @return
     */
    public List<UserInfo> getAllUserDetails(String department) {
        return sqlDatabase.findAllByDepartment(department);
    }

    /**
     * verifies old password  provided by the user to the database password
     * @param oldPassword
     * @param username
     * @return boolean
     */
    public boolean verifyPasswords(String oldPassword, String username) {
        String storedPassword = this.getUserDetails(username).getPassword();
        return passwordEncoder.matches(oldPassword, storedPassword);
    }

    /**
     * saves the new password to databse after verification of the provided old password
     * @param newPassword
     * @param username
     */
    public void savePasswordToDataBase(String newPassword, String username){
        UserInfo user=this.getUserDetails(username);
        user.setPassword(newPassword);
        this.saveToDatabase(user);
    }

    /**
     * verification of the provided user in database
     * @param username
     * @return boolean
     */
    public boolean existingUser(String username){ return sqlDatabase.existsByUsername(username);}

    /**
     * coverts the format of the date received from user input
     * @param date
     * @return
     */
    public String dateConvert(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);}
}

