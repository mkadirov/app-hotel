package pdp.apphotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.apphotel.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    boolean existsByName(String name);
}
