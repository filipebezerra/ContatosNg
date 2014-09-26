package contatosng.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import contatosng.model.Contact;

/**
 * @author Filipe Bezerra
 *
 */
public interface ContactRepository extends
		PagingAndSortingRepository<Contact, Long> {

	Page<Contact> findByNameLike(Pageable pageable, String name);

}
