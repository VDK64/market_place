package marketplace.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entity for database store. Represents Item.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "seller", nullable = false)
    private String seller;

    @Column(name = "start_price", nullable = false)
    private float startPrice;

    @Column(name = "bid_inc", nullable = false)
    private float bidInc;

    @Column(name = "best_offer", nullable = false)
    private float bestOffer;

    @Column(name = "bidder", nullable = false)
    private String bidder;

    @Column(name = "stop_date", nullable = false)
    private String stopDate;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "item_user_id_fkey"),
            nullable = false)
    private User user;

    @Column(name = "image")
    private byte[] image;

}
