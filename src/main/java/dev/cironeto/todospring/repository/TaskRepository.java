package dev.cironeto.todospring.repository;

import dev.cironeto.todospring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM task WHERE task_completed = 0 " +
            "AND user_id = :userId " +
            "AND priority = COALESCE(:priority, priority)"
    )
    List<Task> findUncompletedTasks(Long userId, Integer priority);
}
