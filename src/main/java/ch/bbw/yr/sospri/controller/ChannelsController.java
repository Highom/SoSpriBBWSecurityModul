package ch.bbw.yr.sospri.controller;

import ch.bbw.yr.sospri.member.Member;
import ch.bbw.yr.sospri.member.MemberService;
import ch.bbw.yr.sospri.message.Message;
import ch.bbw.yr.sospri.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

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

		logger.info("called getRequestChannel() with chatroom " + chatroom);

		return  "channel";
	}

	@PostMapping("/add-message")
	public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult,  @AuthenticationPrincipal OAuth2User oAuth2User) {
		if(bindingResult.hasErrors()) {
			logger.error("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
			model.addAttribute("messages", messageservice.getAll());
			return "channel";
		}

		message.setChatroom(currentChatroom);
		message.setOrigin(new Date());

		try {
			String email = oAuth2User.getAttribute("email").toString();

			if (memberservice.existsByEmail(email)) {
				Member user = memberservice.getByEmail(email);
				message.setAuthor(user.getUsername());
				messageservice.add(message);
				logger.info("User "+ user.getUsername() + " called postRequestChannel() with message \"" + message.getContent() + "\" in chatroom " + currentChatroom);
				return "redirect:/get-channel?chatroom=" + currentChatroom;
			}
		} catch (NullPointerException ne) {
			logger.debug(ne.getMessage());
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			message.setAuthor(user.getUsername());
			messageservice.add(message);
			logger.info("User "+ user.getUsername() + " called postRequestChannel() with message \"" + message.getContent() + "\" in chatroom " + currentChatroom);
			return "redirect:/get-channel?chatroom=" + currentChatroom;
		}

		return "redirect:/get-channel?chatroom=" + currentChatroom;
	}
}
