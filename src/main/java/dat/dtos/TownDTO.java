package dat.dtos;

import dat.entities.Town;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TownDTO {
    private Long id;
    private String name;
    private String description;
    private String features;
    private List<ShopDTO> shops;


    public TownDTO(Town town) {
        this.id = town.getId();
        this.name = town.getName();
        this.description = town.getDescription();
        this.features = town.getFeatures();
        if (town.getShops() != null) {
            this.shops = town.getShops().stream()
                    .map(ShopDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public TownDTO(String name, String description, String features, List<ShopDTO> shops) {
        this.name = name;
        this.description = description;
        this.features = features;
        this.shops = shops;
    }
}
