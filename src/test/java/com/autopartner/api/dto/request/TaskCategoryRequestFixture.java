package com.autopartner.api.dto.request;

public class TaskCategoryRequestFixture {
    public static TaskCategoryRequest createTaskCategoryRequest(){
        return TaskCategoryRequest.builder()
                .name("TaskCategory")
                .parentId(1L)
                .build();
    }
}
