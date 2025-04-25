package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.NomineeDetailsEntity;
import com.example.demo.entity.ProposerDetailsEntity;
import com.example.demo.pagination.ProposerPagination;
import com.example.demo.pagination.Searching;
import com.example.demo.repository.NomineeRepository;
import com.example.demo.repository.ProposerDetailsRepository;
import com.example.demo.request.NomineeDto;
import com.example.demo.request.RequestDto;
import com.example.demo.request.RequiredDto;
import com.example.demo.validation.Validations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ProposerDetailsServiceImpl implements ProposerDetailsService {

	@Autowired
	private ProposerDetailsRepository proposerDetailsRepository;
	
	@Autowired
	private Validations validations;
	
	@Autowired
	private NomineeRepository nomineeRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public String saveProposer(RequestDto requestDto) {
		
		ProposerDetailsEntity add = new ProposerDetailsEntity();

		String missingFields = validations.checkConditions(requestDto, new ArrayList<>());

//		List<String> missingFields = new ArrayList<>();
//		
//		if(requestDto.getTitle()==null || requestDto.getTitle().toString().isEmpty() ){
//			missingFields.add("title");
//		}
//		String proposerFullName = requestDto.getProposerFullName();
//		if(proposerFullName == null || proposerFullName.isEmpty()){
//			missingFields.add("proposerFullName");
//		}
//		
//		if(requestDto.getProposerGender() == null || requestDto.getProposerGender().toString().isEmpty()){
//			missingFields.add("proposerGender");
//		}
//		
//		if(requestDto.getDateOfBirth() == null || requestDto.getDateOfBirth().toString().isEmpty()){
//			missingFields.add("dateOfBirth");
//		}
//		
//		if(requestDto.getPanNumber() == null || requestDto.getPanNumber().isEmpty()){
//			missingFields.add("panNumber");
//		}
//		
////		if(!Validations.panCardValidation(requestDto.getPanNumber())) {
////			missingFields.add("pancard format is wrong");
////		}
//		
//		if(!requestDto.getPanNumber().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")) {
//			missingFields.add("pancard format is wrong");
//		}
//		
//		if(requestDto.getPanNumber().length()!=10) {
//			 missingFields.add("pancard number is not valid");
//		}
//		
//		if(requestDto.getAadharNo() == null || requestDto.getAadharNo().toString().isEmpty()){
//			 missingFields.add("aadharNo");
//		}
//		
//		if(requestDto.getAadharNo().toString().length()!=12) {
//			 missingFields.add("aadharcard number is not valid");
//		}
//		
//		if(requestDto.getMaritalStatus() == null || requestDto.getMaritalStatus().toString().isEmpty()){
//			missingFields.add("maritalStatus");
//		}
//		
//		if(requestDto.getProposerEmail() == null || requestDto.getProposerEmail().isEmpty()){
//			missingFields.add("proposerEmail");
//		}
//		
//		if(requestDto.getProposerMobileNo() == null || requestDto.getProposerMobileNo().toString().isEmpty()){
//			missingFields.add("proposerMobileNo");
//		}
//		
//		if(requestDto.getProposerMobileNo().toString().length()!=10){
//			missingFields.add("Invalid proposerMobileNo");
//		}
//		
//		if(requestDto.getAddressLine1() == null || requestDto.getAddressLine1().isEmpty()){
//			missingFields.add("addressLine1");
//		}
//		
//		if(requestDto.getPincode() == null || requestDto.getPincode().toString().isEmpty()){
//			 missingFields.add("pincode");
//		}
//		
//		if(requestDto.getCity() == null || requestDto.getCity().isEmpty()){
//			missingFields.add("city");
//		}
//		
//		if(requestDto.getState() == null || requestDto.getState().isEmpty()){
//			missingFields.add("state");
//		}
//		
//		if(requestDto.getAlterMobileNo() == null || requestDto.getAlterMobileNo().toString().isEmpty()){
//			missingFields.add("alterMobileNo");
//		}
//		
//		
		if (missingFields != null) {
			throw new IllegalArgumentException("Missing required fields: " + String.join(", ", missingFields));
			// return "Missing required fields: " + String.join(", ", missingFields);
		}

		add.setTitle(requestDto.getTitle());
		add.setAadharNo(requestDto.getAadharNo());
		add.setAddressLine1(requestDto.getAddressLine1());
		add.setAddressLine2(requestDto.getAddressLine2());
		add.setAddressLine3(requestDto.getAddressLine3());
		add.setAlterMobileNo(requestDto.getAlterMobileNo());
		add.setCity(requestDto.getCity());
		add.setDateOfBirth(requestDto.getDateOfBirth());
		add.setMaritalStatus(requestDto.getMaritalStatus());
		add.setPanNumber(requestDto.getPanNumber());
		add.setPincode(requestDto.getPincode());
		add.setProposerEmail(requestDto.getProposerEmail());
		add.setProposerGender(requestDto.getProposerGender());
		add.setProposerFirstName(requestDto.getProposerFirstName());
		add.setProposerMiddleName(requestDto.getProposerMiddleName());
		add.setProposerLastName(requestDto.getProposerLastName());
		// add.setProposerFullName(requestDto.getProposerFullName());
		add.setProposerMobileNo(requestDto.getProposerMobileNo());
		add.setState(requestDto.getState());

		ProposerDetailsEntity proposerSave = proposerDetailsRepository.save(add);
		Integer proposerId = proposerSave.getProposerId();
		
		NomineeDto dto = requestDto.getNomineeDetailsEntities();
		
		NomineeDetailsEntity entity = new NomineeDetailsEntity();
		
		entity.setProposerId(proposerId);
		entity.setAddress(dto.getAddress());
		entity.setDateOfBirth(dto.getDateOfBirth());
		entity.setGender(dto.getGender());
		entity.setMobileNo(dto.getMobileNo());
		entity.setNomineeName(dto.getNomineeName());
		entity.setRelationWithProposer(dto.getRelationWithProposer());
		
		nomineeRepository.save(entity);
		

//		List<NomineeDto> nomineeDtos = requestDto.getNomineeDetailsEntities();
//
//		List<NomineeDetailsEntity> nomineeDetailsEntities = new ArrayList<>();
//
//		for (NomineeDto dto : nomineeDtos) {
//			NomineeDetailsEntity entity = new NomineeDetailsEntity();
//
//			entity.setProposerId(proposerSave.getProposerId());
//			entity.setAddress(dto.getAddress());
//			entity.setDateOfBirth(dto.getDateOfBirth());
//			entity.setGender(dto.getGender());
//			entity.setMobileNo(dto.getMobileNo());
//			entity.setNomineeName(dto.getNomineeName());
//			entity.setRelationWithProposer(dto.getRelationWithProposer());
//
//			nomineeDetailsEntities.add(entity);
//		}
//
//		nomineeRepository.saveAll(nomineeDetailsEntities);

		return "Proposer added successfully";
	}

	@Override
	public List<ProposerDetailsEntity> getAll(ProposerPagination pagination) {

		// List<ProposerDetailsEntity> list =
		// proposerDetailsRepository.getAllActiveStatus("Yes");                                                          

//		StringBuilder builder = new StringBuilder("select p from ProposerDetailsEntity p where p.status = 'Yes'");
//
//		String sortBy = pagination.getSortBy();
//
//		if (sortBy == null || sortBy.isEmpty()) {
//			sortBy = "proposerId";
//		}
//
//		String sortOrder = pagination.getSortOrder();
//		if (sortOrder == null || sortOrder.isEmpty()) {
//			sortOrder = "desc";
//		}
//		
//		builder.append(" order by p.").append(sortBy).append(" ").append(sortOrder);
//		
//		TypedQuery<ProposerDetailsEntity> query = entityManager.createQuery(builder.toString(), ProposerDetailsEntity.class);
//		
//		int page = pagination.getPage();
//	    int size = pagination.getSize();
//
//	    if (page == 0 && size == 0) {
//			return query.getResultList();
//		}
//		
//	    if (page != 0 || size != 0) {
//	        query.setFirstResult(page * size);
//	        query.setMaxResults(size);
//	    }

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ProposerDetailsEntity> cq = cb.createQuery(ProposerDetailsEntity.class);

		Root<ProposerDetailsEntity> root = cq.from(ProposerDetailsEntity.class);

		// cq.where(cb.equal(root.get("status"), "Yes"));

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.equal(root.get("status"), "Yes"));

		Searching searching = pagination.getSearching();
		
	
		if (searching != null) {

			String firstName = searching.getProposerFirstName();

			if (firstName != null && !firstName.isEmpty()) 
			{
				predicates.add(cb.like(cb.lower(root.get("proposerFirstName")), "%" + firstName.toLowerCase() + "%"));
			}

			String lastName = searching.getProposerLastName();

			if (lastName != null && !lastName.isEmpty()) 
			{
				predicates.add(cb.like(cb.lower(root.get("proposerLastName")), "%" + lastName.toLowerCase() + "%"));
			}

			String city = searching.getCity();

			if (city != null && !city.isEmpty())
			{
				predicates.add(cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
			}

			Long mobileNo = searching.getProposerMobileNo();

			if (mobileNo != null && !mobileNo.toString().isEmpty() && mobileNo > 0) 
			{
				predicates.add(cb.equal(root.get("proposerMobileNo"), mobileNo));
			}

		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		String sortBy = pagination.getSortBy();

		if (sortBy == null || sortBy.isEmpty())
		{
			sortBy = "proposerId";
		}

		String sortOrder = pagination.getSortOrder();
		
		if (sortOrder == null || sortOrder.isEmpty())
		{
			sortOrder = "desc";
		}

		if (sortOrder.equalsIgnoreCase("desc"))
		{
			cq.orderBy(cb.desc(root.get(sortBy)));
		} else
		{
			cq.orderBy(cb.asc(root.get(sortBy)));
		}

		int page = pagination.getPage();
		int size = pagination.getSize();

		TypedQuery<ProposerDetailsEntity> query = entityManager.createQuery(cq);

		int startIndex = (page - 1) * size;

		if (page > 0 && size > 0) {

			query.setFirstResult(startIndex);

			query.setMaxResults(size);

		} else if ((page == 0 && size > 0) || (page > 0 && size == 0)) {
			
			throw new IllegalArgumentException("page or size can't be zero");
			
		}

		return query.getResultList();

//		List<RequiredDto> dtoList = new ArrayList<>();
//		
//		for (ProposerDetailsEntity proposerDetailsEntity : list) {
//			
//			RequiredDto requiredDto = new RequiredDto();
//			
////			Map<String,Object> response = new HashMap<>();
////			response.put("title", proposerDetailsEntity.getTitle());
//			
//			requiredDto.setAadharNo(proposerDetailsEntity.getAadharNo());
//			requiredDto.setAddressLine1(proposerDetailsEntity.getAddressLine1());
//			requiredDto.setAddressLine2(proposerDetailsEntity.getAddressLine2());
//			requiredDto.setAddressLine3(proposerDetailsEntity.getAddressLine3());
//			requiredDto.setAlterMobileNo(proposerDetailsEntity.getAlterMobileNo());
//			requiredDto.setTitle(proposerDetailsEntity.getTitle());
//			requiredDto.setCity(proposerDetailsEntity.getCity());
//			requiredDto.setDateOfBirth(proposerDetailsEntity.getDateOfBirth());
//			requiredDto.setMaritalStatus(proposerDetailsEntity.getMaritalStatus());
//			requiredDto.setPanNumber(proposerDetailsEntity.getPanNumber());
//			requiredDto.setPincode(proposerDetailsEntity.getPincode());
//			requiredDto.setProposerEmail(proposerDetailsEntity.getProposerEmail());
//			requiredDto.setProposerGender(proposerDetailsEntity.getProposerGender());
//			//dto.setProposerFullName(proposerDetailsEntity.getProposerFullName());
//			
//			StringBuilder builder = new StringBuilder();
//			builder.append(proposerDetailsEntity.getProposerFirstName());
//			
//			if(proposerDetailsEntity.getProposerMiddleName()!=null) {
//				builder.append(" "+proposerDetailsEntity.getProposerMiddleName());
//			}
//			
//			builder.append(" "+proposerDetailsEntity.getProposerLastName());
//			
//			requiredDto.setProposerFullName(builder.toString());
//			requiredDto.setProposerMobileNo(proposerDetailsEntity.getProposerMobileNo());
//			requiredDto.setState(proposerDetailsEntity.getState());
//			
//			Integer proposerId = proposerDetailsEntity.getProposerId();
//			Optional<NomineeDetailsEntity> optional = nomineeRepository.findByProposerIdAndNomieeStatus(proposerId, "Yes");
//			
//			NomineeDetailsEntity detailsEntity = optional.get();
//			NomineeDto detailsEntity2 = new NomineeDto();
//			
//			detailsEntity2.setAddress(detailsEntity.getAddress());
//			detailsEntity2.setDateOfBirth(detailsEntity.getDateOfBirth());
//			detailsEntity2.setProposerId(proposerId);
//			detailsEntity2.setGender(detailsEntity.getGender());
//			detailsEntity2.setMobileNo(detailsEntity.getMobileNo());
//			detailsEntity2.setNomineeName(detailsEntity.getNomineeName());
//			detailsEntity2.setRelationWithProposer(detailsEntity.getRelationWithProposer());
//			
//			requiredDto.setNomineeDetailsEntity(detailsEntity2);
//			
//			dtoList.add(requiredDto);
//		}

	}
    
	@Override
	public String update(Integer id, RequestDto requestDto) {
		Optional<ProposerDetailsEntity> opt = proposerDetailsRepository.findByProposerIdAndStatus(id, "Yes");

		if (opt.isPresent()) {

			ProposerDetailsEntity existingProposer = opt.get();

//			String missingFields = validations.checkConditions(requestDto, new ArrayList<>());
//
//			if (missingFields != null) {
//				throw new IllegalArgumentException("Missing required fields: " + String.join(", ", missingFields));
//				// return "Missing required fields: " + String.join(", ", missingFields);
//			}
			
			if(requestDto.getTitle()!=null && !requestDto.getTitle().toString().isEmpty()) {
				existingProposer.setTitle(requestDto.getTitle());
			}
			
			// existingProposer.setProposerFullName(requestDto.getProposerFullName());
			if(requestDto.getProposerGender()!=null && !requestDto.getProposerGender().toString().isEmpty()) {
				existingProposer.setProposerGender(requestDto.getProposerGender());
			}
			
			
			if(requestDto.getProposerFirstName()!=null && !requestDto.getProposerFirstName().isEmpty()) {
				existingProposer.setProposerFirstName(requestDto.getProposerFirstName());
			}
			//existingProposer.setProposerFirstName(requestDto.getProposerFirstName());
			if(requestDto.getProposerMiddleName()!=null && !requestDto.getProposerMiddleName().isEmpty()) {
				existingProposer.setProposerMiddleName(requestDto.getProposerMiddleName());
			}
			
			if(requestDto.getProposerLastName()!=null && !requestDto.getProposerLastName().isEmpty()) {
				existingProposer.setProposerLastName(requestDto.getProposerLastName());
			}
			
			if(requestDto.getDateOfBirth()!=null && !requestDto.getDateOfBirth().toString().isEmpty()) {
				existingProposer.setDateOfBirth(requestDto.getDateOfBirth());
			}
			
			if(requestDto.getPanNumber()!=null && !requestDto.getPanNumber().isEmpty()) {
				existingProposer.setPanNumber(requestDto.getPanNumber());
			}
			
			if(requestDto.getAadharNo()!=null && !requestDto.getAadharNo().toString().isEmpty()) {
				existingProposer.setAadharNo(requestDto.getAadharNo());
			}
			
			if(requestDto.getMaritalStatus()!=null && !requestDto.getMaritalStatus().toString().isEmpty()) {
				existingProposer.setMaritalStatus(requestDto.getMaritalStatus());
			}
			
			if(requestDto.getProposerEmail()!=null && !requestDto.getProposerEmail().isEmpty()) {
				existingProposer.setProposerEmail(requestDto.getProposerEmail());
			}
			
			if(requestDto.getProposerMobileNo()!=null && !requestDto.getProposerMobileNo().toString().isEmpty()) {
				existingProposer.setProposerMobileNo(requestDto.getProposerMobileNo());
			}
			
			if(requestDto.getAlterMobileNo()!=null && !requestDto.getAlterMobileNo().toString().isEmpty()) {
				existingProposer.setAlterMobileNo(requestDto.getAlterMobileNo());
			}
			
			if(requestDto.getAddressLine1()!=null && !requestDto.getAddressLine1().isEmpty()) {
				existingProposer.setAddressLine1(requestDto.getAddressLine1());
			}
			
			if(requestDto.getAddressLine2()!=null && !requestDto.getAddressLine2().isEmpty()) {
				existingProposer.setAddressLine2(requestDto.getAddressLine2());
			}
			
			if(requestDto.getAddressLine3()!=null && !requestDto.getAddressLine3().isEmpty()) {
				existingProposer.setAddressLine3(requestDto.getAddressLine3());
			}
			
			if(requestDto.getPincode()!=null && !requestDto.getPincode().toString().isEmpty()) {
				existingProposer.setPincode(requestDto.getPincode());
			}
			
			if(requestDto.getCity()!=null && !requestDto.getCity().isEmpty()) {
				existingProposer.setCity(requestDto.getCity());
			}
			
			if(requestDto.getState()!=null && !requestDto.getState().isEmpty()) {
				existingProposer.setState(requestDto.getState());
			}
			

			ProposerDetailsEntity detailsEntity = proposerDetailsRepository.save(existingProposer);
			
			NomineeDto dto = requestDto.getNomineeDetailsEntities(); 
			Optional<NomineeDetailsEntity> optional = nomineeRepository.findByProposerIdAndNomieeStatus(detailsEntity.getProposerId(),"Yes");
			NomineeDetailsEntity existingNominee = optional.get();
			
			if(requestDto.getIsUpdate().equalsIgnoreCase("Yes")) {
				
				existingNominee.setProposerId(detailsEntity.getProposerId());
				existingNominee.setAddress(dto.getAddress());
				existingNominee.setDateOfBirth(dto.getDateOfBirth());
				existingNominee.setGender(dto.getGender());
				existingNominee.setMobileNo(dto.getMobileNo());
				existingNominee.setNomineeName(dto.getNomineeName());
				existingNominee.setRelationWithProposer(dto.getRelationWithProposer());
				
				nomineeRepository.save(existingNominee);
			}else {
				
				existingNominee.setNomieeStatus("No");
				
				nomineeRepository.save(existingNominee);
				
				NomineeDetailsEntity nomineeDetailsEntity = new NomineeDetailsEntity();
				nomineeDetailsEntity.setAddress(dto.getAddress());
				nomineeDetailsEntity.setProposerId(detailsEntity.getProposerId());
				nomineeDetailsEntity.setDateOfBirth(dto.getDateOfBirth());
				nomineeDetailsEntity.setGender(dto.getGender());
				nomineeDetailsEntity.setMobileNo(dto.getMobileNo());
				nomineeDetailsEntity.setNomineeName(dto.getNomineeName());
				nomineeDetailsEntity.setRelationWithProposer(dto.getRelationWithProposer());
				
				nomineeRepository.save(nomineeDetailsEntity);
			}

//			List<NomineeDto> nomineeDtos = requestDto.getNomineeDetailsEntities();
//
//			List<NomineeDetailsEntity> nomineeDetailsEntities = new ArrayList<>();

//			for (NomineeDto dto : nomineeDtos) {
//				Optional<List<NomineeDetailsEntity>> optional = nomineeRepository.findAllByProposerId(detailsEntity.getProposerId());
//				List<NomineeDetailsEntity> existingNominee = optional.get();				
//				for (NomineeDto dto1 : nomineeDtos) {
//					entity.setProposerId(detailsEntity.getProposerId());
//					entity.setAddress(dto1.getAddress());
//					entity.setDateOfBirth(dto1.getDateOfBirth());
//					entity.setGender(dto1.getGender());
//					entity.setMobileNo(dto1.getMobileNo());
//					entity.setNomineeName(dto1.getNomineeName());
//					entity.setRelationWithProposer(dto1.getRelationWithProposer());
//				}
				
//				Optional<List<NomineeDetailsEntity>> optional = nomineeRepository.findAllByProposerId(detailsEntity.getProposerId());
//				List<NomineeDetailsEntity> existingNominee = optional.get();	
//				
//				for(int i=0 ;i<nomineeDtos.size();i++) {
//					
//					for (NomineeDto dto1 : nomineeDtos) {
//						existingNominee.get(i).setProposerId(detailsEntity.getProposerId());
//						existingNominee.get(i).setAddress(dto1.getAddress());
//						existingNominee.get(i).setDateOfBirth(dto1.getDateOfBirth());
//						existingNominee.get(i).setGender(dto1.getGender());
//						existingNominee.get(i).setMobileNo(dto1.getMobileNo());
//						existingNominee.get(i).setNomineeName(dto1.getNomineeName());
//						existingNominee.get(i).setRelationWithProposer(dto1.getRelationWithProposer());
//						if(i==0) {
//							break;
//						}
//						if(existingNominee.size()==1) {
//							break;
//						}
//					}
//					nomineeDetailsEntities.add(existingNominee.get(i));
//				}


//				entity.setProposerId(detailsEntity.getProposerId());
//				entity.setAddress(dto.getAddress());
//				entity.setDateOfBirth(dto.getDateOfBirth());
//				entity.setGender(dto.getGender());
//				entity.setMobileNo(dto.getMobileNo());
//				entity.setNomineeName(dto.getNomineeName());
//				entity.setRelationWithProposer(dto.getRelationWithProposer());

				//nomineeDetailsEntities.add(entity);
			

			//nomineeRepository.saveAll(nomineeDetailsEntities);

			return "updated successfully";
		}
		return "Proposer not found";
	}

	@Override
	public String delete(Integer id) {
		
		Optional<ProposerDetailsEntity> opt = proposerDetailsRepository.findById(id);

		if (opt.isPresent()) {
			ProposerDetailsEntity existingProposer = opt.get();
			existingProposer.setStatus("No");
			
			
			ProposerDetailsEntity detailsEntity= proposerDetailsRepository.save(existingProposer);
			
			Optional<List<NomineeDetailsEntity>> optional = nomineeRepository.findAllByProposerId(detailsEntity.getProposerId());
			List<NomineeDetailsEntity> existingNominee = optional.get();
			
			for(int i=0 ;i<existingNominee.size();i++) {
				existingNominee.get(i).setNomieeStatus("No");
				}
			
			nomineeRepository.saveAll(existingNominee);
			
			

			return "deleted successfull";
		}
		return "proposer not existed";
	}

	@Override
	public List<ProposerDetailsEntity> getAllActiveProposers() {

		List<ProposerDetailsEntity> list = proposerDetailsRepository.getAllActiveStatus("Yes");
		return list;
	}

	@Override
	public RequiredDto fetchProposerById(Integer id) {
		
		Optional<ProposerDetailsEntity> optional = proposerDetailsRepository.findByProposerIdAndStatus(id, "Yes");
		RequiredDto dto = new RequiredDto();
		
		if(optional.isPresent()) {
			
			ProposerDetailsEntity detailsEntity = optional.get();
			
			dto.setTitle(detailsEntity.getTitle());
			//dto.setProposerFullName(detailsEntity.getProposerFullName());
			dto.setProposerGender(detailsEntity.getProposerGender());
			
			StringBuilder builder = new StringBuilder();
			builder.append(detailsEntity.getProposerFirstName());
			
			if(detailsEntity.getProposerMiddleName()!=null) {
				builder.append(" "+detailsEntity.getProposerMiddleName());
			}
			
			builder.append(" "+detailsEntity.getProposerLastName());
			dto.setProposerFullName(builder.toString());
			dto.setDateOfBirth(detailsEntity.getDateOfBirth());
			dto.setPanNumber(detailsEntity.getPanNumber());
			dto.setAadharNo(detailsEntity.getAadharNo());
			dto.setMaritalStatus(detailsEntity.getMaritalStatus());
			dto.setProposerEmail(detailsEntity.getProposerEmail());
			dto.setProposerMobileNo(detailsEntity.getProposerMobileNo());
			dto.setAlterMobileNo(detailsEntity.getAlterMobileNo());
			dto.setAddressLine1(detailsEntity.getAddressLine1());
			dto.setAddressLine2(detailsEntity.getAddressLine2());
			dto.setAddressLine3(detailsEntity.getAddressLine3());
			dto.setPincode(detailsEntity.getPincode());
			dto.setCity(detailsEntity.getCity());
			dto.setState(detailsEntity.getState());
			dto.setStatus(detailsEntity.getStatus());
			
			Integer proposerId = detailsEntity.getProposerId();
			
			Optional<NomineeDetailsEntity> opt = nomineeRepository.findByProposerIdAndNomieeStatus(proposerId,"Yes");
			NomineeDetailsEntity nomineeDetailsEntity = opt.get();
			
	        NomineeDto nomineeDto = new NomineeDto();
			nomineeDto.setProposerId(proposerId);
			nomineeDto.setAddress(nomineeDetailsEntity.getAddress());
			nomineeDto.setDateOfBirth(nomineeDetailsEntity.getDateOfBirth());
			nomineeDto.setGender(nomineeDetailsEntity.getGender());
			nomineeDto.setMobileNo(nomineeDetailsEntity.getMobileNo());
			nomineeDto.setNomineeName(nomineeDetailsEntity.getNomineeName());
			nomineeDto.setRelationWithProposer(nomineeDetailsEntity.getRelationWithProposer());
			
			dto.setNomineeDetailsEntity(nomineeDto);
			
			
//			List<NomineeDetailsEntity> detailsEntities = nomineeRepository.getAllByProposerId(proposerId);
//			List<NomineeDto> nomineeDtos = new ArrayList<>();
//			
//			for (NomineeDetailsEntity nomineeDetailsEntity : detailsEntities) {
//				
//				NomineeDto nomineeDto = new NomineeDto();
//				nomineeDto.setProposerId(proposerId);
//				nomineeDto.setAddress(nomineeDetailsEntity.getAddress());
//				nomineeDto.setDateOfBirth(nomineeDetailsEntity.getDateOfBirth());
//				nomineeDto.setGender(nomineeDetailsEntity.getGender());
//				nomineeDto.setMobileNo(nomineeDetailsEntity.getMobileNo());
//				nomineeDto.setNomineeName(nomineeDetailsEntity.getNomineeName());
//				nomineeDto.setRelationWithProposer(nomineeDetailsEntity.getRelationWithProposer());
//				
//				nomineeDtos.add(nomineeDto);
//			}
//			
//			dto.setNomineeDetailsEntities(nomineeDtos);
			
			
		}
		return dto;
	}

	@Override
	public Integer fetchTotalRecord() {
		
		List<ProposerDetailsEntity> list = proposerDetailsRepository.getAllActiveStatus("Yes");
		
		return list.size();
	}

	@Override
	public List<ProposerDetailsEntity> fetchAllByStringBuilder(ProposerPagination pagination) {
		
		StringBuilder builder = new StringBuilder("select p from ProposerDetailsEntity p where p.status = 'Yes'");

		String sortBy = pagination.getSortBy();

		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "proposerId";
		}

		String sortOrder = pagination.getSortOrder();
		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "desc";
		}

		Searching searching = pagination.getSearching();

		if (searching != null) {

			String firstName = searching.getProposerFirstName();

			if (firstName != null && !firstName.isEmpty()) {
				builder.append(" and lower(p.proposerFirstName) like ").append("'%" + firstName.toLowerCase() + "%'");
			}

			String lastName = searching.getProposerLastName();

			if (lastName != null && !lastName.isEmpty()) {
				builder.append(" and lower(p.proposerLastName) like ").append("'%" + lastName.toLowerCase() + "%'");
			}

			String city = searching.getCity();

			if (city != null && !city.isEmpty()) {
				builder.append(" and lower(p.city) like ").append("'%" + city.toLowerCase() + "%'");
			}

			Long mobileNo = searching.getProposerMobileNo();

			if (mobileNo != null && !mobileNo.toString().isEmpty() && mobileNo !=0) {
				builder.append(" and p.proposerMobileNo= ").append(mobileNo);
			}

		}

		builder.append(" order by p.").append(sortBy).append(" ").append(sortOrder);

		TypedQuery<ProposerDetailsEntity> query = entityManager.createQuery(builder.toString(),
				ProposerDetailsEntity.class);

		int page = pagination.getPage();
		int size = pagination.getSize();

		int startIndex = (page - 1) * size;

		if (page > 0 && size > 0) {

			query.setFirstResult(startIndex);

			query.setMaxResults(size);

		} else if ((page == 0 && size > 0) || (page > 0 && size == 0)) {
			throw new IllegalArgumentException("page or size can't be zero");
		}

		return query.getResultList();
	}

	

}
