package in.gourav.elite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.gourav.elite.DTO.UserOrderDTO;
import in.gourav.elite.entity.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {

	@Query("SELECT new in.darshan.elite.DTO.UserOrderDTO(o.orderId,o.orderStatus) FROM Orders o WHERE o.user.user_id = :userId")
	List<UserOrderDTO> getOrderIdStatus(@Param("userId") Integer userId);

	@Query(value = "SELECT COUNT(order_id) FROM orders WHERE DATE(created_at) = CURDATE()", nativeQuery = true)
	Integer getSalesCount();

	boolean existsByAddress_AddressId(Integer addressId);





}
