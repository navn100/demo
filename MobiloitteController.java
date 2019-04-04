package com.example.demo.mobiloittecontroller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.utils.ObjectUtils;

import com.example.demo.mobiloitteservice.MobiloitteService;
import com.example.demo.util.CloudinaryConfig;

@RestController
public class MobiloitteController {

	@Autowired
	MobiloitteService ms;
	@Autowired
	CloudinaryConfig cloudc;

	@PostMapping("/sendmail")
	public String mail() {

		return ms.sendMail();
	}

	@PostMapping("/greet")
	public String greeting() {

		return ms.sendSms();
	}

	@PostMapping("/upload")
	public String singleImageUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			Model model) {
		if (file.isEmpty()) {
			model.addAttribute("message", "Please select a file to upload");
			return "upload";
		}
		try {
			Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
			model.addAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
			model.addAttribute("imageurl", uploadResult.get("url"));
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("message", "Sorry I can't upload that!");
		}
		return "upload";
	}
	

	@PostMapping("/Demo")
	public String demo( @RequestBody String hello)
	{
		String speech;
		switch(hello)
		{
		case "hi": speech	="hello , Nice to meet you";
		break;
		
		case "bye" : speech="Bye, Goodnight";
		break;
		
		case "anything" : speech="Yes, You Can";
		break;
		
		default :
			speech="Sorry";
		}
		
		return speech;
	}

/*	@RequestMapping(path = "/employees")
	public class EmployeeController {
		

		@GetMapping(path = "/", produces = "application/json")
		public Employees getEmployees() {
			return employeeDao.getAllEmployees();
		}
	}*/

}
