package com.ssafy.cheertogether;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/")
	String hello() {
		return "index";
	}
}
