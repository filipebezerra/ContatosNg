package contatosng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import contatosng.model.Contact;
import contatosng.repository.ContactRepository;

/**
 * @author Filipe Bezerra
 *
 */
@Service
@Transactional
public class ContactService {
	
	@Autowired
	private ContactRepository repository;
	
	public ContactListVO findAll(int page, int maxResults) {
		Page<Contact> result = executeQueryFindAll(page, maxResults);
		
		if (shouldExecuteSameQueryInLastPage(page, result)) {
			int lastPage = result.getTotalPages() - 1;
			result = executeQueryFindAll(lastPage, maxResults);
		}
		
		return buildResult(result);
	}
	
	private ContactListVO buildResult(Page<Contact> result) {
		return new ContactListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
	}

	private Page<Contact> executeQueryFindAll(int page, int maxResults) {
		final PageRequest pageRequest = new PageRequest(page, maxResults);
		
		return repository.findAll(pageRequest);
	}
	
	private Sort sortByNameASC() {
		return new Sort(Sort.Direction.ASC, "name");
	}

	public void save(Contact contact) {
		repository.save(contact);
	}
	
	@Secured("ROLE_ADMIN")
	public void delete(Long contactId) {
		repository.delete(contactId);
	}
	
	@Transactional(readOnly = true)
	public ContactListVO findAllByNameLike(int page, int maxResults, String name) {
		Page<Contact> result = executeQueryFindByName(page, maxResults, name);
		
		if (shouldExecuteSameQueryInLastPage(page, result)) {
			int lastPage = result.getTotalPages() - 1;
			result = executeQueryFindByName(lastPage, maxResults, name);
		}
		
		return buildResult(result);
	}

	private Page<Contact> executeQueryFindByName(int page, int maxResults, String name) {
		final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());
		
		return repository.findByNameLike(pageRequest, "%".concat(name).concat("%"));
	}

	private boolean shouldExecuteSameQueryInLastPage(int page,
			Page<Contact> result) {
		return isUserAfterOrOnLastPage(page, result) && hasDataInDatabase(result);
	}

	private boolean hasDataInDatabase(Page<Contact> result) {
		return result.getTotalElements() > 0;
	}

	private boolean isUserAfterOrOnLastPage(int page, Page<Contact> result) {
		return page >= result.getTotalPages() -1;
	}
	
}
