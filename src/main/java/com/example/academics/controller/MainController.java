package com.example.academics.controller;


import com.example.academics.model.Cart;
import com.example.academics.model.Course;
import com.example.academics.model.Role;
import com.example.academics.model.Users;
import com.example.academics.model.repo.CartRepository;
import com.example.academics.model.repo.CourseRepository;
import com.example.academics.model.repo.UserRepository;
import com.example.academics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainController {

    Users user = new Users();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

//    Boolean fromLogin = false;
//
//    Authentication auth = null;


    @GetMapping(
            produces = MediaType.IMAGE_JPEG_VALUE,
            path = "course-img/{courseId}")
    @ResponseBody
    public byte[] getImage(@PathVariable("courseId") Long blogId, HttpServletResponse response) {
        return courseRepository.findById(blogId).orElseThrow().getPhoto();
    }

    @GetMapping("/")
    public String index() {
//        auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth.getAuthorities());
//        if (!auth.getAuthorities().toString().toLowerCase().contains("anonymous")){
//            if (!fromLogin){
//                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
//                String UserText = oAuth2User.getAttributes().toString();
//                System.out.println(UserText);
//                int emailInt = UserText.lastIndexOf("email"),
//                        family_nameInt = UserText.indexOf("family_name"),
//                        given_nameInt = UserText.indexOf("given_name"),
//                        name = UserText.indexOf("name"),
//                        iatInt = UserText.indexOf(", iat"),
//                        localeInt = UserText.indexOf(", locale=");
//                String userEmail = UserText.substring(emailInt + 6, UserText.length() - 1);
//                String userLastname = UserText.substring(family_nameInt + 12, iatInt);
//                String userFirstname = UserText.substring(given_nameInt + 11, localeInt);
//                System.out.println(userEmail);
//                System.out.println(userLastname);
//                System.out.println(userFirstname);
//
//                user.setUsername(userFirstname);
//                user.setFirstName(userFirstname);
//                user.setLastName(userLastname);
//                user.setPassword(userFirstname);
//                Role role_user = new Role("ROLE_USER");
//                user.setRoles(Set.of(role_user));
//                userService.saveUser(user);
//
//
//            }
//        }
        return "index";
    }

    @GetMapping("books")
    public String home(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses";
    }


    @GetMapping("users")
    public String user(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }


    @GetMapping("books/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow();
        model.addAttribute(course);

        return "courses";
    }

    @GetMapping("addBook")
    public String createPost(Model model) {
        Course newCourse = new Course().setCreatedDate(LocalDateTime.now());
        model.addAttribute("newCourse", newCourse);
        return "addBook";
    }

    @GetMapping("addToCartCourse/{id}")
    public String addToCart(@PathVariable long id){
        Cart cart = new Cart();
        Course course= courseRepository.findById(id).orElseThrow();
        System.out.println(course.getTitle() + " " + course.getPrice());
        cart.setName(course.getTitle());cart.setPrice(course.getPrice());
        cartRepository.save(cart);
        return "redirect:/books";
    }
    @PostMapping("order")
    public String order(@RequestParam("cart_name") String cart_name,@RequestParam("cart_price") String cart_price,
                        @RequestParam("city") String city, @RequestParam("address") String address,
                        @RequestParam("email") String email, @RequestParam("tel") String tel,
                        @RequestParam("message") String message){
        System.out.println(cart_name + cart_price + city + email + address + tel + message);
        return "redirect:/books";
    }

    @GetMapping("editCourse/{id}")
    public String editPost(Model model, @PathVariable long id) {
        Course newCourse = courseRepository.findById(id).orElseThrow();
        model.addAttribute("newCourse", newCourse);
        return "editCourse";
    }

    @GetMapping("deleteCourse/{id}")
    public String deletePost(@PathVariable long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }

    @PostMapping(value = "savecourse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String savePost(
            @ModelAttribute Course course,
            @RequestPart("photofile") MultipartFile photo,
            Principal principal
    ) {
        courseRepository.save(course);
        try {
            course.setPhoto(photo.getBytes());
            courseRepository.save(course);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/courses";
    }

    @GetMapping("adduser")
    public String createUser(Model model) {
        Users newUsers = new Users();
        model.addAttribute("newUser", newUsers);
        return "adduser";
    }

    @PostMapping(value = "saveuser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveUser(
            @ModelAttribute Users users,
            Principal principal
    ) {
        Role role_user = new Role("ROLE_USER");
        users.setRoles(Set.of(role_user));
        userService.saveUser(users);
        return "redirect:/";
    }

    @GetMapping("admin")
    @ResponseBody
    public String adminPanel() {
        return "admin";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
//    @PostMapping("login")
//    public String postLogin(@RequestParam("username") String username, @RequestParam("password") String password){
//        System.out.println(username + " " + password);
//        return "redirect:/";
//    }

    @GetMapping("register")
    public String register(Model model) {
        Users newUsers = new Users();
        model.addAttribute("newUser", newUsers);
        return "register";
    }


    @GetMapping("cart")
    public String contact(Model model) {
        model.addAttribute("carts",cartRepository.findAll());
        return "contact";
    }

    @GetMapping("aboutus")
    public String admissions() {
        return "admissions";
    }

}
