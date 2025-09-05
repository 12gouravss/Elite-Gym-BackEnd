package in.gourav.elite.response;

import in.gourav.elite.entity.Product;
import lombok.Data;

@Data
public class CartResponse {
	
	private Integer cartId;

	private Product product;
	
}
