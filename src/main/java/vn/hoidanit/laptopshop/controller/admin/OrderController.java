package vn.hoidanit.laptopshop.controller.admin;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/admin/order")
  public String getDashboard(Model model) {
    List<Order> orders = this.orderService.fetchAllOrders();
    model.addAttribute("orders", orders);
    return "admin/order/show";
  }

  @GetMapping("/admin/order/{id}")
  public String getOrderDetailPage(Model model, @PathVariable long id) {
    Order order = this.orderService.fetchOrderById(id).get();
    model.addAttribute("order", order);
    model.addAttribute("id", id);
    model.addAttribute("orderDetails", order.getOrderDetails());
    return "admin/order/detail";
  }

  @GetMapping("/admin/order/update/{id}") //GET
  public String getUpdateOrderPage(Model model, @PathVariable long id) {
    Optional<Order> currentOrder = this.orderService.fetchOrderById(id);
    model.addAttribute("newOrder", currentOrder.get());
    return "admin/order/update";
  }

  @PostMapping("/admin/order/update")
  public String handleUpdateUserPage(@ModelAttribute("newOrder") Order order) {
    this.orderService.updateOrder(order);
    return "redirect:/admin/order";
  }

  @GetMapping("/admin/order/delete/{id}") //GET
  public String getDeleteOrderPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    model.addAttribute("newOrder", new Order()/*,user*/);
    return "admin/order/delete";
  }

  @PostMapping("/admin/user/delete")
  public String postDeleteUserPage(@ModelAttribute("newOrder") Order order) {
    this.orderService.deleteOrderById(order.getId());
    return "redirect:/admin/order";
  }
}
