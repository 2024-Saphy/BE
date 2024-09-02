package saphy.saphy.contact.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.contact.domain.Contact;

public interface ProductInquiryRepository extends JpaRepository<Contact, Long> {

}
