package dat.routes;

;
import dat.controllers.impl.TownController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TownRoute {

    private final TownController townController = new TownController();

    protected EndpointGroup getRoutes() {
        return () -> {
            get("/populate", townController::populate);      // populate DB from JSON
            post("/", townController::create, Role.ADMIN);    // create new town (ADMIN role required)
            get("/", townController::readAll);               // get all towns
            get("/{id}", townController::read);              // get town by id
            put("/{id}", townController::update, Role.ADMIN);            // update town by id (ADMIN role required)
            delete("/{id}", townController::delete, Role.ADMIN);         // delete town by id (ADMIN role required)
        };
    }
}
