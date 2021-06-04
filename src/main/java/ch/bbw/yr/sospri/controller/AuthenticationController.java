/**
 * @Author: Yannick Ruck
 * @Date: 30/05/2021
 */
package ch.bbw.yr.sospri.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logOut")
    public String logOut() {
        return "logout";
    }
}