package com.techstore.common.utils;

import com.techstore.common.entities.User;

public class DirectUtil {
	public static String getRedirectURLtoAffectedUser(User user) {
		
		String firstPartOfEmail = user.getEmail().split("@")[0];
		
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
	}
}
