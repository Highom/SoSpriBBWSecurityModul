/**
 * @Author: Yannick Ruck
 * @Date: 04/06/2021
 */
package ch.bbw.yr.sospri.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;

public class MemberToUserDetailsMapper {
    private static final Logger logger = LoggerFactory.getLogger(MemberToUserDetailsMapper.class);

    public static UserDetails toUserDetails(Member member) {
        User user = null;
        if (member != null) {
            logger.info("member: " + member);
            java.util.Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            logger.info("autorities: "
                    + Arrays.toString(authorities.toArray()));
            user = new User (member.getPrename ()+ " " + member.getLastname()
                    , member.getPassword()
                    , true
                    , true
                    , true
                    , true
                    , authorities // java.util.Collection<? extends GrantedAuthority> authorities)
);
        }
        return user;
    }
}
