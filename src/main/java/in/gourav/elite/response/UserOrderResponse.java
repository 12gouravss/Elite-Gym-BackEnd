package in.gourav.elite.response;

import java.time.LocalDate;

import in.gourav.elite.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderResponse {
	
	private Integer orderId;
	 private String orderStatus;
	 private Product product;
	private Integer quantity;
	private LocalDate orderDate;
	private String paymentMode;
	private Double rating;

}
