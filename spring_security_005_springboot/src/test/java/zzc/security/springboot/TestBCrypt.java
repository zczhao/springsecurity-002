package zzc.security.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestBCrypt {

	@Test
	public void testBCrypt() {
		// 对密码进行加密
		String hashpw = BCrypt.hashpw("123456", BCrypt.gensalt());
		System.out.println(hashpw);
		
		System.out.println(BCrypt.hashpw("secret", BCrypt.gensalt()));

		// 校验密码
		boolean checkpw = BCrypt.checkpw("123456", "$2a$10$Y6oMzwqbNyF8pYGaXbMPVeNCD1.LrwS4ZxQ0jl9BzFrp9q6NG.s42");
		System.out.println("checkpw=" + checkpw);
		
	}
}
