package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ShopType shoptype;

    private String owner;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;

    public Shop(@JsonProperty("name") String name,
                @JsonProperty("shoptype") ShopType shoptype,
                @JsonProperty("owner") String owner) {
        this.name = name;
        this.shoptype = shoptype;
        this.owner = owner;
    }

    public enum ShopType {
        BLACKSMITH,
        ALCHEMIST,
        GENERAL_STORE,
        MAGIC_SHOP,
        ARMORER,
        WEAPONSMITH,
        TAILOR,
        JEWELER,
        HERBALIST,
        ENCHANTER,
        BOOKSELLER,
        BAKER,
        BUTCHER,
        INN,
        STABLE,
        FLETCHER,
        FISHMONGER,
        CARPENTER,
        POTTER,
        CURIO_SHOP
    }

    @Override
    public String toString() {
        return "Shop{id=" + id +
                ", name='" + name + '\'' +
                ", shoptype=" + shoptype +
                ", owner='" + owner + '\'' +
                '}';
    }
}
