package pdp.apphotel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pdp.apphotel.entity.Hotel;
import pdp.apphotel.repository.HotelRepository;

import java.util.Optional;


@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping()
    public Page<Hotel> getHotelList(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        return hotelRepository.findAll(pageable);
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        boolean isAdded = false;
        if(hotel.getName()!=null) {
            if(hotelRepository.existsByName(hotel.getName())){
                return "Hotel exists";
            }
            hotelRepository.save(hotel);
            isAdded = true;
        }
        return isAdded? "Successfully added":"Hotel should has name";
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElseGet(Hotel::new);
    }
    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel){
        boolean isEdited = false;
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent() && hotel.getName()!=null){
            Hotel editingHotel = optionalHotel.get();
            editingHotel.setName(hotel.getName());
            hotelRepository.save(editingHotel);
            isEdited = true;
        }
        return isEdited? "Successfully edited":"Hotel not found";
    }
    @DeleteMapping("7{id}")
    public String deleteHotelById(@PathVariable Integer id){
        boolean isDeleted = false;
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            hotelRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted?"Successfully deleted":"Hotel not found";
    }
}
