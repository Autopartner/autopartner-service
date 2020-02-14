package autopartner.repository;

import autopartner.domain.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Iterable<Task> findByActiveTrue();
}