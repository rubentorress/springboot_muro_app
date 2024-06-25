package sust.muro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

import sust.muro.daos.UsersDao;
import sust.muro.models.*;

@Controller
public class UserController {

  @Autowired
  UserRepository userRepo;

  @Autowired
  UsersDao usersDao;

  @GetMapping("/login")
  public String loginForm() {
    return "login.html";
  }

  @GetMapping("/register")
  public String registerForm() {
    return "register.html";
  }

  @PostMapping("/register")
  public String register(@RequestParam String username, @RequestParam String name, @RequestParam String password,
      @RequestParam String pass_confirm, RedirectAttributes redAt, HttpSession session) {
    // 1. Validamos que las contraseñas coincidan
    if (!password.equals(pass_confirm)) {
      redAt.addFlashAttribute("mal", "Las contraseñas no coinciden");
      return "redirect:/register";
    }
    // 2. Creamos un nuevo usuario, si todo sale OK, redirigimos a pantalla
    // principal
    // en caso contrario, redirigimos al formulario
    boolean resultado = usersDao.create(name, username, password, redAt, session);
    if (resultado) {
      return "redirect:/";
    }
    return "redirect:/register";
  }

  @PostMapping("/login")
  public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redAt,
      HttpSession session) {
    User u = userRepo.findByUsername(username);
    if (u == null) {
      redAt.addFlashAttribute("mal", "Usuario inexistente o contraseña incorrecta");
      return "redirect:/login";
    }
    if (!u.getPassword().equals(password)) {
      redAt.addFlashAttribute("mal", "Usuario inexistente o contraseña incorrecta");
      return "redirect:/login";
    }
    session.setAttribute("user", u);
    session.setAttribute("user_session_id", u.getId());
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    // 1. Eliminar la sesión
    session.setAttribute("user", null);
    // 2. Redirigir a la pantalla de login
    return "redirect:/login";
  }

}
