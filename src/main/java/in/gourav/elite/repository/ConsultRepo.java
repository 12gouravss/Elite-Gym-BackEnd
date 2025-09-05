package in.gourav.elite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.gourav.elite.entity.Consultancy;
import in.gourav.elite.entity.MemberShip;
import in.gourav.elite.entity.User;
import in.gourav.elite.response.ConsultResponse;

import java.time.LocalDate;
import java.util.List;



@Repository
public interface ConsultRepo  extends JpaRepository<Consultancy, Integer>{

	@Query(value = "SELECT consult_id,age,height,weight,consult_type,date FROM consultancy WHERE user_id = :id", nativeQuery = true)
	public List<ConsultResponse> findByUserId(@Param("id") Integer id);
}
