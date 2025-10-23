package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.MonsterDAO;
import dat.dtos.MonsterDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MonsterController implements IController<MonsterDTO, Integer> {

    private final MonsterDAO dao;

    public MonsterController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = MonsterDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id").get();
        MonsterDTO dto = dao.read(id);
        ctx.res().setStatus(200);
        ctx.json(dto);
    }

    @Override
    public void readAll(Context ctx) {
        List<MonsterDTO> dtos = dao.readAll();
        ctx.res().setStatus(200);
        ctx.json(dtos);
    }

    @Override
    public void create(Context ctx) {
        MonsterDTO dto = ctx.bodyAsClass(MonsterDTO.class);
        MonsterDTO created = dao.create(dto);
        ctx.res().setStatus(201);
        ctx.json(created);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id").get();
        MonsterDTO dto = ctx.bodyAsClass(MonsterDTO.class);
        MonsterDTO updated = dao.update(id, dto);
        ctx.res().setStatus(200);
        ctx.json(updated);
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        return dao.validatePrimaryKey(id);
    }

    @Override
    public MonsterDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(MonsterDTO.class)
                .check(m -> m.getName() != null && !m.getName().isEmpty(), "Name must be set")
                .get();
    }
}
