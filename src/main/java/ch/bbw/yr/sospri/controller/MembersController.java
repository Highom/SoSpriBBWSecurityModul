package ch.bbw.yr.sospri.controller;

import ch.bbw.yr.sospri.member.Member;
import ch.bbw.yr.sospri.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * UsersController
 * @author Peter Rutschmann
 * @version 28.04.2020
 */
@Controller
public class MembersController {
	private final Logger logger = LoggerFactory.getLogger(MembersController.class);

	@Autowired
	MemberService memberservice;
	
	@GetMapping("/get-members")
	public String getRequestMembers(Model model) {

		logger.info("called getRequestMembers()");
		model.addAttribute("members", memberservice.getAll());
		return "members";
	}
	
	@GetMapping("/edit-member")
	public String editMember(@RequestParam(name="id", required = true) long id, Model model) {
		Member member = memberservice.getById(id);
		logger.info("called editMember() with Id " + id);
		model.addAttribute("member", member);
		return "editmember";
	}

	@PostMapping("/edit-member")
	public String editMember(Member member, Model model) {
		Member value = memberservice.getById(member.getId());
		value.setAuthority(member.getAuthority());
		logger.info("edited Member with Id " + member.getId());
		memberservice.update(member.getId(), value);
		return "redirect:/get-members";
	}

	@GetMapping("/delete-member")
	public String deleteMember(@RequestParam(name="id", required = true) long id, Model model) {
		logger.info("deleted Member with Id " +id);
		memberservice.deleteById(id);
		return "redirect:/get-members";
	}
}
