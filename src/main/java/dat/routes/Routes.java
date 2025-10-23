package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final MonsterRoutes monsterRoute = new MonsterRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/monsters", monsterRoute.getRoutes());

        };
    }
}
