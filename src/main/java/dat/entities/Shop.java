package dat.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private ShopType shoptype;
    private String owner;

    public Shop(String name, ShopType shoptype, String owner) {
        this.name = name;
        this.shoptype = shoptype;
        this.owner = owner;
    }

    public enum ShopType {
        BLACKSMITH,          // Smed - våben, rustninger, værktøj
        ALCHEMIST,           // Trylledrikke, eliksirer, ingredienser
        GENERAL_STORE,       // Diverse handelsvarer og basisudstyr
        MAGIC_SHOP,          // Tryllestave, runer, magiske artefakter
        ARMORER,             // Specialiseret i rustninger og skjolde
        WEAPONSMITH,         // Specialiseret i våben
        TAILOR,              // Tøj, kapper, uniformer
        JEWELER,             // Smykker, ædelsten, ringe
        HERBALIST,           // Urter, salver, naturmedicin
        ENCHANTER,           // Fortryllelse af genstande
        BOOKSELLER,          // Bøger, skrifter, trylleruller
        BAKER,               // Brød, kager, bagværk
        BUTCHER,             // Kød, spegepølser, tørret kød
        INN,                 // Værtshus – mad, drikke og overnatning
        STABLE,              // Heste, staldplads, udstyr til rejser
        FLETCHER,            // Buer, pile, armbrøste
        FISHMONGER,          // Fisk, skaldyr, saltet mad
        CARPENTER,           // Træarbejde, møbler, vogne
        POTTER,              // Lerkrukker, fade, service
        CURIO_SHOP           // Mystiske genstande, sjældne fund, nips
    }
}
