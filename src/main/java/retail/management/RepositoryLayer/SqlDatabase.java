package retail.management.RepositoryLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import retail.management.ObjectClassLayer.UserInfo;

import java.util.List;

@Service
public interface SqlDatabase extends CrudRepository<UserInfo, String> {
    UserInfo findByUsername(String username);
    List<UserInfo>findAllByDepartment(String department);
    boolean existsByUsername(String username);
}
