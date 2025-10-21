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
            post("/", townController::create, Role.USER);    // create new town (USER role required)
            get("/", townController::readAll);               // get all towns
            get("/{id}", townController::read);              // get town by id
            put("/{id}", townController::update);            // update town by id
            delete("/{id}", townController::delete);         // delete town by id
        };
    }
}
