package ch.bbw.yr.sospri.controller;

import ch.bbw.yr.sospri.member.Member;
import ch.bbw.yr.sospri.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.yr.sospri.member.MemberService;
import ch.bbw.yr.sospri.member.RegisterMember;

import javax.validation.Valid;
import java.security.SecureRandom;

/**
 * RegisterController
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class RegisterController {
	@Autowired
	MemberService memberservice;

	private static final String PEPPER = "IAmAnEpicPepper";

	public static String  GetPep() {
		return PEPPER;
	}

	Message message = new Message();

	@GetMapping("/get-register")
	public String getRequestRegistMembers(Model model) {
		System.out.println("getRequestRegistMembers");
		model.addAttribute("registerMember", new RegisterMember());
		return "register";
	}
	
	@PostMapping("/get-register")
	public String postRequestRegistMembers(@Valid RegisterMember registerMember, BindingResult result, Model model) {
		System.out.println("postRequestRegistMembers: registerMember");
		System.out.println(registerMember);

		if (result.hasErrors()) {
			return "register";
		}

		String username = registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase();
		if (memberservice.getByUserName(username) != null) {
			String msg = "User " + username +" already exists. change the first or last name";
			System.out.println(msg);
			registerMember.setMessage(msg);
			return "register";
		}

		if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
			String msg = "Passwords do not match!";
			System.out.println(msg);
			registerMember.setMessage(msg);
			return "register";
		}

		Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(PEPPER, 185000, 256);

		Member member = new Member();
		member.setPrename(registerMember.getPrename());
		member.setLastname(registerMember.getLastname());
		member.setUsername(username);
		member.setPassword(encoder.encode(registerMember.getPassword()));
		member.setAuthority("member");

		memberservice.add(member);

		model.addAttribute("username",member.getUsername());
		return "registerconfirmed";
	}
}