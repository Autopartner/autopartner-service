package autopartner.repository;

import autopartner.domain.entity.TaskType;
import org.springframework.data.repository.CrudRepository;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long> {
    Iterable<TaskType> findByActiveTrue();
}