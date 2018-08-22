package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;

/**
 * Basic DAO interface which describes all required, CRUD,
 * operations for all entities.
 *
 * @author Illia Derhun
 * @version 1.0
 */
public interface Dao<Entity, Integer> {

    boolean create(Entity entity);
    Entity read(Integer entityId) throws IdInvalidExcepiton;
    boolean update(Entity entity);
    boolean delete(Integer entityId);

}
