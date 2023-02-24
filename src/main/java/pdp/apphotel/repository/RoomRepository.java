package pdp.apphotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pdp.apphotel.entity.Room;



public interface RoomRepository extends JpaRepository<Room, Integer> {

    Page<Room> findAllByHotelId(Integer hotelId, Pageable pageable);

    boolean existsByNumberAndHotelId(int roomNumber, int hotelId);
}
