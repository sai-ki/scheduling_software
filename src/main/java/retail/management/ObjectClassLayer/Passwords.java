package retail.management.ObjectClassLayer;

import org.springframework.stereotype.Service;

@Service
public class Passwords {
    private String oldPassword;
    private String newPassword;

    public Passwords() {
    }

    public Passwords(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
