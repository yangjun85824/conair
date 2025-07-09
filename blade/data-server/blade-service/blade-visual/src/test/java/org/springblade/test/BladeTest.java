package org.springblade.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringExtension;
import org.springblade.modules.visual.VisualApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Blade单元测试
 *
 * @author Chill
 */
@ExtendWith(BladeSpringExtension.class)
@SpringBootTest(classes = VisualApplication.class)
@BladeBootTest(appName = "blade-runner", profile = "test", enableLoader = true)
public class BladeTest {

	@Test
	public void contextLoads() {
		System.out.println("test");
	}

}
