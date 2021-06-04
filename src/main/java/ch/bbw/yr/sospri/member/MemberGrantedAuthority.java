/**
 * @Author: Yannick Ruck
 * @Date: 04/06/2021
 */
package ch.bbw.yr.sospri.member;

import org.springframework.security.core.GrantedAuthority;

public class MemberGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 8835903531623403993L;
    private String authority;

    public MemberGrantedAuthority(String authority) {
        super();
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "MemberGrantedAuthority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
