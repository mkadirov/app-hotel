package pdp.apphotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room", uniqueConstraints = {@UniqueConstraint(columnNames = {"number","hotel_id" })})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer id;

    @Column(nullable = false, unique = true)
    private int number;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private double sizeOfRoom;

    @ManyToOne
    private Hotel hotel;
}
