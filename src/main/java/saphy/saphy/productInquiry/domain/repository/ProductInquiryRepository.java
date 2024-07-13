package saphy.saphy.productInquiry.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.productInquiry.domain.ProductInquiry;

public interface ProductInquiryRepository extends JpaRepository<ProductInquiry, Long> {

}
