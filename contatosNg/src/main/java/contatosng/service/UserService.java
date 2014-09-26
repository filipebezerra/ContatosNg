package contatosng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import contatosng.model.User;
import contatosng.repository.UserRepository;

/**
 * @author Filipe Bezerra
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
