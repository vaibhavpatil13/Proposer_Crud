package com.example.demo.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.BatchQueue;
import com.example.demo.entity.ErrorDetails;
import com.example.demo.entity.NomineeDetailsEntity;
import com.example.demo.entity.ProposerDetailsEntity;
import com.example.demo.enums.Gender;
import com.example.demo.enums.Marital_Status;
import com.example.demo.enums.Title;
import com.example.demo.pagination.ProposerPagination;
import com.example.demo.pagination.Searching;
import com.example.demo.repository.BatchQueueRepository;
import com.example.demo.repository.ErrorDetailsRepository;
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
	private ErrorDetailsRepository errorDetailsRepository;
	
	@Autowired
	private BatchQueueRepository batchQueueRepository;

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

			if (firstName != null && !firstName.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("proposerFirstName")), "%" + firstName.toLowerCase() + "%"));
			}

			String lastName = searching.getProposerLastName();

			if (lastName != null && !lastName.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("proposerLastName")), "%" + lastName.toLowerCase() + "%"));
			}

			String city = searching.getCity();

			if (city != null && !city.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
			}

			Long mobileNo = searching.getProposerMobileNo();

			if (mobileNo != null && !mobileNo.toString().isEmpty() && mobileNo > 0) {
				predicates.add(cb.equal(root.get("proposerMobileNo"), mobileNo));
			}

		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		String sortBy = pagination.getSortBy();

		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "proposerId";
		}

		String sortOrder = pagination.getSortOrder();

		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "desc";
		}

		if (sortOrder.equalsIgnoreCase("desc")) {
			cq.orderBy(cb.desc(root.get(sortBy)));
		} else {
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

			if (requestDto.getTitle() != null && !requestDto.getTitle().toString().isEmpty()) {
				existingProposer.setTitle(requestDto.getTitle());
			}

			// existingProposer.setProposerFullName(requestDto.getProposerFullName());
			if (requestDto.getProposerGender() != null && !requestDto.getProposerGender().toString().isEmpty()) {
				existingProposer.setProposerGender(requestDto.getProposerGender());
			}

			if (requestDto.getProposerFirstName() != null && !requestDto.getProposerFirstName().isEmpty()) {
				existingProposer.setProposerFirstName(requestDto.getProposerFirstName());
			}
			// existingProposer.setProposerFirstName(requestDto.getProposerFirstName());
			if (requestDto.getProposerMiddleName() != null && !requestDto.getProposerMiddleName().isEmpty()) {
				existingProposer.setProposerMiddleName(requestDto.getProposerMiddleName());
			}

			if (requestDto.getProposerLastName() != null && !requestDto.getProposerLastName().isEmpty()) {
				existingProposer.setProposerLastName(requestDto.getProposerLastName());
			}

			if (requestDto.getDateOfBirth() != null && !requestDto.getDateOfBirth().toString().isEmpty()) {
				existingProposer.setDateOfBirth(requestDto.getDateOfBirth());
			}

			if (requestDto.getPanNumber() != null && !requestDto.getPanNumber().isEmpty()) {
				existingProposer.setPanNumber(requestDto.getPanNumber());
			}

			if (requestDto.getAadharNo() != null && !requestDto.getAadharNo().toString().isEmpty()) {
				existingProposer.setAadharNo(requestDto.getAadharNo());
			}

			if (requestDto.getMaritalStatus() != null && !requestDto.getMaritalStatus().toString().isEmpty()) {
				existingProposer.setMaritalStatus(requestDto.getMaritalStatus());
			}

			if (requestDto.getProposerEmail() != null && !requestDto.getProposerEmail().isEmpty()) {
				existingProposer.setProposerEmail(requestDto.getProposerEmail());
			}

			if (requestDto.getProposerMobileNo() != null && !requestDto.getProposerMobileNo().toString().isEmpty()) {
				existingProposer.setProposerMobileNo(requestDto.getProposerMobileNo());
			}

			if (requestDto.getAlterMobileNo() != null && !requestDto.getAlterMobileNo().toString().isEmpty()) {
				existingProposer.setAlterMobileNo(requestDto.getAlterMobileNo());
			}

			if (requestDto.getAddressLine1() != null && !requestDto.getAddressLine1().isEmpty()) {
				existingProposer.setAddressLine1(requestDto.getAddressLine1());
			}

			if (requestDto.getAddressLine2() != null && !requestDto.getAddressLine2().isEmpty()) {
				existingProposer.setAddressLine2(requestDto.getAddressLine2());
			}

			if (requestDto.getAddressLine3() != null && !requestDto.getAddressLine3().isEmpty()) {
				existingProposer.setAddressLine3(requestDto.getAddressLine3());
			}

			if (requestDto.getPincode() != null && !requestDto.getPincode().toString().isEmpty()) {
				existingProposer.setPincode(requestDto.getPincode());
			}

			if (requestDto.getCity() != null && !requestDto.getCity().isEmpty()) {
				existingProposer.setCity(requestDto.getCity());
			}

			if (requestDto.getState() != null && !requestDto.getState().isEmpty()) {
				existingProposer.setState(requestDto.getState());
			}

			ProposerDetailsEntity detailsEntity = proposerDetailsRepository.save(existingProposer);

			NomineeDto dto = requestDto.getNomineeDetailsEntities();
			Optional<NomineeDetailsEntity> optional = nomineeRepository
					.findByProposerIdAndNomieeStatus(detailsEntity.getProposerId(), "Yes");
			NomineeDetailsEntity existingNominee = optional.get();

			if (requestDto.getIsUpdate().equalsIgnoreCase("Yes")) {

				existingNominee.setProposerId(detailsEntity.getProposerId());
				existingNominee.setAddress(dto.getAddress());
				existingNominee.setDateOfBirth(dto.getDateOfBirth());
				existingNominee.setGender(dto.getGender());
				existingNominee.setMobileNo(dto.getMobileNo());
				existingNominee.setNomineeName(dto.getNomineeName());
				existingNominee.setRelationWithProposer(dto.getRelationWithProposer());

				nomineeRepository.save(existingNominee);
			} else {

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

			// nomineeDetailsEntities.add(entity);

			// nomineeRepository.saveAll(nomineeDetailsEntities);

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

			ProposerDetailsEntity detailsEntity = proposerDetailsRepository.save(existingProposer);

			Optional<List<NomineeDetailsEntity>> optional = nomineeRepository
					.findAllByProposerId(detailsEntity.getProposerId());
			List<NomineeDetailsEntity> existingNominee = optional.get();

			for (int i = 0; i < existingNominee.size(); i++) {
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

		Optional<ProposerDetailsEntity> optional = proposerDetailsRepository.findById(id, "Yes");
		RequiredDto dto = new RequiredDto();

		if (optional.isPresent()) {

			ProposerDetailsEntity detailsEntity = optional.get();

			dto.setTitle(detailsEntity.getTitle());
			// dto.setProposerFullName(detailsEntity.getProposerFullName());
			dto.setProposerGender(detailsEntity.getProposerGender());

			StringBuilder builder = new StringBuilder();
			builder.append(detailsEntity.getProposerFirstName());

			if (detailsEntity.getProposerMiddleName() != null) {
				builder.append(" " + detailsEntity.getProposerMiddleName());
			}

			builder.append(" " + detailsEntity.getProposerLastName());
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

			Optional<NomineeDetailsEntity> opt = nomineeRepository.findByProposerIdAndNomieeStatus(proposerId, "Yes");
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

		sortBy = (sortBy == null || sortBy.isEmpty()) ? "proposerId" : sortBy;

//		if (sortBy == null || sortBy.isEmpty()) {
//			sortBy = "proposerId";
//		}

		String sortOrder = pagination.getSortOrder();

		sortOrder = (sortOrder == null || sortOrder.isEmpty()) ? "desc" : sortOrder;

//		if (sortOrder == null || sortOrder.isEmpty()) {
//			sortOrder = "desc";
//		}

		if (pagination.getSearching() == null) {
			pagination.setSearching(new Searching());
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

			if (mobileNo != null && !mobileNo.toString().isEmpty() && mobileNo != 0) {
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

	@Override
	public String getDataInExcel() throws IOException {

//		List<ProposerDetailsEntity> list = proposerDetailsRepository.getAllActiveStatus("Yes");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Proposer Details");
		XSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("Title");
		row.createCell(1).setCellValue("FirstName");
		row.createCell(2).setCellValue("MiddleName");
		row.createCell(3).setCellValue("LastName");
		row.createCell(4).setCellValue("ProposerGender");
		row.createCell(5).setCellValue("DateOfBirth");
		row.createCell(6).setCellValue("PanNumber");
		row.createCell(7).setCellValue("AadharNo");
		row.createCell(8).setCellValue("MaritalStatus");
		row.createCell(9).setCellValue("ProposerEmail");
		row.createCell(10).setCellValue("ProposerMobileNo");
		row.createCell(11).setCellValue("AlterMobileNo");
		row.createCell(12).setCellValue("AddressLine1");
		row.createCell(13).setCellValue("AddressLine2");
		row.createCell(14).setCellValue("AddressLine3");
		row.createCell(15).setCellValue("Pincode");
		row.createCell(16).setCellValue("City");
		row.createCell(17).setCellValue("State");

//		int dataRowIndex = 1;
//
//		for (ProposerDetailsEntity detailsEntity : list) {
//
//			XSSFRow dataRow = sheet.createRow(dataRowIndex);
//			dataRow.createCell(0).setCellValue(detailsEntity.getProposerId());
//			dataRow.createCell(1).setCellValue(detailsEntity.getTitle().toString());
//			dataRow.createCell(2).setCellValue(detailsEntity.getProposerFirstName());
//			dataRow.createCell(3).setCellValue(detailsEntity.getProposerMiddleName());
//			dataRow.createCell(4).setCellValue(detailsEntity.getProposerLastName());
//			dataRow.createCell(5).setCellValue(detailsEntity.getProposerGender().toString());
//			dataRow.createCell(6).setCellValue(detailsEntity.getDateOfBirth().toString());
//			dataRow.createCell(7).setCellValue(detailsEntity.getPanNumber());
//			dataRow.createCell(8).setCellValue(detailsEntity.getAadharNo().toString());
//			dataRow.createCell(9).setCellValue(detailsEntity.getMaritalStatus().toString());
//			dataRow.createCell(10).setCellValue(detailsEntity.getCreatedAt().toString());
//			dataRow.createCell(11).setCellValue(detailsEntity.getUpdatedAt().toString());
//			dataRow.createCell(12).setCellValue(detailsEntity.getProposerEmail());
//			dataRow.createCell(13).setCellValue(detailsEntity.getProposerMobileNo().toString());
//			dataRow.createCell(14).setCellValue(detailsEntity.getAlterMobileNo().toString());
//			dataRow.createCell(15).setCellValue(detailsEntity.getAddressLine1());
//			dataRow.createCell(16).setCellValue(detailsEntity.getAddressLine2());
//			dataRow.createCell(17).setCellValue(detailsEntity.getAddressLine3());
//			dataRow.createCell(18).setCellValue(detailsEntity.getPincode().toString());
//			dataRow.createCell(19).setCellValue(detailsEntity.getCity());
//			dataRow.createCell(20).setCellValue(detailsEntity.getState());
//
//			dataRowIndex++;
//		}
		String uid = UUID.randomUUID().toString().substring(0, 3);
		String fileName = "Proposer" + uid + ".xlsx";
		String filePath = "C:\\Users\\HP\\Documents\\" + fileName;

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			workbook.write(out);
		}
		workbook.close();

		return filePath;
	}

	@Override
	public String importExcel(MultipartFile file) throws IOException {

		Workbook workbook = new XSSFWorkbook(file.getInputStream());

		Sheet sheet = workbook.getSheetAt(0);

		Row headRow = sheet.getRow(0);

		int lastCol = headRow.getLastCellNum();

		headRow.createCell(lastCol).setCellValue("Error Message");

		headRow.createCell(lastCol + 1).setCellValue("Error Status");

//		XSSFWorkbook exportWorkbook = new XSSFWorkbook();
//		XSSFSheet exportSheet = exportWorkbook.createSheet("Response Error ");
//		XSSFRow exportRow = exportSheet.createRow(0);
//
//		exportRow.createCell(0).setCellValue("ErrorMessage");
//		exportRow.createCell(1).setCellValue("ErrorField");
//		exportRow.createCell(2).setCellValue("Status");
//		exportRow.createCell(3).setCellValue("RowIndex");

		List<String> errors = new ArrayList<>();
		int dataRowIndex = 1;

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			Row row = sheet.getRow(i);

			if (row == null) {
				continue;
			}

			List<String> errorFields = new ArrayList<>();
			List<String> fields = new ArrayList<>();

			Cell firstName = row.getCell(1);
			if (firstName == null || firstName.getStringCellValue().isEmpty()) {
				errorFields.add("First Name is missing");
				fields.add("First Name");

			}

			Cell middleName = row.getCell(2);
			if (middleName == null || middleName.getStringCellValue().isEmpty()) {
				errorFields.add("Middle Name is missing");
				fields.add("Middle Name");
			}

			Cell lastName = row.getCell(3);
			if (lastName == null || lastName.getStringCellValue().isEmpty()) {
				errorFields.add("Last Name is missing");
				fields.add("Last Name");
			}

			Cell gender = row.getCell(4);
			if (gender == null || gender.getStringCellValue().isEmpty()) {
				errorFields.add("Gender is missing");
				fields.add("Gender");
			}

			Cell dob = row.getCell(5);
			if (dob == null || dob.getDateCellValue() == null) {
				errorFields.add("Date of Birth of is missing");
				fields.add("Date of Birth");
			}

			Cell panNumber = row.getCell(6);
			if (panNumber == null || panNumber.getStringCellValue().isEmpty()) {
				errorFields.add("Pan Number is missing");
				fields.add("Pan Number");
			} else if (!panNumber.getStringCellValue().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")
					|| proposerDetailsRepository.existsByPanNumber(panNumber.getStringCellValue())) {
				errorFields.add("Pan Number is Invalid or Already Exists");
				fields.add("Pan Number");
			}

			Cell aadharNumber = row.getCell(7);
			Long aadharLength = (long) aadharNumber.getNumericCellValue();
			if (aadharNumber == null || aadharNumber.getNumericCellValue() == 0
					|| aadharLength.toString().length() != 12) {
				errorFields.add("Aadhar Number is missing or Invalid");
				fields.add("Aadhar Number");
			} else if (proposerDetailsRepository.existsByAadharNo((long) aadharNumber.getNumericCellValue())) {
				errorFields.add("Aadhar Number Already Exists");
				fields.add("Aadhar Number");
			}

			Cell maritalStatus = row.getCell(8);
			if (maritalStatus == null || maritalStatus.getStringCellValue().isEmpty()) {
				errorFields.add("Marital Status is missing");
				fields.add("Marital Status");
			}

			Cell emailId = row.getCell(9);
			if (emailId == null || emailId.getCellType() == CellType.BLANK) {
				errorFields.add("Email Id is missing");
				fields.add("Email Id");
			} else if (proposerDetailsRepository.existsByProposerEmail(emailId.getStringCellValue())) {
				errorFields.add("Email Id Already Exists");
				fields.add("Email Id");
			}

			Cell mobileNumber = row.getCell(10);
			Long mobileLength = (long) mobileNumber.getNumericCellValue();
			if (mobileNumber == null || mobileLength.toString().length() != 10) {
				errorFields.add("Mobile Number is missing or Invalid");
				fields.add("Mobile Number");
			} else if (proposerDetailsRepository.existsByProposerMobileNo((long) mobileNumber.getNumericCellValue())) {
				errorFields.add("Mobile Number Already Exists");
				fields.add("Mobile Number");
			}

			Cell address = row.getCell(12);
			if (address == null || address.getStringCellValue().isEmpty()) {
				errorFields.add("Address is missing");
				fields.add("Address");
			}

			Cell pincode = row.getCell(15);
			Long pinLength = (long) pincode.getNumericCellValue();
			if (pincode == null || pincode.getNumericCellValue() == 0 || pinLength.toString().length() != 6) {
				errorFields.add("Pincode is missing or not Valid");
				fields.add("Pincode");
			}

			Cell city = row.getCell(16);
			if (city == null || city.getStringCellValue().isEmpty()) {
				errorFields.add("City is missing");
				fields.add("City");
			}

			Cell state = row.getCell(17);
			if (state == null || state.getStringCellValue().isEmpty()) {
				errorFields.add("State is missing");
				fields.add("State");
			}

			if (errorFields != null && !errorFields.isEmpty()) {

				errors.addAll(errorFields);

				int k = 0;
				for (String individualError : errorFields) {

					ErrorDetails errorDetails = new ErrorDetails();
					errorDetails.setError(individualError);
					errorDetails.setErrorField(fields.get(k));
					errorDetails.setStatus("Fail");
					errorDetails.setRowIndex(row.getRowNum());

//					XSSFRow dataRow = exportSheet.createRow(dataRowIndex);
//					dataRow.createCell(0).setCellValue(individualError);
//					dataRow.createCell(1).setCellValue(fields.get(k));
//					dataRow.createCell(2).setCellValue("Fail");
//					dataRow.createCell(3).setCellValue(row.getRowNum());
//					dataRowIndex++;

					errorDetailsRepository.save(errorDetails);
					k++;
				}

				Cell errorMessage = row.createCell(lastCol);
				Cell status = row.createCell(lastCol + 1);

				errorMessage.setCellValue(String.join(", ", errorFields));
				status.setCellValue("fail");

			} else {
				ProposerDetailsEntity entity = new ProposerDetailsEntity();

				entity.setTitle(Title.valueOf(row.getCell(0).getStringCellValue()));
				entity.setProposerFirstName(firstName.getStringCellValue());
				entity.setProposerMiddleName(middleName.getStringCellValue());
				entity.setProposerLastName(lastName.getStringCellValue());
				entity.setProposerGender(Gender.valueOf(gender.getStringCellValue()));
				Date utilDate = dob.getDateCellValue(); // java.util.Date
				if (utilDate != null) {
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					entity.setDateOfBirth(sqlDate);
				}
				entity.setPanNumber(panNumber.getStringCellValue());
				entity.setAadharNo((long) aadharNumber.getNumericCellValue());
				entity.setMaritalStatus(Marital_Status.valueOf(maritalStatus.getStringCellValue()));
				entity.setProposerEmail(emailId.getStringCellValue());
				entity.setProposerMobileNo((long) mobileNumber.getNumericCellValue());
				entity.setAlterMobileNo((long) row.getCell(11).getNumericCellValue());
				entity.setAddressLine1(address.getStringCellValue());
				entity.setAddressLine2(row.getCell(13).getStringCellValue());
				entity.setAddressLine3(row.getCell(14).getStringCellValue());
				entity.setPincode((long) pincode.getNumericCellValue());
				entity.setCity(city.getStringCellValue());
				entity.setState(state.getStringCellValue());

				ProposerDetailsEntity save = proposerDetailsRepository.save(entity);

				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setError("Proposer saved with id: " + save.getProposerId());
				errorDetails.setErrorField(null);
				errorDetails.setStatus("Success");
				errorDetails.setRowIndex(row.getRowNum());

//				XSSFRow dataRow = exportSheet.createRow(dataRowIndex);
//				dataRow.createCell(0).setCellValue(save.getProposerId().toString());
//				dataRow.createCell(1).setCellValue("");
//				dataRow.createCell(2).setCellValue("Success");
//				dataRow.createCell(3).setCellValue(row.getRowNum());
//				dataRowIndex++;

				errorDetailsRepository.save(errorDetails);

				Cell errorMessage = row.createCell(lastCol);
				Cell status = row.createCell(lastCol + 1);

				errorMessage.setCellValue(save.getProposerId().toString());
				status.setCellValue("success");

			}

		}

		String uid = UUID.randomUUID().toString().substring(0, 3);
		String fileName = "ErrorResponse" + uid + ".xlsx";
		String filePath = "C:\\Users\\HP\\Documents\\" + fileName;

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			workbook.write(out);
		}

		workbook.close();

		if (!errors.isEmpty()) {
			return String.join(", ", errors);
		}

		return "All Proposers Saved";
	}

	@Override
	public String queueExcel(MultipartFile file) throws IOException {

		Workbook workbook = new XSSFWorkbook(file.getInputStream());

		Sheet sheet = workbook.getSheetAt(0);

		List<String> errors = new ArrayList<>();

		int totalRows = sheet.getLastRowNum();
		int batchSize = 3;

		if (totalRows > 5) {
			String uid = UUID.randomUUID().toString().substring(0, 4);
			String fileName = "QueueFile" + uid + ".xlsx";
			String filePath = "C:\\Users\\HP\\Documents\\" + fileName;

			try (FileOutputStream out = new FileOutputStream(filePath)) {
				workbook.write(out);
			}

			BatchQueue batchQueue = new BatchQueue();
			batchQueue.setFilePath(filePath);
			batchQueue.setRowCount(totalRows);
			batchQueue.setIsProcess("N");
			batchQueue.setRowRead(0);
			batchQueue.setStatus("N");
			batchQueue.setLastProcessCount(batchSize);

			batchQueueRepository.save(batchQueue);

			workbook.close();

			return "File goes for batch processing";

		}

//		  if (rowRead % batchSize == 0 || i == totalRows) {
//			  	System.out.println("XYZ"+rowRead % batchSize);
//	            batchQueue.setRowRead(rowRead);
//	            batchQueue.setLastProcessCount(batchSize);
//	            batchQueue.setIsProcess("Y"); 
//	            batchQueue.setStatus(rowRead == totalRows ? "COMPLETED" : "IN_PROCESS");
//	            
//	            batchQueueRepository.save(batchQueue);
//	        }

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			Row row = sheet.getRow(i);

			if (row == null) {
				continue;
			}

			List<String> errorFields = new ArrayList<>();
			List<String> fields = new ArrayList<>();

			Cell firstName = row.getCell(1);
			if (firstName == null || firstName.getStringCellValue().isEmpty()) {
				errorFields.add("First Name is missing");
				fields.add("First Name");

			}

			Cell middleName = row.getCell(2);
			if (middleName == null || middleName.getStringCellValue().isEmpty()) {
				errorFields.add("Middle Name is missing");
				fields.add("Middle Name");
			}

			Cell lastName = row.getCell(3);
			if (lastName == null || lastName.getStringCellValue().isEmpty()) {
				errorFields.add("Last Name is missing");
				fields.add("Last Name");
			}

			Cell gender = row.getCell(4);
			if (gender == null || gender.getStringCellValue().isEmpty()) {
				errorFields.add("Gender is missing");
				fields.add("Gender");
			}

			Cell dob = row.getCell(5);
			if (dob == null || dob.getDateCellValue() == null) {
				errorFields.add("Date of Birth of is missing");
				fields.add("Date of Birth");
			}

			Cell panNumber = row.getCell(6);
			if (panNumber == null || panNumber.getStringCellValue().isEmpty()) {
				errorFields.add("Pan Number is missing");
				fields.add("Pan Number");
			} else if (!panNumber.getStringCellValue().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")
					|| proposerDetailsRepository.existsByPanNumber(panNumber.getStringCellValue())) {
				errorFields.add("Pan Number is Invalid or Already Exists");
				fields.add("Pan Number");
			}

			Cell aadharNumber = row.getCell(7);
			Long aadharLength = (long) aadharNumber.getNumericCellValue();
			if (aadharNumber == null || aadharNumber.getNumericCellValue() == 0
					|| aadharLength.toString().length() != 12) {
				errorFields.add("Aadhar Number is missing or Invalid");
				fields.add("Aadhar Number");
			} else if (proposerDetailsRepository.existsByAadharNo((long) aadharNumber.getNumericCellValue())) {
				errorFields.add("Aadhar Number Already Exists");
				fields.add("Aadhar Number");
			}

			Cell maritalStatus = row.getCell(8);
			if (maritalStatus == null || maritalStatus.getStringCellValue().isEmpty()) {
				errorFields.add("Marital Status is missing");
				fields.add("Marital Status");
			}

			Cell emailId = row.getCell(9);
			if (emailId == null || emailId.getCellType() == CellType.BLANK) {
				errorFields.add("Email Id is missing");
				fields.add("Email Id");
			} else if (proposerDetailsRepository.existsByProposerEmail(emailId.getStringCellValue())) {
				errorFields.add("Email Id Already Exists");
				fields.add("Email Id");
			}

			Cell mobileNumber = row.getCell(10);
			Long mobileLength = (long) mobileNumber.getNumericCellValue();
			if (mobileNumber == null || mobileLength.toString().length() != 10) {
				errorFields.add("Mobile Number is missing or Invalid");
				fields.add("Mobile Number");
			} else if (proposerDetailsRepository.existsByProposerMobileNo((long) mobileNumber.getNumericCellValue())) {
				errorFields.add("Mobile Number Already Exists");
				fields.add("Mobile Number");
			}

			Cell address = row.getCell(12);
			if (address == null || address.getStringCellValue().isEmpty()) {
				errorFields.add("Address is missing");
				fields.add("Address");
			}

			Cell pincode = row.getCell(15);
			Long pinLength = (long) pincode.getNumericCellValue();
			if (pincode == null || pincode.getNumericCellValue() == 0 || pinLength.toString().length() != 6) {
				errorFields.add("Pincode is missing or not Valid");
				fields.add("Pincode");
			}

			Cell city = row.getCell(16);
			if (city == null || city.getStringCellValue().isEmpty()) {
				errorFields.add("City is missing");
				fields.add("City");
			}

			Cell state = row.getCell(17);
			if (state == null || state.getStringCellValue().isEmpty()) {
				errorFields.add("State is missing");
				fields.add("State");
			}

			if (errorFields != null && !errorFields.isEmpty()) {

				errors.addAll(errorFields);

				int k = 0;
				for (String individualError : errorFields) {

					ErrorDetails errorDetails = new ErrorDetails();
					errorDetails.setError(individualError);
					errorDetails.setErrorField(fields.get(k));
					errorDetails.setStatus("Fail");
					errorDetails.setRowIndex(row.getRowNum());

					errorDetailsRepository.save(errorDetails);
					k++;
				}

			} else {
				ProposerDetailsEntity entity = new ProposerDetailsEntity();

				entity.setTitle(Title.valueOf(row.getCell(0).getStringCellValue()));
				entity.setProposerFirstName(firstName.getStringCellValue());
				entity.setProposerMiddleName(middleName.getStringCellValue());
				entity.setProposerLastName(lastName.getStringCellValue());
				entity.setProposerGender(Gender.valueOf(gender.getStringCellValue()));
				Date utilDate = dob.getDateCellValue(); // java.util.Date
				if (utilDate != null) {
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					entity.setDateOfBirth(sqlDate);
				}
				entity.setPanNumber(panNumber.getStringCellValue());
				entity.setAadharNo((long) aadharNumber.getNumericCellValue());
				entity.setMaritalStatus(Marital_Status.valueOf(maritalStatus.getStringCellValue()));
				entity.setProposerEmail(emailId.getStringCellValue());
				entity.setProposerMobileNo((long) mobileNumber.getNumericCellValue());
				entity.setAlterMobileNo((long) row.getCell(11).getNumericCellValue());
				entity.setAddressLine1(address.getStringCellValue());
				entity.setAddressLine2(row.getCell(13).getStringCellValue());
				entity.setAddressLine3(row.getCell(14).getStringCellValue());
				entity.setPincode((long) pincode.getNumericCellValue());
				entity.setCity(city.getStringCellValue());
				entity.setState(state.getStringCellValue());

				ProposerDetailsEntity save = proposerDetailsRepository.save(entity);

				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setError("Proposer saved with id: " + save.getProposerId());
				errorDetails.setErrorField(null);
				errorDetails.setStatus("Success");
				errorDetails.setRowIndex(row.getRowNum());

				errorDetailsRepository.save(errorDetails);

			}

//			rowRead++;
//			
//			  if (rowRead % batchSize == 0 || i == totalRows) {
//				  	System.out.println("XYZ"+rowRead % batchSize);
//		            batchQueue.setRowRead(rowRead);
//		            batchQueue.setLastProcessCount(batchSize);
//		            batchQueue.setIsProcess("Y"); 
//		            batchQueue.setStatus(rowRead == totalRows ? "COMPLETED" : "IN_PROCESS");
//		            
//		            batchQueueRepository.save(batchQueue);
//		        }

		}

		String uid = UUID.randomUUID().toString().substring(0, 3);
		String fileName = "ErrorResponse" + uid + ".xlsx";
		String filePath = "C:\\Users\\HP\\Documents\\" + fileName;

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			workbook.write(out);
		}

		workbook.close();

		if (!errors.isEmpty()) {
			return String.join(", ", errors);
		}

		return "All Proposers Saved";
	}

	@Override
	@Scheduled(fixedDelay = 5000)
	public void batchProcessing() throws FileNotFoundException {
//	    long startTime = System.currentTimeMillis(); // Capture start time

		List<BatchQueue> batchQueues = batchQueueRepository.findByIsProcess("N");

		for (BatchQueue batchQueue : batchQueues) {

			try {
	
				FileInputStream fis = new FileInputStream(batchQueue.getFilePath());
				Workbook workbook = new XSSFWorkbook(fis);
				Sheet sheet = workbook.getSheetAt(0);

				    // Then use this 'sheet' in your logic below
				
//				Workbook workbook = new XSSFWorkbook(batchQueue.getFilePath());
//
//				Sheet sheet = workbook.getSheetAt(0);

				int rowStart = batchQueue.getRowRead() + 1;
				int totalRows = batchQueue.getRowCount();
				int batchSize = 3;
				
				Row headRow = sheet.getRow(0);
				
				if (rowStart == 1) {
					int lastCol = headRow.getLastCellNum();

					headRow.createCell(lastCol).setCellValue("Error Message");

					headRow.createCell(lastCol + 1).setCellValue("Error Status");
				}

//				int lastCol = headRow.getLastCellNum();
//					
//					headRow.createCell(lastCol).setCellValue("Error Message");
//
//					headRow.createCell(lastCol + 1).setCellValue("Error Status");
			


				for (int i = rowStart; i <= totalRows && i < rowStart + batchSize; i++) {

					Row row = sheet.getRow(i);

					if (row == null) {
						continue;
					}

					List<String> errorFields = new ArrayList<>();
					List<String> fields = new ArrayList<>();

					Cell firstName = row.getCell(1);
					if (firstName == null || firstName.getStringCellValue().isEmpty()) {
						errorFields.add("First Name is missing");
						fields.add("First Name");

					}

					Cell middleName = row.getCell(2);
					if (middleName == null || middleName.getStringCellValue().isEmpty()) {
						errorFields.add("Middle Name is missing");
						fields.add("Middle Name");
					}

					Cell lastName = row.getCell(3);
					if (lastName == null || lastName.getStringCellValue().isEmpty()) {
						errorFields.add("Last Name is missing");
						fields.add("Last Name");
					}

					Cell gender = row.getCell(4);
					if (gender == null || gender.getStringCellValue().isEmpty()) {
						errorFields.add("Gender is missing");
						fields.add("Gender");
					}

					Cell dob = row.getCell(5);
					if (dob == null || dob.getDateCellValue() == null) {
						errorFields.add("Date of Birth of is missing");
						fields.add("Date of Birth");
					}

					Cell panNumber = row.getCell(6);
					if (panNumber == null || panNumber.getStringCellValue().isEmpty()) {
						errorFields.add("Pan Number is missing");
						fields.add("Pan Number");
					} else if (!panNumber.getStringCellValue().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")
							|| proposerDetailsRepository.existsByPanNumber(panNumber.getStringCellValue())) {
						errorFields.add("Pan Number is Invalid or Already Exists");
						fields.add("Pan Number");
					}

					Cell aadharNumber = row.getCell(7);
					Long aadharLength = (long) aadharNumber.getNumericCellValue();
					if (aadharNumber == null || aadharNumber.getNumericCellValue() == 0
							|| aadharLength.toString().length() != 12) {
						errorFields.add("Aadhar Number is missing or Invalid");
						fields.add("Aadhar Number");
					} else if (proposerDetailsRepository.existsByAadharNo((long) aadharNumber.getNumericCellValue())) {
						errorFields.add("Aadhar Number Already Exists");
						fields.add("Aadhar Number");
					}

					Cell maritalStatus = row.getCell(8);
					if (maritalStatus == null || maritalStatus.getStringCellValue().isEmpty()) {
						errorFields.add("Marital Status is missing");
						fields.add("Marital Status");
					}

					Cell emailId = row.getCell(9);
					if (emailId == null || emailId.getCellType() == CellType.BLANK) {
						errorFields.add("Email Id is missing");
						fields.add("Email Id");
					} else if (proposerDetailsRepository.existsByProposerEmail(emailId.getStringCellValue())) {
						errorFields.add("Email Id Already Exists");
						fields.add("Email Id");
					}

					Cell mobileNumber = row.getCell(10);
					Long mobileLength = (long) mobileNumber.getNumericCellValue();
					if (mobileNumber == null || mobileLength.toString().length() != 10) {
						errorFields.add("Mobile Number is missing or Invalid");
						fields.add("Mobile Number");
					} else if (proposerDetailsRepository
							.existsByProposerMobileNo((long) mobileNumber.getNumericCellValue())) {
						errorFields.add("Mobile Number Already Exists");
						fields.add("Mobile Number");
					}

					Cell address = row.getCell(12);
					if (address == null || address.getStringCellValue().isEmpty()) {
						errorFields.add("Address is missing");
						fields.add("Address");
					}

					Cell pincode = row.getCell(15);
					Long pinLength = (long) pincode.getNumericCellValue();
					if (pincode == null || pincode.getNumericCellValue() == 0 || pinLength.toString().length() != 6) {
						errorFields.add("Pincode is missing or not Valid");
						fields.add("Pincode");
					}

					Cell city = row.getCell(16);
					if (city == null || city.getStringCellValue().isEmpty()) {
						errorFields.add("City is missing");
						fields.add("City");
					}

					Cell state = row.getCell(17);
					if (state == null || state.getStringCellValue().isEmpty()) {
						errorFields.add("State is missing");
						fields.add("State");
					}

					if (errorFields != null && !errorFields.isEmpty()) {

						int k = 0;
						for (String individualError : errorFields) {

							ErrorDetails errorDetails = new ErrorDetails();
							errorDetails.setQueueId(batchQueue.getQueueId());
							errorDetails.setError(individualError);
							errorDetails.setErrorField(fields.get(k));
							errorDetails.setStatus("Fail");
							errorDetails.setRowIndex(row.getRowNum());

							errorDetailsRepository.save(errorDetails);
							k++;
						}
						
//						if(i==1) {
//							Cell errorMessage = row.createCell(row.getLastCellNum()+1);
//							Cell status = row.createCell(row.getLastCellNum()+2);
//
//							errorMessage.setCellValue(String.join(", ", errorFields));
//							status.setCellValue("fail");
//						}else {
//							Cell errorMessage = row.createCell(row.getLastCellNum()-1);
//							Cell status = row.createCell(row.getLastCellNum());
//
//							errorMessage.setCellValue(String.join(", ", errorFields));
//							status.setCellValue("fail");
//						}
						
						Cell errorMessage = row.createCell(headRow.getLastCellNum()-2);
						Cell status = row.createCell(headRow.getLastCellNum() - 1);

						errorMessage.setCellValue(String.join(", ", errorFields));
						status.setCellValue("fail");
						

					} else {
						ProposerDetailsEntity entity = new ProposerDetailsEntity();

						entity.setTitle(Title.valueOf(row.getCell(0).getStringCellValue()));
						entity.setProposerFirstName(firstName.getStringCellValue());
						entity.setProposerMiddleName(middleName.getStringCellValue());
						entity.setProposerLastName(lastName.getStringCellValue());
						entity.setProposerGender(Gender.valueOf(gender.getStringCellValue()));
						Date utilDate = dob.getDateCellValue(); // java.util.Date
						if (utilDate != null) {
							java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
							entity.setDateOfBirth(sqlDate);
						}
						entity.setPanNumber(panNumber.getStringCellValue());
						entity.setAadharNo((long) aadharNumber.getNumericCellValue());
						entity.setMaritalStatus(Marital_Status.valueOf(maritalStatus.getStringCellValue()));
						entity.setProposerEmail(emailId.getStringCellValue());
						entity.setProposerMobileNo((long) mobileNumber.getNumericCellValue());
						entity.setAlterMobileNo((long) row.getCell(11).getNumericCellValue());
						entity.setAddressLine1(address.getStringCellValue());
						entity.setAddressLine2(row.getCell(13).getStringCellValue());
						entity.setAddressLine3(row.getCell(14).getStringCellValue());
						entity.setPincode((long) pincode.getNumericCellValue());
						entity.setCity(city.getStringCellValue());
						entity.setState(state.getStringCellValue());

						ProposerDetailsEntity save = proposerDetailsRepository.save(entity);

						ErrorDetails errorDetails = new ErrorDetails();
						errorDetails.setQueueId(batchQueue.getQueueId());
						errorDetails.setError("Proposer saved with id: " + save.getProposerId());
						errorDetails.setErrorField(null);
						errorDetails.setStatus("Success");
						errorDetails.setRowIndex(row.getRowNum());

						errorDetailsRepository.save(errorDetails);
						
						Cell errorMessage = row.createCell(headRow.getLastCellNum()-2);
						Cell status = row.createCell(headRow.getLastCellNum() - 1);

						errorMessage.setCellValue(save.getProposerId().toString());
						status.setCellValue("success");

					}

					batchQueue.setRowRead(i);
					
				}
				
				
				
			

				if (batchQueue.getRowRead() >= totalRows) {
					batchQueue.setStatus("Y");
					batchQueue.setIsProcess("Y");
					
//					Workbook resultWorkbook = new XSSFWorkbook();
//					Sheet resultSheet = resultWorkbook.createSheet("Response Excel");
//
//					
//					Row row = sheet.getRow(0);
//					Row resultHeader = resultSheet.createRow(0);
//					int colCount = row.getLastCellNum();
//
//					
//					for (int i = 0; i < colCount; i++) {
//					    resultHeader.createCell(i).setCellValue(row.getCell(i).toString());
//					}
//
//					
//					resultHeader.createCell(colCount).setCellValue("Status");
//					resultHeader.createCell(colCount + 1).setCellValue("Error Message");
//
//					// Fetch all errors for this batch
//					List<ErrorDetails> allErrors = errorDetailsRepository.findByQueueId(batchQueue.getQueueId());
//
//					int rowIndex = 1;
//					for (ErrorDetails error : allErrors) {
//					    int errorRowNum = error.getRowIndex();
//					    Row originalRow = sheet.getRow(errorRowNum);
//					    if (originalRow == null) continue;
//
//					  
//					    Row resultRow = resultSheet.createRow(rowIndex++);
//
//					    
//					    for (int i = 0; i < colCount; i++) {
//					        Cell originalCell = originalRow.getCell(i);
//					        if (originalCell != null) {
//					            resultRow.createCell(i).setCellValue(originalCell.toString());
//					        }
//					    }
//
//					    // Add status and error message
//					    resultRow.createCell(colCount).setCellValue(error.getStatus());
//					    resultRow.createCell(colCount + 1).setCellValue(error.getError());
//					}
//
//					// Save result file
//					String resultPath = batchQueue.getFilePath().replace(".xlsx", "_Result.xlsx");
//					try (FileOutputStream fos = new FileOutputStream(resultPath)) {
//					    resultWorkbook.write(fos);
//					}
//					resultWorkbook.close();
				}
				
				
				
//				String uid = UUID.randomUUID().toString().substring(0, 4);
//				String fileName = "QueueFile" + uid + ".xlsx";
//				String filePath = "C:\\Users\\HP\\Documents\\" + fileName;
				
				String filePath = batchQueue.getFilePath();

				try (FileOutputStream out = new FileOutputStream(filePath)) {
					workbook.write(out);
	
				}
				
				batchQueue.setFilePath(filePath);

				workbook.close();
		
				batchQueueRepository.save(batchQueue);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		long endTime = System.currentTimeMillis(); // Capture end time
//	    long duration = endTime - startTime; // Execution time in milliseconds

//	    System.out.println("Batch processing executed in " + startTime + " ms");
	}

}
