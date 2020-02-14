package autopartner.controller.rest;

import autopartner.domain.entity.Task;
import autopartner.service.TaskService;
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
public class TaskController {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskService taskService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/task"}, method = RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> getAll() {
        return ResponseEntity.ok(taskService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> get(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/task", method = RequestMethod.POST)
    public ResponseEntity<Task> save(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.saveTask(task));
    }
}