package ch.bbw.yr.sospri.message;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * MessageRepository
 * 
 * @author Peter Rutschmann
 * @version 25.06.2020
 */
                                                        //Klasse, id-Typ
public interface MessageRepository extends CrudRepository<Message, Long>{
	//Da wir eine embedded database verwenden, braucht es keine Conecction Information.

    List<Message> findAllByChatroom(String chatroom);
}

