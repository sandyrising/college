package com.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.models.ExcecptionResponse;
import com.models.Student;
import com.models.Teacher;

@Controller
public class CollegeServlet {

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String addStudent(Model model, Student std) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://192.168.0.15:8080/crick-app/college/saveStudent";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Gson gson = new Gson();
		String body = gson.toJson(std);

		HttpEntity<String> entity = new HttpEntity<String>(body, headers);

		ResponseEntity<String> entityResponse;
		try {
			entityResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (HttpServerErrorException httpExcep) {
			String exceptionBody = httpExcep.getResponseBodyAsString();
			System.out.println("Exception body : " + exceptionBody);
			ExcecptionResponse expResponse = gson.fromJson(exceptionBody, ExcecptionResponse.class);
			if (expResponse.getErrorCode().equals("003")) {
				model.addAttribute("failureMsg",
						"Either you are using same student id or our database is down!! Please check and try after some time!!");
			} else {
				model.addAttribute("failureMsg", expResponse.getErrorMessage());				
			}
			return "Home";
		}

		String result = entityResponse.getBody();

		System.out.println("Response from addStudent service : " + result);
		Student student = gson.fromJson(result, Student.class);
		System.out.println("Student name is : " + student.getName());
		model.addAttribute("successMsg", "Student added successfully!!");
		return "Home";
	}
	
	@RequestMapping(value="/addTeacher", method=RequestMethod.POST)
	public String addTeacher(Model model, Teacher t) {
		
		
		RestTemplate restTemplate =  new RestTemplate();
		
		String url = "http://192.168.0.15:8080/crick-app/college/saveTeacher";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Gson gson = new Gson();
		String body = gson.toJson(t);
		System.out.println("requesting json object : " + body);
		System.out.println("Requesting service url : " + url);
		
		HttpEntity<String> entity =  new HttpEntity<String>(body,headers);
		
		ResponseEntity<String> exchangeResponse;
		try {
			exchangeResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (HttpServerErrorException  httpExp) {
			String exceptionBody = httpExp.getResponseBodyAsString();
			ExcecptionResponse exceptionRes = gson.fromJson(exceptionBody, ExcecptionResponse.class);
			if(exceptionRes.getErrorCode().equals("003")) {
				model.addAttribute("failureMsg", "Either you are using same teacher id or our database is down!! Please check and try after some time");
			} else {
				model.addAttribute("failureMsg", exceptionRes.getErrorMessage());
			}
			return "Home";
		}
		String responseBody = exchangeResponse.getBody();
		System.out.println("Response body : " + responseBody);
		Teacher teacher = gson.fromJson(responseBody, Teacher.class);
		model.addAttribute("successMsg", "Teacher added successfully!!");
		return "Home";
	}
}
