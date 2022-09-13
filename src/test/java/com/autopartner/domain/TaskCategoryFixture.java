package com.autopartner.domain;

public class TaskCategoryFixture {
    public static TaskCategory createTaskCategory(){
        return TaskCategory.builder()
                .id(2L)
                .parentId(1L)
                .name("TaskCategory")
                .active(true)
                .build();
    }
}
