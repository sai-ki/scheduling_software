package retail.management.SecurityLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import retail.management.ObjectClassLayer.UserInfo;
import retail.management.RepositoryLayer.SqlDatabase;
@Service
public class UserAuthenticationService implements UserDetailsService {
    @Autowired
    private SqlDatabase sqlDatabase;

    public UserAuthenticationService(SqlDatabase sqlDatabase) {
        this.sqlDatabase = sqlDatabase;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = sqlDatabase.findByUsername(s);
        AuthUserDetails authUserDetails = new AuthUserDetails(userInfo);
        return authUserDetails;
    }
}
