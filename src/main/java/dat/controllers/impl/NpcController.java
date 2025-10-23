package dat.controllers.impl;

import dat.controllers.IController;
import dat.daos.impl.NPCDAO;
import dat.dtos.NPCDTO;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import dat.config.HibernateConfig;

import java.util.List;

public class NpcController implements IController <NPCDTO,Integer>{

private final NPCDAO dao;

public NpcController() {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    this.dao = NPCDAO.getInstance(emf);
}

@Override
public void read(Context ctx) {
    Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
    NPCDTO npcDTO = dao.read(id);
    ctx.status(200).json(npcDTO);
}

@Override
public void readAll(Context ctx) {
    List<NPCDTO> NpcDTOS = dao.readAll();
    ctx.status(200).json(NpcDTOS);
}

@Override
public void create(Context ctx) {
    NPCDTO npcDTO = ctx.bodyAsClass(NPCDTO.class);
    NPCDTO created = dao.create(npcDTO);
    ctx.status(201).json(created);
}

@Override
public void update(Context ctx) {
    Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
    NPCDTO updated = dao.update(id, validateEntity(ctx));
    ctx.status(202).json("{\"message\": \"NPC with ID " + id + " deleted successfully\"}");
}

@Override
public void delete(Context ctx) {
    Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
    dao.delete(id);
    ctx.status(202).json("{\"message\": \"NPC with ID " + id + " deleted successfully\"}");
}

@Override
public boolean validatePrimaryKey(Integer id) {
    return dao.validatePrimaryKey(id);
}

@Override
public NPCDTO validateEntity(Context ctx) {
    return ctx.bodyValidator(NPCDTO.class)
            .check(n -> n.getName() != null && !n.getName().isEmpty(), "NPC name must be set")
            .check(n -> n.getDescription() != null, "NPC type must be set")
            .check(n -> n.getRace() != null, "NPC owner must be set")
            .check(n -> n.getProfessions() != null, "NPC professions must be set")
            .get();
}

public void populate(Context ctx) {
    dao.populate();
    ctx.status(200).json("{\"message\":\"NPC database has been populated\"}");
}
}
