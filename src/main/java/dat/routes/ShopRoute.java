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
            post("/", shopController::create);    // create new shop (USER role required)
            get("/", shopController::readAll);                // get all shops
            get("/{id}", shopController::read);               // get shop by id
            put("/{id}", shopController::update);             // update shop by id
            delete("/{id}", shopController::delete);          // delete shop by id
        };
    }
}
