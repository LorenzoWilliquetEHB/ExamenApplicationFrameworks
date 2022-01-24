package be.ehb.eindproject_lorenzo_williquet.controller;

import be.ehb.eindproject_lorenzo_williquet.model.DAO.CartItemDAO;
import be.ehb.eindproject_lorenzo_williquet.model.DAO.ProductDAO;
import be.ehb.eindproject_lorenzo_williquet.model.DAO.RoleDAO;
import be.ehb.eindproject_lorenzo_williquet.model.DAO.UserDAO;
import be.ehb.eindproject_lorenzo_williquet.model.entities.CartItem;
import be.ehb.eindproject_lorenzo_williquet.model.entities.Product;
import be.ehb.eindproject_lorenzo_williquet.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    UserDAO userDAO;
    RoleDAO roleDAO;
    ProductDAO productDAO;
    CartItemDAO cartDAO;

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AppController(UserDAO userDAO, RoleDAO roleDAO, ProductDAO productDAO, CartItemDAO cartItemDAO){
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.productDAO = productDAO;
        this.cartDAO = cartItemDAO;
    }

    // Geeft de homepagina terug
    @GetMapping("/index")
    public String viewHomePage(){
        return "index";
    }

    // Geeft de login pagina terug
    @RequestMapping("/login")
    public String viewLoginPage(Model model,User user) {
        // Voorkomen dat een ingelogde user zich opnieuw gaat inloggen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user",user);
            return "login";
        }

        return "redirect:/";
    }

    // Geeft de registreer pagina terug
    @GetMapping("/register")
    public String viewRegisterPage(Model model){
        // Voorkomen dat een ingelogde user zich opnieuw gaat registreren
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user", new User());
            return "register";
        }
        return "redirect:/";
    }
    // Als de user geregistreerd is dan gaat hij automatisch naar de login pagina gestuurd worden
    @PostMapping("/process_register")
    public String processRegistration(User user){
        // Er voor zorgen dat de geregistreerde user zijn password gencrypteerd wordt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.addRole(roleDAO.findByName("User"));
        userDAO.save(user);
        return "login";
    }

    // Geeft de CRUD pagina van de producten weer
    @GetMapping("/products_crud")
    public String viewProductsCrudPage(Model model){
        List<Product> listProducts = productDAO.findAll();
        model.addAttribute("listProducts",listProducts);
        return "products_crud";
    }

    // verwijderd het product uit de producten en toon dan de lijst van alle producten
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model){
      Product p = productDAO.findProductById(id);
      productDAO.delete(p);

      return "redirect:/products_crud";
    }

    // Geeft de edit pagina terug van een product
    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model){
        Product product = productDAO.findProductById(id);
        model.addAttribute("product", product);
        return "product_edit";
    }

    // Geeft de pagina terug om een product toe te voegen
    @GetMapping("/product_add")
    public String viewAddProductPage(Model model){
        model.addAttribute("product", new Product());
        return "product_add";
    }

    // Bewaard het product
    @PostMapping("/product/save")
    public String saveProduct(Product product){
        productDAO.save(product);
        return "redirect:/products_crud";
    }

    // Veranderd het product
    @PostMapping("/product/update/{id}")
    public String editProduct(@PathVariable int id, Product product){
        product.setId(id);
        productDAO.save(product);
        return "redirect:/products_crud";
    }

    // Voegt een product aan de winkelwagen
    @PostMapping("/addToCart/{id}")
    public String addItemToCart(@PathVariable int id, Product product,CartItem cartItem){
        String u = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            u = ((UserDetails) principal).getUsername();
        }
        User user = userDAO.findByEmail(u);
        product.setId(id);

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        cartDAO.save(cartItem);
        return "redirect:/";
    }

    // Orderen van producten
    @PostMapping("/order")
    public String orderProducts(){


        return "redirect:/";
    }

    // Geeft de pagina terug van alle producten
    @GetMapping("/products")
    public String viewProductsPage(Model model){
        List<Product> listProducts = productDAO.findAll();
        String u = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            u = ((UserDetails) principal).getUsername();
        }
        User user = userDAO.findByEmail(u);
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("user",user);
        return "products";
    }

    // Geeft de pagina terug van alle habitat producten
    @GetMapping("/products_habitat")
    public String viewProductsHabitatPage(Model model){
        List<Product> listProductsHabitat = productDAO.findProductByCategory("Habitat");
        String u = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            u = ((UserDetails) principal).getUsername();
        }
        User user = userDAO.findByEmail(u);
        model.addAttribute("listProductsHabitat",listProductsHabitat);
        model.addAttribute("user",user);
        return "products_habitat";
    }

    // Geeft de pagina terug van alle speelgoed producten
    @GetMapping("/products_speelgoed")
    public String viewProductsSpeelgoedPage(Model model){
        List<Product> listProductsSpeelgoed = productDAO.findProductByCategory("Speelgoed");
        String u = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            u = ((UserDetails) principal).getUsername();
        }
        User user = userDAO.findByEmail(u);
        model.addAttribute("listProductsSpeelgoed",listProductsSpeelgoed);
        model.addAttribute("user",user);
        return "products_speelgoed";
    }

    // Geeft de pagina terug van alle voeding producten
    @GetMapping("/products_voeding")
    public String viewProductsVoedingPage(Model model){
        List<Product> listProductsVoeding = productDAO.findProductByCategory("Voeding");
        String u = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            u = ((UserDetails) principal).getUsername();
        }
        User user = userDAO.findByEmail(u);
        model.addAttribute("listProductsVoeding",listProductsVoeding);
        model.addAttribute("user",user);
        return "products_voeding";
    }

    // Geeft de pagina terug van het winkelwagentje
    @GetMapping("/shopping_cart")
    public String showShoppingCart(Model model){
        String u = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
             u = ((UserDetails) principal).getUsername();
        }
        User user = userDAO.findByEmail(u);
        List<CartItem> listCartitems = cartDAO.findByUser(user.getId());
        double total = listCartitems.stream().mapToDouble(f -> f.getProduct().getPrice()).sum();
        model.addAttribute("listCartItems", listCartitems);
        model.addAttribute("total",total);
        return "shopping_cart";
    }
}
