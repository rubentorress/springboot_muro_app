package sust.muro.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  public List<Comment> findByMessage(Message message);
}
