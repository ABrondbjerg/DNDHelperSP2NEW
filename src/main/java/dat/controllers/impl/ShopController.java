package dat.controllers.impl;

import dat.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.ShopDAO;
import dat.dtos.ShopDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ShopController implements IController<ShopDTO, Integer> {

    private final ShopDAO dao;

    public ShopController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = ShopDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        ShopDTO shopDTO = dao.read(id);
        ctx.status(200).json(shopDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<ShopDTO> shopDTOS = dao.readAll();
        ctx.status(200).json(shopDTOS);
    }

    @Override
    public void create(Context ctx) {
        ShopDTO shopDTO = ctx.bodyAsClass(ShopDTO.class);
        ShopDTO created = dao.create(shopDTO);
        ctx.status(201).json(created);
    }

    @Override
    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        ShopDTO updated = dao.update(id, validateEntity(ctx));
        ctx.status(202).json("{\"message\": \"Shop with ID " + id + " updated successfully\"}");
    }

    @Override
    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.status(202).json("{\"message\": \"Shop with ID " + id + " deleted successfully\"}");
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        return dao.validatePrimaryKey(id);
    }

    @Override
    public ShopDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(ShopDTO.class)
                .check(s -> s.getName() != null && !s.getName().isEmpty(), "Shop name must be set")
                .check(s -> s.getShoptype() != null, "Shop type must be set")
                .check(s -> s.getOwner() != null && !s.getOwner().isEmpty(), "Shop owner must be set")
                .get();
    }

    public void populate(Context ctx) {
        dao.populate();
        ctx.status(200).json("{\"message\":\"Shop database has been populated\"}");
    }
}
