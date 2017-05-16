package autopartner.service.impl;

import autopartner.domain.entity.Task;
import autopartner.repository.TaskRepository;
import autopartner.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<Task> getByActiveTrue() {
        return taskRepository.findByActiveTrue();
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}
