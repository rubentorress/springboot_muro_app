package sust.muro.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import sust.muro.models.Message;
import sust.muro.models.MessageRepository;
import sust.muro.models.User;

@Component
public class MessageDao {

  @Autowired
  MessageRepository messageRepo;

  public List<Message> findAll() {
    List<Message> messages = messageRepo.findAll();
    // Para poder pasar esta variable a los templates, debo primero limpiarlas
    for (Message m : messages) {
      m.setCreatorsName(m.getUser().getName());
      m.setUser(null);
    }
    return messages;
  }

  public void create(String message, HttpSession session) {
    User yo = (User) session.getAttribute("user");

    Message m = new Message();
    m.setMessage(message);
    m.setUser(yo);

    messageRepo.save(m);
  }
}
