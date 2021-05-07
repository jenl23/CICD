package com.skcc.home.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.WebSession;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import com.skcc.account.domain.Account;
import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.service.AccountBankService;
import com.skcc.cart.domain.Cart;
import com.skcc.cart.service.CartService;
import com.skcc.product.domain.Product;
import com.skcc.product.service.ProductService;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final MeterRegistry meterRegistry;
	
	private ProductService productService;
	private CartService cartService;
	private AccountBankService accountBankService;
	@Value("${awesome.title}")
    private String title;
    
    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;
	
	@Autowired
	public HomeController(CartService cartService, ProductService productService, AccountBankService accountBankService, MeterRegistry meterRegistry) {
		this.productService = productService;
		this.cartService = cartService;
        this.accountBankService = accountBankService;
        this.meterRegistry = meterRegistry;
    }
	
	@GetMapping("/logout")
	public String logout(WebSession session, Model model) {
		// HttpSession session = request.getSession();
        log.debug("logout called");

		if (session.getAttribute("username") != null) {
			session.getAttributes().remove("id");
			session.getAttributes().remove("username");
			session.getAttributes().remove("name");
			session.getAttributes().remove("mobile");
			session.getAttributes().remove("address");
			session.getAttributes().remove("scope");
		}
		
		return "sign";
	}
	
	@GetMapping({"","/","/index","main"})
	public String index(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/index");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		List<Product> productsList = this.productService.getAllProducts();
		model.addAttribute(productsList);
        model.addAttribute("title", this.title);
        
        // Sample Metric
        Counter.builder("custom_call_main_page_total").tags("username", session.getAttribute("username"))
        .description("Total access user").register(meterRegistry).increment();
		
		return "index";
	}
	
	@GetMapping("/about")
	public String about(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/about");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		return "about";
	}
	
	@GetMapping("/blog")
	public String blog(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/blog");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		return "blog";
	}

	@GetMapping("/blog-detail")
	public String blogDetail(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/blog-detail");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		return "blog-detail";
	}
	
	@GetMapping("/contact")
	public String contact(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/contact");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		return "contact";
	}
	
	@GetMapping("/shoping-cart")
	public String shopingCart(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/shoping-cart");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		Account account = new Account();
		account.setId((long) session.getAttribute("id"));
		account.setUsername((String) session.getAttribute("username"));
		account.setName((String) session.getAttribute("name"));
		account.setMobile((String) session.getAttribute("mobile"));
		account.setScope((String) session.getAttribute("scope"));
		account.setAddress((String) session.getAttribute("address"));
		model.addAttribute(account);
		
		long accountId = (long) session.getAttribute("id");
        // List<Cart> cartList = this.cartService.getCartsByAccountId(accountId);
        List<Cart> cartList = circuitBreakerFactory.create("cartService").run(cartService.getCartsByAccountIdSupplier(accountId), throwable -> {
            log.error("CartService is Unavailable");
            return new ArrayList<Cart>();
        });
		model.addAttribute(cartList);
		
		AccountBank accountBank = this.accountBankService.findAccountBankByAccountId((long) session.getAttribute("id"));
		model.addAttribute(accountBank);
		
		return "shoping-cart";
	}
	
}
