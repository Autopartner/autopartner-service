package com.autopartner.service;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.repository.TaskCategoryRepository;
import com.autopartner.service.impl.TaskCategoryServiceImpl;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class TaskCategoryServiceTest {
  @Mock
  TaskCategoryRepository taskCategoryRepository;
  @InjectMocks
  TaskCategoryServiceImpl taskCategoryService;
  @Captor
  ArgumentCaptor<TaskCategory> taskCategoryArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> taskCategoryIdArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> companyIdArgumentCaptor;


  @Test
  void findAll() {
    long companyId = 1L;
    List<TaskCategory> taskCategories = List.of(TaskCategoryFixture.createTaskCategory());
    when(taskCategoryRepository.findAll(companyId)).thenReturn(taskCategories);
    List<TaskCategory> actualTaskCategories = taskCategoryService.findAll(companyId);
    assertThat(taskCategories).isEqualTo(actualTaskCategories);
  }

  @Test
  void create() {
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    taskCategoryService.create(request, 1L);
    verify(taskCategoryRepository).save(taskCategoryArgumentCaptor.capture());
    TaskCategory actualTaskCategory = taskCategoryArgumentCaptor.getValue();
    assertThatTaskCategoryMappedCorrectly(actualTaskCategory, request);
  }

  @Test
  void findById() {
    Long companyId = 1L;
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    when(taskCategoryRepository.findById(anyLong(), anyLong()))
        .thenReturn(Optional.ofNullable(taskCategory));
    taskCategoryService.findById(taskCategory.getId(), companyId);
    verify(taskCategoryRepository).findById(taskCategoryIdArgumentCaptor.capture(), companyIdArgumentCaptor.capture());
    Long currentCompanyId = companyIdArgumentCaptor.getValue();
    Long id = taskCategoryIdArgumentCaptor.getValue();
    assertThat(id).isEqualTo(taskCategory.getId());
    assertThat(currentCompanyId).isEqualTo(companyId);
  }

  @Test
  void update() {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    taskCategoryService.update(taskCategory, request);
    verify(taskCategoryRepository).save(taskCategoryArgumentCaptor.capture());
    TaskCategory actualTaskCategory = taskCategoryArgumentCaptor.getValue();
    assertThatTaskCategoryMappedCorrectly(actualTaskCategory, request);
  }

  @Test
  void delete() {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    taskCategoryService.delete(taskCategory);
    verify(taskCategoryRepository).save(taskCategoryArgumentCaptor.capture());
    TaskCategory actualTaskCategory = taskCategoryArgumentCaptor.getValue();
    assertThat(actualTaskCategory).isEqualTo(taskCategory);
    assertThat(actualTaskCategory.getActive()).isFalse();
  }

  private void assertThatTaskCategoryMappedCorrectly(TaskCategory actualTaskCategory, TaskCategoryRequest request) {
    assertThat(actualTaskCategory.getName()).isEqualTo((request.getName()));
    assertThat(actualTaskCategory.getParentId()).isEqualTo(request.getParentId());
  }
}
