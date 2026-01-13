package Jar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskViewController {

    private final TaskRepository repo;

    public TaskViewController(TaskRepository repo) {
        this.repo = repo;
    }

    // 一覧 + 追加フォーム
    @GetMapping("/ui/tasks")
    public String list(Model model) {
        model.addAttribute("tasks", repo.findAll());
        return "tasks"; // templates/tasks.html
    }

    // 追加（フォームから）
    @PostMapping("/ui/tasks")
    public String create(@RequestParam String title) {
        if (title == null || title.isBlank()) {
            return "redirect:/ui/tasks";
        }
        Task t = new Task();
        t.setTitle(title);
        t.setStatus("TODO"); // 追加
        repo.save(t);
        return "redirect:/ui/tasks";
    }


    // 削除（ボタンから）
    @PostMapping("/ui/tasks/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/tasks";
    }

    // 編集画面へ
    @GetMapping("/ui/tasks/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Task t = repo.findById(id).orElseThrow();
        model.addAttribute("task", t);
        return "edit"; // templates/edit.html
    }

    // 更新（編集フォームから）
    @PostMapping("/ui/tasks/{id}")
    public String update(@PathVariable Long id, @RequestParam String title) {
        Task t = repo.findById(id).orElseThrow();
        if (title != null && !title.isBlank()) {
            t.setTitle(title);
            repo.save(t);
        }
        return "redirect:/ui/tasks";
    }
    @PostMapping("/ui/tasks/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        // 変な値を弾く（最低限）
        if (!("TODO".equals(status) || "DOING".equals(status) || "DONE".equals(status))) {
            return "redirect:/ui/tasks";
        }

        Task t = repo.findById(id).orElseThrow();
        t.setStatus(status);
        repo.save(t);
        return "redirect:/ui/tasks";
    }

}
