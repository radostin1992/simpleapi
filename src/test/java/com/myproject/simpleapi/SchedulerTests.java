package com.myproject.simpleapi;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.awaitility.Awaitility.await;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.myproject.simpleapi.scheduler.CheckServerStatus;

@SpringBootTest
@ActiveProfiles("tests")
public class SchedulerTests {
    
    @MockitoSpyBean
	CheckServerStatus task;

	@Test
	public void reportCurrentTime() {
		await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
			verify(task, atLeast(1)).reportCurrentTime();
		});
	}
}
