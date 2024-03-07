package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/")
  public String getHomePage(Model model) {
    model.addAttribute("Kyle", "test");
    model.addAttribute("Actilazion", "from controller with model");
    return "hello";
  }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
  public String createUserPage(
    Model mode,
    @ModelAttribute("newUser") User actilazion
  ) {
    System.out.println("Run here" + actilazion);
    this.userService.handleSaveUser(actilazion);
    return "hello";
  }
}
