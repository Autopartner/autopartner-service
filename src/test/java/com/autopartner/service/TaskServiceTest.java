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
  ArgumentCaptor<Long> taskIdCaptor;

  @Captor
  ArgumentCaptor<Long> companyIdCaptor;

  @Captor
  ArgumentCaptor<String> nameCaptor;

  @Test
  void findAll() {
    Long companyId = 1L;
    List<Task> tasks = List.of(TaskFixture.createTask(), TaskFixture.createTask());
    when(repository.findAll(companyId)).thenReturn(tasks);
    List<Task> actualTasks = service.findAll(companyId);
    assertThat(actualTasks.size()).isEqualTo(tasks.size());
    assertThat(actualTasks).isEqualTo(tasks);
  }

  @Test
  void findById() {
    Long companyId = 1L;
    Task task = TaskFixture.createTask();
    when(repository.findById(anyLong(), anyLong())).thenReturn(Optional.ofNullable(task));
    service.findById(task.getId(), companyId);
    verify(repository).findById(taskIdCaptor.capture(), companyIdCaptor.capture());
    Long id = taskIdCaptor.getValue();
    Long currentCompanyId = companyIdCaptor.getValue();
    assertThat(task.getId()).isEqualTo(id);
    assertThat(currentCompanyId).isEqualTo(companyId);
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
  void findAllByCategory() {
    Long companyId = 1L;
    Task task = TaskFixture.createTask();
    Long categoryId = Objects.requireNonNull(task).getCategory().getId();
    List<Task> tasks = List.of(task, TaskFixture.createTask());
    when(repository.findAllByCategoryId(eq(categoryId), eq(companyId)))
        .thenReturn(tasks);

    List<Task> actualTasks = service.findAllByCategory(categoryId, companyId);

    verify(repository).findAllByCategoryId(taskIdCaptor.capture(), companyIdCaptor.capture());
    assertThat(tasks.size()).isEqualTo(actualTasks.size());
    assertThat(tasks.get(1).getCategory().getId()).isEqualTo(actualTasks.get(1).getCategory().getId());
  }
  @Test
  void findByCategoryIdAndName() {
    Long companyId = 1L;
    Task task = TaskFixture.createTask();
    Long categoryId = Objects.requireNonNull(task).getCategory().getId();
    String name = task.getName();
    when(repository.findByCategoryIdAndNameAndActiveTrue(eq(name), eq(categoryId), eq(companyId)))
        .thenReturn(Optional.of(task));

    service.findByCategoryIdAndName(name, categoryId, companyId);

    verify(repository).findByCategoryIdAndNameAndActiveTrue(nameCaptor.capture(), taskIdCaptor.capture(), companyIdCaptor.capture());
    assertThat(task.getCategory().getId()).isEqualTo(taskIdCaptor.getValue());
    assertThat(task.getName()).isEqualTo(nameCaptor.getValue());
    assertThat(companyId).isEqualTo(companyIdCaptor.getValue());
  }

  private void assertThatTaskMappedCorrectly(Task actualTask, TaskRequest request) {
    assertThat(actualTask.getName()).isEqualTo(request.getName());
    assertThat(actualTask.getCategory().getId()).isEqualTo(request.getCategoryId());
    assertThat(actualTask.getPrice()).isEqualTo(request.getPrice());
  }

}
