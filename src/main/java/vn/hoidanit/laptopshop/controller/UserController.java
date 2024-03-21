package vn.hoidanit.laptopshop.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public String getHomePage(Model model) {
    List<User> arrayUsers =
      this.userService.getAllUsersByEmail("abc@gmail.com");
    System.out.println(arrayUsers);
    model.addAttribute("Kyle", "test");
    model.addAttribute("Actilazion", "from controller with model");
    return "hello";
  }

  @GetMapping("/admin/user/create") //GET
  public String getCreateUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @PostMapping("/admin/user/update")
  public String postUpdateUserPage(
    Model model,
    @ModelAttribute("newUser") User actilazion
  ) {
    User currentUser = this.userService.getUserById(actilazion.getId());
    if (currentUser != null) {
      currentUser.setAddress(actilazion.getAddress());
      currentUser.setFullName(actilazion.getFullName());
      currentUser.setPhone(actilazion.getPhone());
      this.userService.handleSaveUser(currentUser);
    }
    return "redirect:/admin/user";
  }

  @GetMapping("/admin/user")
  public String getUserPage(Model model) {
    model.addAttribute("newUser", new User());
    List<User> users = this.userService.getAllUsers();
    model.addAttribute("users1", users);
    return "admin/user/table-user";
  }

  @GetMapping("/admin/user/{id}")
  public String getUserPage(Model model, @PathVariable long id) {
    User user = this.userService.getUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    return "admin/user/show";
  }

  @GetMapping("/admin/user/update/{id}") //GET
  public String getUpdateUserPage(Model model, @PathVariable long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser", currentUser);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/create")
  public String createUserPage(
    Model mode,
    @ModelAttribute("newUser") User actilazion
  ) {
    this.userService.handleSaveUser(actilazion);
    return "redirect:/admin/user";
  }

  @GetMapping("/admin/user/delete/{id}") //GET
  public String getDeleteUserPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    // User user = new User();
    // user.getId();
    model.addAttribute("newUser", new User()/*,user*/);
    return "admin/user/delete";
  }

  @PostMapping("/admin/user/delete")
  public String postDeleteUserPage(
    Model model,
    @ModelAttribute("newUser") User actilazion
  ) {
    this.userService.deleteAllUser(actilazion.getId());
    return "redirect:/admin/user";
  }
}
