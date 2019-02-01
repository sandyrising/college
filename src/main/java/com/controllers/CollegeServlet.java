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
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.models.Response;
import com.models.Student;

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

		ResponseEntity<String> entityResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		String result = entityResponse.getBody();

		Response res = gson.fromJson(result, Response.class);
		if (res.getErrorCode().equals("003")) {
			model.addAttribute("message",
					"Either you are trying with same student id or our database is down!! Please check and try after sometime!!");
		} else {
			model.addAttribute("message", res.getErrorMessage());			
		}
		Student student = gson.fromJson(res.getResponseData(), Student.class);

		System.out.println(result);
		return "Home";
	}
}
