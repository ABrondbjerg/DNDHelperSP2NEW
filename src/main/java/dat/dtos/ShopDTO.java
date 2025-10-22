package dat.dtos;

import dat.entities.Shop;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class ShopDTO {
    private Integer id;
    private String name;
    private Shop.ShopType shoptype;
    private String owner;

    public ShopDTO() {}

    public ShopDTO(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.shoptype = shop.getShoptype();
        this.owner = shop.getOwner();
    }

    public ShopDTO(String name, Shop.ShopType shoptype, String owner) {
        this.name = name;
        this.shoptype = shoptype;
        this.owner = owner;
    }
}
