package com.autopartner.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.api.dto.request.TaskRequestFixture;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.domain.TaskFixture;
import com.autopartner.repository.TaskRepository;
import com.autopartner.service.impl.TaskServiceImpl;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class TaskServiceTest {

  @Mock
  TaskRepository repository;

  @InjectMocks
  TaskServiceImpl service;

  @Captor
  ArgumentCaptor<Task> taskArgumentCaptor;

  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;

  @Test
  void findAll() {
    List<Task> tasks = List.of(TaskFixture.createTask(), TaskFixture.createTask());
    when(repository.findByActiveTrue()).thenReturn(tasks);
    List<Task> actualTasks = service.findAll();
    assertThat(actualTasks.size()).isEqualTo(tasks.size());
    assertThat(actualTasks).isEqualTo(tasks);
  }

  @Test
  void findById() {
    Task task = TaskFixture.createTask();
    when(repository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(task));
    service.findById(Objects.requireNonNull(task).getId());
    verify(repository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    Long id = longArgumentCaptor.getValue();
    assertThat(task.getId()).isEqualTo(id);
  }

  @Test
  void create() {
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    service.create(request, category, 1L);
    verify(repository).save(taskArgumentCaptor.capture());
    Task actualTask = taskArgumentCaptor.getValue();
    assertThatCarMappedCorrectly(actualTask, request);
  }

  @Test
  void update() {
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    Task task = TaskFixture.createTask();
    service.update(task, category, request);
    verify(repository).save(taskArgumentCaptor.capture());
    Task actualTask = taskArgumentCaptor.getValue();
    assertThatCarMappedCorrectly(actualTask, request);
  }

  @Test
  void delete() {
    Task task = TaskFixture.createTask();
    service.delete(task);
    verify(repository).save(taskArgumentCaptor.capture());
    Task actualTask = taskArgumentCaptor.getValue();
    assertThat(actualTask).isEqualTo(task);
    assertThat(actualTask.getActive()).isFalse();
  }

  @Test
  void findByCategoryId() {
    Task task = TaskFixture.createTask();
    when(repository.findByTaskCategoryIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(task));
    service.findByCategoryId(Objects.requireNonNull(task).getTaskCategory().getId());
    verify(repository).findByTaskCategoryIdAndActiveTrue(longArgumentCaptor.capture());
    Long id = longArgumentCaptor.getValue();
    assertThat(task.getTaskCategory().getId()).isEqualTo(id);
  }

  private void assertThatCarMappedCorrectly(Task actualTask, TaskRequest request) {
    assertThat(actualTask.getName()).isEqualTo(request.getName());
    assertThat(actualTask.getTaskCategory().getId()).isEqualTo(request.getTaskCategoryId());
    assertThat(actualTask.getPrice()).isEqualTo(request.getPrice());
  }

}
