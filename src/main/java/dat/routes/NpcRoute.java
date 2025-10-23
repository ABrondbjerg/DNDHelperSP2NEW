package dat.routes;

import dat.controllers.impl.NpcController;
import dat.controllers.impl.ShopController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class NpcRoute {

    private final NpcController npcController = new NpcController();

    protected EndpointGroup getRoutes() {
        return () -> {
            get("/populate", npcController::populate);       // populate DB from JSON
            post("/", npcController::create);    // create new NPC (USER role required)
            get("/", npcController::readAll);                // get all npcs
            get("/{id}", npcController::read);               // get NPC by id
            put("/{id}", npcController::update);             // update NPC by id
            delete("/{id}", npcController::delete);          // delete NPC by id
        };
    }
}
