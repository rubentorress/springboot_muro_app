package sust.muro.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sust.muro.models.Comment;
import sust.muro.models.CommentRepository;
import sust.muro.models.Message;
import sust.muro.models.MessageRepository;
import sust.muro.models.User;
import sust.muro.models.UserRepository;

@Component
public class CommentsDao {

  @Autowired
  CommentRepository commentRepo;

  @Autowired
  MessageRepository messageRepo;

  @Autowired
  UserRepository userRepo;

  public void create(Long message_id, Long user_id, String content) {
    // 1. Creo un Comment vacío
    Comment c = new Comment();
    // 2. Me traigo el mensaje y el usuario de la base de datos
    User u = userRepo.findById(user_id).get();
    Message m = messageRepo.findById(message_id).get();
    // 3. Completo el nuevo comment
    c.setContent(content);
    c.setUser(u);
    c.setMessage(m);
    // 4. Guardo en la base de datos
    commentRepo.save(c);
  }

  public List<Comment> getCommentsFromMessage(Long message_id) {
    Message m = messageRepo.findById(message_id).get();
    List<Comment> comments = commentRepo.findByMessage(m);
    List<Comment> clean_comments = new ArrayList<Comment>();
    for (Comment com : comments) {
      Comment c = new Comment();
      c.setId(com.getId());
      c.setContent(com.getContent());
      c.setCreated(com.getCreated());
      c.setCreatorsName(com.getUser().getName());
      clean_comments.add(c);
    }
    return clean_comments;
  }
}
