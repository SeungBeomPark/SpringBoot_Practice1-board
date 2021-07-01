package com.board;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @category SpringBoot UnitTest 
 * @since 2021. 7. 1
 * @author 박승범
 * @History
 * @1. 2021.7.1 박승범 sql 연결 테스트 코드 작성
 *
 */
@SpringBootTest
class BoardApplicationTests {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private SqlSessionFactory sessionFactory;
	@Test
	void contextLoads() {
	}
	//ApplicationContext 객체 단위테스트
	@Test
	public void testByApplicationContext() {
		try {
			System.out.println("======");
			System.out.println(context.getBean("sqlSessionFactory"));
			System.out.println("======");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// SqlSessionFactory 객체 단위테스트
	@Test
	public void testBySqlSessionFactory() {
		try {
			System.out.println("======");
			System.out.println(sessionFactory.toString());
			System.out.println("======");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
