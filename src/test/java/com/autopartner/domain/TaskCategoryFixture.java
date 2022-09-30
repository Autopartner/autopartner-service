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

    public static TaskCategory createTaskCategoryWithDifferentName(){
        return TaskCategory.builder()
            .parentId(1L)
            .name("NewTaskCategory")
            .active(true)
            .build();
    }
}
