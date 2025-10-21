package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.TownDAO;
import dat.dtos.TownDTO;
import dat.entities.Town;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TownController implements IController<TownDTO, Long> {

    private final TownDAO dao;

    public TownController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TownDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        TownDTO townDTO = dao.read(id);
        ctx.status(200).json(townDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<TownDTO> townDTOS = dao.readAll();
        ctx.status(200).json(townDTOS);
    }

    @Override
    public void create(Context ctx) {
        TownDTO townDTO = ctx.bodyAsClass(TownDTO.class);
        TownDTO created = dao.create(townDTO);
        ctx.status(201).json(created);
    }

    @Override
    public void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        TownDTO updated = dao.update(id, validateEntity(ctx));
        ctx.status(200).json(updated);
    }

    @Override
    public void delete(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.status(204);
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        return dao.validatePrimaryKey(id);
    }

    @Override
    public TownDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TownDTO.class)
                .check(t -> t.getName() != null && !t.getName().isEmpty(), "Town name must be set")
                .check(t -> t.getDescription() != null && !t.getDescription().isEmpty(), "Town description must be set")
                .check(t -> t.getFeatures() != null, "Town features must be set")
                .get();
    }

    public void populate(Context ctx) {
        dao.populate();
        ctx.status(200).json("{\"message\":\"Town database has been populated\"}");
    }
}
