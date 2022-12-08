package com.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.service.Calculator;
import com.junit.service.Multiply;

/*
 * @author Smit Tank
 * 
 * @TestInstance(Lifecycle.PER_CLASS)  : 1 instance / test-class
 * 									   : @BeforeAll and  @AfterAll no need static
 * @TestInstance(Lifecycle.PER_METHOD) : 1 instance / per test
 * 									   : @BeforeAll and  @AfterAll must be static methods
 * @EnabledOnJre(value = {JRE.JAVA_10} : execute test-Class if JRE is under specified versions
 * @EnabledForJreRange(max = JRE.JAVA_20 , min = JRE.JAVA_8) : within JRE version range
 * @EnabledOnOs(value = {OS.LINUX,OS.MAC})  : execute test-Class if OS matches
 * @ExtendWith(MockitoExtension.class) : if you are using @Mock anywhere in test
 */
@TestInstance(Lifecycle.PER_CLASS)
@EnabledOnJre(value = {JRE.JAVA_10,JRE.JAVA_11,JRE.JAVA_12,JRE.JAVA_17,JRE.JAVA_18,JRE.JAVA_19})
@EnabledOnOs(value = {OS.LINUX,OS.MAC,OS.WINDOWS})
@DisplayName("Calculator Test")
@ExtendWith(MockitoExtension.class)
class TestCalculator 
{
	static Calculator calculator;
	static Calculator calculatorForMultiply;
	/*
	 * @Mock : if method that you are calling is depending on any other service which is 
	 * 		   present on serever and not started yet or in case functionality not build yet 
	 * 		   then you can create mock object and manually pass response 
	 * 		   using when().thenReturn() in test
	 * 
	 * 	when(multiply.mul(parameters)).thenReturn(Response Value);
	 */
	@Mock
	Multiply multiply; //= mock(Multiply.class);
	/**
	 * @BeforeAll : executes method only 1 time before execution of first test
	 * 			  : can perform class level start-up activity here
	 *            : like login check,initialization,...
	 *            : if creating instance / class then static not mendatory
	 *            : if creating instance / test then must be static 
	 */
	@BeforeAll
	//static void setUpBeforeClass() {
	void setUpBeforeClass() 
	{
		System.out.println("-> before all");
		System.out.println("intializing calculator object from beofre all");
		calculator = new Calculator();
	}
	/**
	 * @BeforeEach : execute before every test
	 * 			   : can perform activities which is similar for every method
	 * 			   : like token verification,...
	 */
	@BeforeEach
	void setUp(){
		System.out.println("---------------------------------------");
		System.out.println("--> before each");		
	}
	@Test
	@DisplayName("Test Fail Executing")
	void test() {
		System.out.println("---> first test execution...");
		fail("Not yet implemented");	
	}
	/*
	 * @DisplayName : display redable info about which test is running
	 * @ParameterizedTest : test needs parameters
	 * @ValueSource(ints = { 11,2,3 }) : if only one parameter
	 * @CsvSource({"1,2","2,3","3,4"}) : if multiple parameters
	 */
	@DisplayName("Test Sum Executing")
	@ParameterizedTest
	@CsvSource({"1,2","2,3","3,4"})
	void sum(int a,int b) {
		System.out.println("test : sum()");
		calculator.sum(a, b);
	}
	@ParameterizedTest
	@ValueSource(strings = "Dev")
	@DisplayName("Test Environment Check Executing")
	void checkEnvironment(String environment) {
		/*
		 * we can write environment or server specific code 
		 * it will check and execute if matches
		 */
		assumingThat(environment.equals(new String("Dev")), () -> System.out.println("Dev environment"));
		System.out.println("test : checkEnvironment()");
	}
	/*
	 * @RepeatedTest(n) : can execute test for number of times
	 * 					: TestInfo is an interface that provide information about the test
	 * 					: TestReporter to add entry in test
	 *  			    : no need to write @Test
	 */
	static int repeat = 0;
	@RepeatedTest(2)
	@DisplayName("Test Sub Executing")
	void sub(TestInfo info,TestReporter reporter) 
	{
        reporter.publishEntry("executed : " + repeat + " time");
        System.out.println("executed : " + repeat + " time");
		repeat++;
		System.out.println("test : sub()");
		calculator.sub(1, repeat);
	}
	@Test
	@DisplayName("Test Mul Executing")
	void mul() 
	{
		System.out.println("test : mul()");
		calculatorForMultiply = new Calculator(multiply);
		when(multiply.mul(1, 2)).thenReturn(2);
		calculatorForMultiply.mul(1,2);
	}
	/*
	 * we can make group of similar test
	 * like here we have 2 assert test then we are grouping them and 
	 * anotated with @Nested
	 */
	@Nested
	@DisplayName("Assert Tests")
	class asert {
		@Test
		@DisplayName("Test Assert1 Executing")
		void asert1() {
			assertTrue(calculator.check(true));
			assertFalse(calculator.check(false));
		}

		@Test
		@DisplayName("Test Assert2 Executing")
		void asert2() {
			assertEquals(5, calculator.div(10, 2));
			/*
			 * assertThrows(Exception.class,() -> calculator.div(1, 0)); -> method must be
			 * throwing specified exception else test fail -> make sure that you are using
			 * lambda function
			 */
			assertThrows(ArithmeticException.class, () -> calculator.div(1, 0));
			System.out.println("test : div()");
			/*
			 * assertAll( () -> assert statement ) you can write multiple asserts
			 */
			assertAll(() -> assertEquals(5, calculator.div(10, 2)),
					() -> assertEquals(1, calculator.div(10, 1), "divided by 1 not 10 - smit"),
					() -> assertEquals(2, calculator.div(10, 5)));
		}
	}
	@Test
	@DisplayName("Test Manually Disabled")
	@Disabled
	void disableTest(){
		System.out.println("disable");	
			}
	/**
	 * @AfterEach : execute after every test
	 * 			  : can perform class level clean-up activity here
	 */
	@AfterEach
	void tearDown(){
		System.out.println("--> after each");	
			}
	/**
	 * @AfterAll : executes method only 1 time after execution of last test
	 * 			 : can perform class level clean-up activity here
	 *            : if creating instance / class then static not mendatory
	 *            : if creating instance / test then must be static 
	 */
	@AfterAll
//	static void tearDownAfterClass() {
	void tearDownAfterClass() {
			System.out.println("---------------------------------------");
		System.out.println("-> after all");
		calculator = null;
		System.out.println("destroying calculator object from after all");
	}
}