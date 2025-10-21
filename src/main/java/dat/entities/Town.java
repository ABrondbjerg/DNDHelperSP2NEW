package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String features;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "town_shops",
            joinColumns = @JoinColumn(name = "town_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id")
    )
    private List<Shop> shops = new ArrayList<>();

    public Town(String name, String description, String features) {
        this.name = name;
        this.description = description;
        this.features = features;
    }

    public Town(String name, String description, String features, List<Shop> allShops) {
        this.name = name;
        this.description = description;
        this.features = features;
        assignRandomShops(allShops);
    }

    /**
     * Randomly assigns 3 shops to this town.
     * Throws an error if there are fewer than 3 shops.
     */
    public void assignRandomShops(List<Shop> allShops) {
        if (allShops == null || allShops.size() < 3) {
            throw new IllegalArgumentException("Need at least 3 shops to assign to a town");
        }
        List<Shop> shuffled = new ArrayList<>(allShops);
        Collections.shuffle(shuffled);
        this.shops = new ArrayList<>(shuffled.subList(0, 3));
    }

    @Override
    public String toString() {
        return "Town{id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", features='" + features + '\'' +
                ", shops=" + shops.stream().map(Shop::getName).toList() +
                '}';
    }
}
