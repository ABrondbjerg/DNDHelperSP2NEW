package dat.routes;

import dat.controllers.impl.MonsterController;
import dat.dtos.MonsterDTO;
import dat.security.enums.Role;
import dat.services.MonsterServices;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MonsterRoutes {

    private final MonsterController controller = new MonsterController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", controller::create, Role.USER);
            get("/", controller::readAll);

            // Ekstern API
            get("/external/{name}", ctx -> {
                String name = ctx.pathParam("name");
                MonsterDTO dto = MonsterServices.fetchMonsterByName(name);

                if (dto != null) {
                    ctx.json(dto);
                } else {
                    ctx.status(404).result("Monster not found: " + name);
                }
            });

            get("/{id}", controller::read);
            put("/{id}", controller::update);
            delete("/{id}", controller::delete);
        };
    }
}
