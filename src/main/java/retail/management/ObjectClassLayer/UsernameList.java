package retail.management.ObjectClassLayer;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsernameList {
    private List<String> usernames;

    public UsernameList() {
    }

    public UsernameList(List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
