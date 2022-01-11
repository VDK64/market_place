package marketplace.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Bid", uniqueConstraints = @UniqueConstraint(
        name = "bid_user_id_item_id_amount_key",
        columnNames = {
                "amount",
                "user_id",
                "item_id"
        }
))
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "time_stamp", nullable = false)
    private String timeStamp;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "bid_user_id_fkey"),
            nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id",
            foreignKey = @ForeignKey(name = "bid_item_id_fkey"),
            nullable = false)
    private Item item;

}
