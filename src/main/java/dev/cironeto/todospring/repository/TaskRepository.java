package dev.cironeto.todospring.repository;

import dev.cironeto.todospring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
