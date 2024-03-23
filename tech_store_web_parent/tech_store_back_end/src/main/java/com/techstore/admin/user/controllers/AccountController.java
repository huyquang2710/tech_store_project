package com.techstore.admin.user.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techstore.admin.common.utils.FileUploadUtils;
import com.techstore.admin.securities.TechStoreUserDetail;
import com.techstore.admin.user.services.UserService;
import com.techstore.common.entities.User;
import com.techstore.common.utils.Constant;
import com.techstore.common.utils.MessageConstant;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String viewDetail(@AuthenticationPrincipal TechStoreUserDetail techStoreUserDetail, Model model) {

		String email = techStoreUserDetail.getUsername();
		User user = userService.getUserByEmail(email);

		model.addAttribute(Constant.USER, user);

		return "users/account_form";
	}

	@PostMapping("/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal TechStoreUserDetail techStoreUserDetail,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.updateAccount(user);

			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtils.cleanDir(uploadDir);
			FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

		} else {
			if (user.getPhotos().isEmpty())
				user.setPhotos(null);
			userService.updateAccount(user);
		}

		techStoreUserDetail.setFirstName(user.getFirstName());
		techStoreUserDetail.setLastName(user.getLastName());

		redirectAttributes.addFlashAttribute(Constant.MESSAGE, MessageConstant.MESSAGE_YOUR_ACCOUNT_HAVE_BEEN_UPDATED);

		return "redirect:/account";

	}
}
