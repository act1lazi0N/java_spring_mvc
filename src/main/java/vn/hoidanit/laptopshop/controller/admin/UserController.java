package vn.hoidanit.laptopshop.controller.admin;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

  private final UserService userService;
  private final UploadService uploadService;
  private final PasswordEncoder passwordEncoder;

  public UserController(
    UserService userService,
    UploadService uploadService,
    PasswordEncoder passwordEncoder
  ) {
    this.userService = userService;
    this.uploadService = uploadService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/admin/user/create")
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
    List<User> users = this.userService.getAllUsers();
    model.addAttribute("users1", users);
    return "admin/user/show";
  }

  @GetMapping("/admin/user/{id}")
  public String getUserPage(Model model, @PathVariable long id) {
    User user = this.userService.getUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    return "admin/user/detail";
  }

  @GetMapping("/admin/user/update/{id}") //GET
  public String getUpdateUserPage(Model model, @PathVariable long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser", currentUser);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/create")
  public String createUserPage(
    Model model,
    @Valid @ModelAttribute("newUser") User actilazion,
    BindingResult bindingResult,
    @RequestParam("actilazionFile") MultipartFile file
  ) {
    List<FieldError> errors = bindingResult.getFieldErrors();
    for (FieldError error : errors) {
      System.out.println(
        ">>>>>>" + error.getObjectName() + "- " + error.getDefaultMessage()
      );
    }
    // this.userService.handleSaveUser(actilazion);
    String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
    String hashPassword = this.passwordEncoder.encode(actilazion.getPassword());

    actilazion.setAvatar(avatar);
    actilazion.setPassword(hashPassword);
    actilazion.setRole(
      this.userService.getRoleByName(actilazion.getRole().getName())
    );

    // save
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
