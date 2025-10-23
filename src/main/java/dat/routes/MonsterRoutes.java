package dat.routes;


import dat.controllers.impl.MonsterController;
import dat.dtos.MonsterDTO;
import dat.security.enums.Role;
import dat.services.MonsterServices;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MonsterRoutes {

    private final MonsterController monsterController = new MonsterController();

    protected EndpointGroup getRoutes() {
        return () -> {
            get("/populate", monsterController::populate);      // populate DB from JSON
            post("/", monsterController::create, Role.ADMIN);    // create new town (ADMIN role required)
            get("/", monsterController::readAll);               // get all towns
            get("/{id}", monsterController::read);              // get town by id
            put("/{id}", monsterController::update, Role.ADMIN);            // update town by id (ADMIN role required)
            delete("/{id}", monsterController::delete, Role.ADMIN);         // delete town by id (ADMIN role required)
        };
    }
}
