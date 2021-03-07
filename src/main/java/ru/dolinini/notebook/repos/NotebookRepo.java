package ru.dolinini.notebook.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.dolinini.notebook.model.NotebookEntry;

import java.util.List;

public interface NotebookRepo extends CrudRepository<NotebookEntry,Long> {
    @Query(value = "SELECT * FROM notebookentry n INNER JOIN users u  ON u.id=n.user_id WHERE n.user_id=?1", nativeQuery = true)
    List<NotebookEntry> findAllByUserId(Long id);
}
