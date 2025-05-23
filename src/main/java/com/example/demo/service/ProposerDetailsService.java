package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ProposerDetailsEntity;
import com.example.demo.pagination.ProposerPagination;
import com.example.demo.request.RequestDto;
import com.example.demo.request.RequiredDto;
import com.example.demo.request.UserDto;

import jakarta.servlet.http.HttpServletResponse;

public interface ProposerDetailsService {
	
	public String saveProposer(RequestDto requestDto);
	
	public List<ProposerDetailsEntity> getAll(ProposerPagination pagination);
	
	public String update(Integer id, RequestDto requestDto);
	
	public String delete(Integer id);
	
	public List<ProposerDetailsEntity> getAllActiveProposers();
	
	public RequiredDto fetchProposerById(Integer id);
	
	public Integer fetchTotalRecord();
	
	public List<ProposerDetailsEntity> fetchAllByStringBuilder(ProposerPagination pagination);
	
	public String getDataInExcel() throws IOException  ;
	
	public String importExcel(MultipartFile file) throws IOException; 
	
	public String queueExcel(MultipartFile file) throws IOException;
	
	public void batchProcessing() throws FileNotFoundException;
	
//	public String saveUser(UserDto userDto);
//	
//	public String updateUser(Integer id, UserDto userDto);

}
