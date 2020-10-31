package hyx.plateform.dao;

import hyx.plateform.entity.CaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaseRepository extends CrudRepository<CaseEntity, Integer> {
    CaseEntity findByNameAndProject(String name, String project);

    List<CaseEntity> findByProject(String project);

}
