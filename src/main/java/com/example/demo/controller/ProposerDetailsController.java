package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ProposerDetailsEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.pagination.ProposerPagination;
import com.example.demo.pagination.Searching;
import com.example.demo.request.RequestDto;
import com.example.demo.request.RequiredDto;
import com.example.demo.request.UserDto;
import com.example.demo.response.ResponseHandler;
import com.example.demo.service.ProposerDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/proposer")
public class ProposerDetailsController {
	
	@Autowired
	private ProposerDetailsService service;
	
	@PostMapping("/add")
	public ResponseHandler createProposer(@RequestBody RequestDto requestDto){
		
		ResponseHandler response = new ResponseHandler();
		
		try {
			
			String add = service.saveProposer(requestDto);
			
				response.setData(add);
				response.setStatus(true);
				response.setMessage("Success");
		
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage("failed");
		}		
		
		return response;
	}
	
	@PostMapping("/listing")
	public ResponseHandler allProposals(@RequestBody ProposerPagination proposerPagination){
		
		ResponseHandler response = new ResponseHandler();
		
		try {
			//List<ProposerDetailsEntity> listOfProposer = service.getAll(proposerPagination);
			List<ProposerDetailsEntity> listOfProposer = service.fetchAllByStringBuilder(proposerPagination);
			response.setData(listOfProposer);
			response.setStatus(true);
			response.setMessage("Success");
			Searching searching = proposerPagination.getSearching();
			if (searching != null || !searching.getProposerFirstName().isEmpty()
					|| !searching.getProposerLastName().isEmpty() || !searching.getCity().isEmpty()
					|| !searching.getProposerMobileNo().toString().isEmpty()) 
			{
				response.setTotlaRecord(listOfProposer.size());
			} 
			else
			{
				response.setTotlaRecord(service.fetchTotalRecord());
			}
			
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage("failed");
		}
		
		return response;		
	}
	
	@PutMapping("/update/{id}")
	public ResponseHandler update(@PathVariable Integer id, @RequestBody RequestDto requestDto){
		
		ResponseHandler response = new ResponseHandler();
		
		try {
			String updated = service.update(id, requestDto);
			response.setData(updated);
			response.setStatus(true);
			response.setMessage("Success");
			
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage("failed");
		}
		
		return response;
		
	}
	
	@PatchMapping("/delete/{id}")
	public ResponseHandler delete(@PathVariable Integer id){
		
		ResponseHandler response = new ResponseHandler();
		
		try {
			
			String deleted = service.delete(id);
			response.setData(deleted);
			response.setStatus(true);
			response.setMessage("Success");
			
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage("failed");
			
		}
		
		return response;
		
	}
	
	@GetMapping("/list")
	public ResponseHandler getAllActive(){
		
		ResponseHandler handler = new ResponseHandler();
		
		try {
			
			List<ProposerDetailsEntity> list = service.getAllActiveProposers();
			handler.setData(list);
			handler.setStatus(true);
			handler.setMessage("Success");
			
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMessage("failure");
		}
		
		return handler;
	}
	
	@GetMapping("/get-by-id/{id}")
	public ResponseHandler getProposerById(@PathVariable Integer id) {
		
		ResponseHandler response = new ResponseHandler();
		
		try {
			
			RequiredDto dto = service.fetchProposerById(id);
			response.setData(dto);
			response.setStatus(true);
			response.setMessage("Success");
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setData(new ArrayList<>());
			response.setStatus(false);
			response.setMessage("failed");
		}
		
		return response;
	}
	
	@GetMapping("/export")
	public ResponseHandler generatedExcel() throws IOException {

		// response.setContentType("application/octet-stream");
//		response.setHeader("Content-Disposition", "attachment;filename=proposer.xlsx");

		ResponseHandler handler = new ResponseHandler();

		try {

			String fileUrl = service.getDataInExcel();

			handler.setData(fileUrl);
			handler.setStatus(true);
			handler.setMessage("Success");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMessage("failed");
		}

		return handler;
	}

	@PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseHandler importExcel(@RequestParam MultipartFile file) {

		ResponseHandler handler = new ResponseHandler();

		try {

			String message = service.queueExcel(file);

			handler.setData(message);
			handler.setStatus(true);
			handler.setMessage("Success");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMessage("failed");
		}

		return handler;
	}
	
//	
//	@PostMapping("/add-user")
//	public ResponseHandler addUser(@RequestBody UserDto userDto){
//		
//		ResponseHandler response = new ResponseHandler();
//		
//		try {
//			
//			String add = service.saveUser(userDto);
//			
//				response.setData(add);
//				response.setStatus(true);
//				response.setMessage("Success");
//		
//		}
//		catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			response.setData(new ArrayList<>());
//			response.setStatus(false);
//			response.setMessage(e.getMessage());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			response.setData(new ArrayList<>());
//			response.setStatus(false);
//			response.setMessage("failed");
//		}		
//		
//		return response;
//	}
//	
//	
//	@PutMapping("/update-user/{id}")
//	public ResponseHandler updateUser(@PathVariable Integer id, @RequestBody UserDto userDto){
//		
//		ResponseHandler response = new ResponseHandler();
//		
//		try {
//			String updated = service.updateUser(id, userDto);
//			response.setData(updated);
//			response.setStatus(true);
//			response.setMessage("Success");
//			
//		}
//		catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			response.setData(new ArrayList<>());
//			response.setStatus(false);
//			response.setMessage(e.getMessage());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			response.setData(new ArrayList<>());
//			response.setStatus(false);
//			response.setMessage("failed");
//		}
//		
//		return response;
//		
//	}

}
