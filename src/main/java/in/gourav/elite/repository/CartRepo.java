package in.gourav.elite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.gourav.elite.entity.Cart;
import in.gourav.elite.response.CartResponse;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

	
	@Query("SELECT COUNT(c) > 0 FROM Cart c WHERE c.product.id = :productId AND c.user.id = :userId")
	boolean existsByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId);
	
	@Query("SELECT c FROM Cart c WHERE c.user.user_id = :id")
	List<Cart> getCartDetails(@Param("id") Integer id);

	List<Cart> findByCartIdIn(Integer[] cartIds);

}
 