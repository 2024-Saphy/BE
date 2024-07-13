package saphy.saphy.cart.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.cart.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
