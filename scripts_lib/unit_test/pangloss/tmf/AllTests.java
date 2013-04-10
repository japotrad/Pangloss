package pangloss.tmf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestConfig.class, TestGis.class, TestIu.class, TestLs.class,
		TestRel.class, TestSn.class, TestTdc.class, TestTe.class, TestTs.class })
public class AllTests {

}
