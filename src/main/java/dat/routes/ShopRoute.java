package dat.routes;

import dat.controllers.impl.ShopController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ShopRoute {

    private final ShopController shopController = new ShopController();

    protected EndpointGroup getRoutes() {
        return () -> {
            get("/populate", shopController::populate);       // populate DB from JSON
            post("/", shopController::create, Role.ADMIN);    // create new shop (ADMIN role required)
            get("/", shopController::readAll);                // get all shops
            get("/{id}", shopController::read);               // get shop by id
            put("/{id}", shopController::update, Role.ADMIN);             // update shop by id (ADMIN role required)
            delete("/{id}", shopController::delete, Role.ADMIN);          // delete shop by id (ADMIN role required)
        };
    }
}
