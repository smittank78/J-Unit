package com.junit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JUnitApplicationTests {
	/*
	 * J-Unit : 

its used for test the application or service.
you can ensure that your logic works as expected.
you can create j-unit test class into test folder and execute the tests.

instead of call the api and check the output manually you can create tests and that will take all responsibilities of output checking
you can test multiple services at once.
in future if any developer is implementing something in the application then we will notified 
	if due to implementation any service not sending response as expected  

sevral annotation are there which provided by j-unit jupiter that helps you to build rapid tests.

JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage

JUnit Platform : it executes our tests on platform/IDE/JVM
JUnit Jupiter -> provides test-engine to write the tests and its provides anotation required for testing
JUnit Vintage : it provides test-engine to run test which written in j-unit3 or j-unit4.

	 */
	
	@Test
	void contextLoads() {
	}

}
