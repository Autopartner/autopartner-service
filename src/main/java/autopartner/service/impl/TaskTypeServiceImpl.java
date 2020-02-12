package autopartner.service.impl;

import autopartner.domain.entity.TaskType;
import autopartner.repository.TaskTypeRepository;
import autopartner.service.TaskTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("TaskTypeService")
public class TaskTypeServiceImpl implements TaskTypeService {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskTypeRepository taskTypeRepository;

    @Override
    public Iterable<TaskType> getByActiveTrue() {
        return taskTypeRepository.findByActiveTrue();
    }

    @Override
    public TaskType getTaskTypeById(Integer id) {
        return taskTypeRepository.findById(id).get();
    }

    @Override
    public TaskType saveTaskType(TaskType taskType) {
        return taskTypeRepository.save(taskType);
    }
}
