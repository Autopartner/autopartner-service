package com.autopartner.service;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.api.dto.request.TaskRequestFixture;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.domain.TaskFixture;
import com.autopartner.repository.TaskRepository;
import com.autopartner.service.impl.TaskServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
  ArgumentCaptor<Long> idCaptor;

  @Captor
  ArgumentCaptor<String> nameCaptor;

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
    verify(repository).findByIdAndActiveTrue(idCaptor.capture());
    Long id = idCaptor.getValue();
    assertThat(task.getId()).isEqualTo(id);
  }

  @Test
  void create() {
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    service.create(request, category, 1L);
    verify(repository).save(taskArgumentCaptor.capture());
    Task actualTask = taskArgumentCaptor.getValue();
    assertThatTaskMappedCorrectly(actualTask, request);
  }

  @Test
  void update() {
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    Task task = TaskFixture.createTask();
    service.update(task, category, request);
    verify(repository).save(taskArgumentCaptor.capture());
    Task actualTask = taskArgumentCaptor.getValue();
    assertThatTaskMappedCorrectly(actualTask, request);
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
  void findByCategoryIdAndName() {
    Task task = TaskFixture.createTask();
    Long categoryId = Objects.requireNonNull(task).getTaskCategory().getId();
    String name = task.getName();
    when(repository.findByTaskCategoryIdAndNameAndActiveTrue(eq(categoryId), eq(name)))
        .thenReturn(Optional.of(task));

    service.findByCategoryIdAndName(categoryId, name);

    verify(repository).findByTaskCategoryIdAndNameAndActiveTrue(idCaptor.capture(), nameCaptor.capture());
    assertThat(task.getTaskCategory().getId()).isEqualTo(idCaptor.getValue());
    assertThat(task.getName()).isEqualTo(nameCaptor.getValue());
  }

  private void assertThatTaskMappedCorrectly(Task actualTask, TaskRequest request) {
    assertThat(actualTask.getName()).isEqualTo(request.getName());
    assertThat(actualTask.getTaskCategory().getId()).isEqualTo(request.getTaskCategoryId());
    assertThat(actualTask.getPrice()).isEqualTo(request.getPrice());
  }

}
