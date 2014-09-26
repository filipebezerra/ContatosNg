package contatosng.repository;

import org.springframework.data.repository.CrudRepository;

import contatosng.model.User;

/**
 * @author Filipe Bezerra
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

}
