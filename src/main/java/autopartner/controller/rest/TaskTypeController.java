package autopartner.controller.rest;

import autopartner.domain.entity.TaskType;
import autopartner.service.TaskTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class TaskTypeController {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskTypeService taskTypeService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/task/type"}, method = RequestMethod.GET)
    public ResponseEntity<Iterable<TaskType>> getAll() {
        return ResponseEntity.ok(taskTypeService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/task/type/{id}", method = RequestMethod.GET)
    public ResponseEntity<TaskType> get(@PathVariable Integer id) {
        return ResponseEntity.ok(taskTypeService.getTaskTypeById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/task/type", method = RequestMethod.POST)
    public ResponseEntity<TaskType> save(@Valid @RequestBody TaskType taskType) {
        return ResponseEntity.ok(taskTypeService.saveTaskType(taskType));
    }
}