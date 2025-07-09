package org.springblade.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringExtension;

/**
 * Blade单元测试
 *
 * @author Chill
 */
@ExtendWith(BladeSpringExtension.class)
@BladeBootTest(appName = "blade-runner", profile = "test")
public class BladeTest {

	@Test
	public void contextLoads() {
		System.out.println("test");
	}

}
