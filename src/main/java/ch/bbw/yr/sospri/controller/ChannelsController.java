package ch.bbw.yr.sospri.controller;

import java.util.Date;

import javax.validation.Valid;

import ch.bbw.yr.sospri.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ch.bbw.yr.sospri.member.MemberService;
import ch.bbw.yr.sospri.message.Message;

/**
 * ChannelsController
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class ChannelsController {
	private final Logger logger = LoggerFactory.getLogger(ChannelsController.class);

	@Autowired
    MessageService messageservice;
	@Autowired
	MemberService memberservice;

	private String currentChatroom = "";

	@GetMapping("/get-channel")
	public String getRequestChannel(Model model, @RequestParam("chatroom") String chatroom) {
		model.addAttribute("messages", messageservice.getByChatroom(chatroom));

		Message message = new Message();
		message.setContent("Hello World!");
		model.addAttribute("message", message);
		model.addAttribute("chatroom", chatroom);

		currentChatroom = chatroom;


		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("User "+ user.getUsername() + " called getRequestChannel() with chatroom " + chatroom);
		return "channel";
	}

	@PostMapping("/add-message")
	public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
			model.addAttribute("messages", messageservice.getAll());
			return "channel";
		}

		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		message.setAuthor(user.getUsername());
		message.setChatroom(currentChatroom);
		message.setOrigin(new Date());
		messageservice.add(message);

		logger.info("User "+ user.getUsername() + " called postRequestChannel() with message \"" + message.getContent() + "\" in chatroom " + currentChatroom);
		return "redirect:/get-channel?chatroom=" + currentChatroom;
	}
}
