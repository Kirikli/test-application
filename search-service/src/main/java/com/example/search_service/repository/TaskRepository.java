package com.example.search_service.repository;

import asyncapi.enums.TaskStatus;
import com.example.search_service.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Page<Task> findByNameContainingIgnoreCase(String nameFragment, Pageable pageable);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    @Modifying
    @Query("""
            update Task t
               set t.executor = (select u from User u where u.id = :executorId)
             where t.id = :taskId
            """)
    void assignExecutor(UUID taskId, UUID executorId);

    @Modifying
    @Query("""
            update Task t
            set t.status = :status
            where t.id = :taskId
            """)
    void completeTask(UUID taskId, TaskStatus status);
}
