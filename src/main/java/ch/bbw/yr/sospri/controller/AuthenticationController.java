/**
 * @Author: Yannick Ruck
 * @Date: 30/05/2021
 */
package ch.bbw.yr.sospri.controller;


import ch.bbw.yr.sospri.member.Member;
import ch.bbw.yr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthenticationController {

    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/login")
    public String login(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "login";
    }

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {

        String email = oAuth2User.getAttribute("email").toString();
        String name = oAuth2User.getAttribute("name").toString();

        if (!memberService.existsByEmail(email)) {
            Member member = new Member();
            member.setUsername("[OAUTH] "+ name);
            member.setPrename(oAuth2User.getAttribute("given_name").toString());
            if(oAuth2User.getAttribute("family_name") != null){
                member.setLastname(oAuth2User.getAttribute("family_name").toString());
            }else {
                member.setLastname(oAuth2User.getAttribute("given_name").toString());
            }
            member.setAuthority("member");
            member.setEmail(email);
            memberService.add(member);
        }

        model.addAttribute("name", name);
        return "redirect:/";
    }

    @RequestMapping("/logOut")
    public String logOut() {
        return "logout";
    }
}