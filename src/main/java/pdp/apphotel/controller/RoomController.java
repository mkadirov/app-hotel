package pdp.apphotel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pdp.apphotel.entity.Hotel;
import pdp.apphotel.entity.Room;
import pdp.apphotel.payload.RoomDto;
import pdp.apphotel.repository.HotelRepository;
import pdp.apphotel.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public Page<Room> getRoomAllRooms(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 15);
        return roomRepository.findAll(pageable);
    }

    @GetMapping("/hotelId/{id}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer id, @RequestParam int page){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Pageable pageable = PageRequest.of(page, 10);
            return roomRepository.findAllByHotelId(id, pageable);
        }
        return null;
    }
    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        boolean isAdded = false;
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            if (roomRepository.existsByNumberAndHotelId(roomDto.getNumber(), roomDto.getHotelId())) {
                return "Room Number exists";
            }
            Room room = new Room(null, roomDto.getNumber(), roomDto.getFloor(), roomDto.getSize(), hotel);
            roomRepository.save(room);
            isAdded = true;
        }
        return isAdded? "Successfully added":"Hotel not found";
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        return optionalRoom.orElseGet(Room::new);
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        boolean isEdited = false;
        Optional<Room> optionalRoom = roomRepository.findById(id);
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent() && optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            if(room.getNumber()!= room.getNumber()) {
                if (roomRepository.existsByNumberAndHotelId(roomDto.getNumber(), roomDto.getHotelId())) {
                    return "Room Number exists";
                }
                room.setNumber(roomDto.getNumber());
            }
            room.setSizeOfRoom(roomDto.getSize());
            room.setFloor(roomDto.getFloor());
            room.setHotel(optionalHotel.get());
            roomRepository.save(room);
            isEdited = true;
        }
        return isEdited? "Successfully edited":"Hotel or Room not found";
    }

    @DeleteMapping ("/{id}")
    public String deleteRoomById(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            roomRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted? "Successfully deleted":"Room not found";
    }

}
