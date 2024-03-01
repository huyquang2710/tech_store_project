package com.techstore.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncodePass() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String beforeEncode = "huyquang1";
		String afterEncode = passwordEncoder.encode(beforeEncode);

		System.out.println(afterEncode);

		boolean matches = passwordEncoder.matches(beforeEncode, afterEncode);

		assertThat(matches).isTrue();
	}
}
