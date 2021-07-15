package ch.bbw.yr.sospri.member;

import ch.bbw.yr.sospri.controller.ChannelsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * MemberService
 * 
 * @author Peter Rutschmann
 * @version 09.04.2020
 * 
 * https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
 * https://reflectoring.io/spring-security-password-handli
 */
@Service
@Transactional
public class MemberService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(MemberService.class);

	@Autowired
	private MemberRepository repository;

	public Iterable<Member> getAll(){
		logger.info("returned all members");
		return repository.findAll();
	}

	public void add(Member member) {
		logger.info("User was saved: " + member);
		repository.save(member);
	}

	public void update(Long id, Member member) {
		logger.info("User with id " + id + " was updated to: " + member);
		//save geht auch für update.
		repository.save(member);
	}

	public void deleteById(Long id) {
		logger.info("User with id " + id + " was deleted");
		repository.deleteById(id);
	}
	
	public Member getById(Long id) {
		Iterable<Member> memberitr = repository.findAll();
		
		for(Member member: memberitr){
			if (member.getId() == id) {
				logger.info("returned User with id " + id);
				return member;
			}
		}

		logger.info("id does not exist in repository: " + id);
		return null;
	}
	
	public Member getByUserName(String username) {
		Iterable<Member> memberitr = repository.findAll();
		
		for(Member member: memberitr){
			if (member.getUsername().equals(username)) {
				logger.info("returned User with username " + username);
				return member;
			}
		}
		logger.info("username does not exist in repository: " + username);
		return null;
	}

	public Member getByEmail(String email) {
		Iterable<Member> memberitr = repository.findAll();

		for(Member member: memberitr){
			if (member.getEmail().equals(email)) {
				logger.info("returned User with email " + email);
				return member;
			}
		}
		logger.info("email does not exist in repository: " + email);
		return null;
	}

	public Boolean existsByEmail(String email) {
		Iterable<Member> memberitr = repository.findAll();

		for(Member member: memberitr){
			if (member.getEmail().equals(email)) {
				logger.info("User with email exists " + email);
				return true;
			}
		}
		logger.info("email does not exist in repository: " + email);
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = getByUserName(username);
		return MemberToUserDetailsMapper.toUserDetails(member);
	}
}
