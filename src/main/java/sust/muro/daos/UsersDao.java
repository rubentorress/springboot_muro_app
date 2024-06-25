package sust.muro.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import sust.muro.models.User;
import sust.muro.models.UserRepository;

@Component
public class UsersDao {

  @Autowired
  UserRepository userRepo;

  public boolean create(String name, String username, String password, RedirectAttributes redAt, HttpSession session) {
    // 2. Creamos un nuevo usuario
    User u = new User();
    u.setName(name);
    u.setPassword(password);
    u.setUsername(username);

    // 3. Lo guardamos en base de datos
    try {
      userRepo.save(u);
    } catch (Exception e) {
      redAt.addFlashAttribute("mal", "Ese nombre de usuario ya existe");
      return false;
    }
    // 4. Creamos la sesión
    session.setAttribute("user", u);

    // 5. Si todo sale OK, retorno un true
    return true;
  }
}
