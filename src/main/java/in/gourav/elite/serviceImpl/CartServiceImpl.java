package in.gourav.elite.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gourav.elite.entity.Address;
import in.gourav.elite.entity.Cart;
import in.gourav.elite.entity.Product;
import in.gourav.elite.exception.CartException;
import in.gourav.elite.repository.AddressRepo;
import in.gourav.elite.repository.CartRepo;
import in.gourav.elite.repository.OrderRepo;
import in.gourav.elite.response.AddressResponse;
import in.gourav.elite.service.CartService;

@Service
public class CartServiceImpl  implements CartService{
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Override
	public Integer getCartCount() {
		// TODO Auto-generated method stub
		return (int) cartRepo.count();
	}

	@Override
	public boolean isPresent(Integer productId, Integer userId) {
		// TODO Auto-generated method stub
		return cartRepo.existsByProductIdAndUserId(productId, userId);
	}

	@Override
	public Integer addToCart(Cart cart) {
		// TODO Auto-generated method stub
		return cartRepo.save(cart).getCartId();
	}

	@Override
	public List<Cart> getCart(Integer userId) {
		// TODO Auto-generated method stub
		return cartRepo.getCartDetails(userId);
	}

	@Override
	public Boolean removeProduct(Integer id) {
	    if (!cartRepo.existsById(id)) {
	        throw new CartException("Product removal failed: ID not found");
	    }

	    cartRepo.deleteById(id);
	    return !cartRepo.existsById(id);
	}

	@Override
	public Integer addAddress(Address address) {
		// TODO Auto-generated method stub
		return addressRepo.save(address).getAddressId();
	}

	@Override
	public List<Address> getAddress(Integer userId) {
		// TODO Auto-generated method stub
		return addressRepo.getAddress(userId);
	}

	@Override
	public Boolean removeAddress(Integer id) {
		// TODO Auto-generated method stub
		if(!addressRepo.existsById(id)) {
			throw new CartException("Address Not Found");
		}
		boolean exists = orderRepo.existsByAddress_AddressId(id);
		System.out.println(exists);
		if(exists) {
			Address address = addressRepo.findById(id).get();
			address.setDeleted(true);
			addressRepo.save(address);
			return true;
		}
		addressRepo.deleteById(id);
		
		return !addressRepo.existsById(id);
	}

	@Override
	public Address getOrderAddress(Integer id) {
		// TODO Auto-generated method stub
		return addressRepo.findById(id).get();
	}

	@Override
	public List<Cart> getCartProducts(Integer[] cartProductIds) {
		// TODO Auto-generated method stub
		return cartRepo.findByCartIdIn(cartProductIds);
	}

	@Override
	public boolean deleteCartItems() {
	    long count = cartRepo.count();

	    if (count > 0) {
	        cartRepo.deleteAll();
	       
	    } 
	    return true;
	}

	
	

}
