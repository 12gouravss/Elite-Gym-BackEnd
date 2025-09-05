package in.gourav.elite.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.gourav.elite.entity.Consultancy;
import in.gourav.elite.repository.ConsultRepo;
import in.gourav.elite.response.ConsultResponse;
import in.gourav.elite.service.ConsultancyServices;

@Service
public class ConsultancyServicesImpl implements ConsultancyServices{

	@Autowired
	private ConsultRepo consultRepo;
	
	@Override
	public Integer book(Consultancy consultancy) {
		// TODO Auto-generated method stub
		return consultRepo.save(consultancy).getConsult_id();
	}

	@Override
	public List<ConsultResponse> getConsultencies(Integer userId) {
		// TODO Auto-generated method stub
		return consultRepo.findByUserId(userId);
	}



}
