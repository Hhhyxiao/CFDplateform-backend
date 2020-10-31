package hyx.plateform.dao;

import hyx.plateform.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer> {
    ProjectEntity findByName(String name);

    List<ProjectEntity> findAll();
}
