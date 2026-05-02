package com.example.test_application.repositories;

import com.example.test_application.model.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @EntityGraph(attributePaths = {"executor"})
    @Query("select t from Task t where t.id = :taskId")
    Task findTaskWithUser(UUID taskId);
}
