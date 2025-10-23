package dat.routes;

import dat.entities.Shop;
import dat.entities.Town;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final TownRoute townRoute = new TownRoute();
    private final ShopRoute shopRoute = new ShopRoute();
    private final MonsterRoutes monsterRoute = new MonsterRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/town", townRoute.getRoutes());
            path("/shop", shopRoute.getRoutes());
            path("/monsters", monsterRoute.getRoutes());

        };
    }
}
