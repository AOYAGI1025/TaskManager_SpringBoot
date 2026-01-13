package Jar;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // 一覧
    @GetMapping("/tasks")
    public List<Task> tasks() {
        return repo.findAll();
    }

    // 追加
    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        String title = task.getTitle();
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }
        Task t = new Task();
        t.setTitle(title);
        return repo.save(t);
    }

    // 編集（title更新）
    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task body) {
        Task t = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"));

        String title = body.getTitle();
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }

        t.setTitle(title);
        return repo.save(t);
    }

    // 削除
    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found");
        }
        repo.deleteById(id);
    }
}
