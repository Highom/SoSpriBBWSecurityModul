package ch.bbw.yr.sospri.message;

import ch.bbw.yr.sospri.member.MemberToUserDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * MessageService
 * 
 * @author Peter Rutschmann
 * @version 25.06.2020
 */
@Service
public class MessageService {
	private final Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private MessageRepository repository;
	
	public Iterable<Message> getAll(){
		logger.info("returned all messages");
		return repository.findAll();
	}

	public void add(Message message) {
		logger.info("saved the message " + message);
		repository.save(message);
	}

	public void update(Long id, Message message) {
		logger.info("updated the message with id " + id + "to: " + message);
		//save geht auch für update.
		repository.save(message);
	}

	public void deleteById(Long id) {
		logger.info("deleted the message with id " + id);
		repository.deleteById(id);
	}

	public Iterable<Message> getByChatroom(String chatroom) {
		logger.info("returned all messages from chatroom " + chatroom);
		return repository.findAllByChatroom(chatroom);
	}
}
