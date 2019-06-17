package vvproject.restful.Server.Clothing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository where all the clothes are stored.
 *
 * @author Lukas Metzner, sINFlumetz
 */
@Repository("ClothingRepository")
public interface ClothingRepository extends CrudRepository<Clothing, Long> {
}
