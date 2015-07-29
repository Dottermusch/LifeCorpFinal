package dataTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
{ 	DemoAdminDBTest.class, 
	DemoCustomerDBTest.class, 
	DemoOrderDBTest.class,
	DemoProductInfoTest.class 
})


public class AllTests
{

}
