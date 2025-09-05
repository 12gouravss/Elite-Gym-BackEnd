package in.gourav.elite.serviceImpl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import in.gourav.elite.DTO.UserNameMembershipDTO;
import in.gourav.elite.entity.MemberShip;
import in.gourav.elite.repository.MemberShipRepo;
import in.gourav.elite.response.MemberShipResponse;
import in.gourav.elite.service.MemberShipServices;

@Service
public class MemberShipServicesImpl  implements MemberShipServices{
	
	@Autowired
	private MemberShipRepo memberShipRepo;

	@Override
	public Integer register(MemberShip memberShip) {
		// TODO Auto-generated method stub
		return memberShipRepo.save(memberShip).getMemberShip_id();
	}

	@Override
	public boolean iscenterPresent(String center) {
		// TODO Auto-generated method stub
		return memberShipRepo.existsByCenter(center);
	}

	@Override
	public boolean istypePresent(String type) {
		// TODO Auto-generated method stub
		return memberShipRepo.existsByType(type);
	}

	@Override
	public LocalDate vaildTill(Integer id) {
		// TODO Auto-generated method stub
		return memberShipRepo.getEndDate(id);
	}

	@Override
	public List<MemberShip> getMemberships() {
		// TODO Auto-generated method stub
		return memberShipRepo.findAll();
	}

	@Override
	public List<MemberShipResponse> getMemberships(Integer id) {
		// TODO Auto-generated method stub
		return memberShipRepo.findALLByUserId(id);
	}

	@Override
	public Integer getMemberShipCount() {
		// TODO Auto-generated method stub
		return (int) memberShipRepo.count();
	}


	@Override
	public List<UserNameMembershipDTO> getMembershipsDashData(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return memberShipRepo.getDashData(pageRequest);
	}

	@Override
	public List<MemberShip> getAllMemberships() {
		// TODO Auto-generated method stub
		return memberShipRepo.findAll();
	}

	@Override
	public MemberShip getMembership(Integer id) {
		// TODO Auto-generated method stub
		return memberShipRepo.findById(id).get();
	}

	@Override
	public void updateStatus(MemberShip membership) {
		// TODO Auto-generated method stub
		memberShipRepo.save(membership);
	}

	@Override
	public MemberShipResponse getMembershipByUser(Integer id) {
		// TODO Auto-generated method stub
		return memberShipRepo.findByUserId(id);
 
 
	}

	@Override
	public boolean deleteMembership(Integer id) {
		// TODO Auto-generated method stub
		if (memberShipRepo.existsById(id)) {
            memberShipRepo.deleteById(id);
            return true;
        }
        return false;
	}



}