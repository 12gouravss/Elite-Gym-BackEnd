package in.gourav.elite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.gourav.elite.entity.Address;
import in.gourav.elite.response.AddressResponse;

import java.util.List;


@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{
	
	@Query("SELECT a FROM Address a WHERE a.user.user_id = :id AND a.deleted = false")
	public List<Address> getAddress(@Param("id") Integer id);

	

}
