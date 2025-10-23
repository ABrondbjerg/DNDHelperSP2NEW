package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;

import dat.daos.impl.MonsterDAO;
import dat.dtos.MonsterDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MonsterController implements IController<MonsterDTO, Long> {

    private final MonsterDAO dao;

    public MonsterController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = MonsterDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();
        MonsterDTO monsterDTO = dao.read(id);
        ctx.status(200).json(monsterDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<MonsterDTO> monsters = dao.readAll();
        ctx.status(200).json(monsters);
    }

    @Override
    public void create(Context ctx) {
        MonsterDTO monsterDTO = ctx.bodyAsClass(MonsterDTO.class);
        MonsterDTO created = dao.create(monsterDTO);
        ctx.status(201).json(created);
    }

    @Override
    public void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();
        MonsterDTO updated = dao.update(id, validateEntity(ctx));
        ctx.status(200).json(updated);
    }

    @Override
    public void delete(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();
        dao.delete(id);
        ctx.status(202).json("{\"message\": \"Monster with ID " + id + " deleted successfully\"}");
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        return dao.validatePrimaryKey(id);
    }

    @Override
    public MonsterDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(MonsterDTO.class)
                .check(m -> m.getName() != null && !m.getName().isEmpty(), "Monster name must be set")
                .check(m -> m.getArmorClass() > 0, "Monster armorClass must be greater than 0")
                .check(m -> m.getHitPoints() > 0, "Monster hitPoints must be greater than 0")
                .check(m -> m.getSize() != null && !m.getSize().isEmpty(), "Monster size must be set")
                .get();
    }

    public void populate(Context ctx) {
        dao.populate();
        ctx.status(200).json("{\"message\":\"Monster database has been populated\"}");
    }
}
