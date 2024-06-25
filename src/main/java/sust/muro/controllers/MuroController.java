package sust.muro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

import sust.muro.models.MessageRepository;
import sust.muro.daos.MessageDao;
import sust.muro.models.Message;

@Controller
public class MuroController {

  @Autowired
  MessageRepository messageRepo;

  @Autowired
  MessageDao messageDao;

  @GetMapping("/")
  public Object home(HttpSession session) {
    // 1. Revisamos que el usuario esté logueado para acceder a esta vista
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }
    // 2. Obtenemos todos los mensajes
    List<Message> messages = messageDao.findAll();

    // 3. Le pasamos los mensajes a la vista
    ModelAndView vista = new ModelAndView("muro.html");
    vista.addObject("messages", messages);
    return vista;
  }

  @PostMapping("/messages")
  public String addMessage(@RequestParam String message, RedirectAttributes redAt, HttpSession session) {

    messageDao.create(message, session);

    redAt.addFlashAttribute("bien", "Mensaje Agregado con éxito");

    return "redirect:/";
  }

}