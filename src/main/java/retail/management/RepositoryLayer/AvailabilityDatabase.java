package retail.management.RepositoryLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import retail.management.ObjectClassLayer.Availability;
@Repository
public interface AvailabilityDatabase extends CrudRepository<Availability,String> {

}
