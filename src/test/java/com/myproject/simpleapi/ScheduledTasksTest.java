package com.myproject.simpleapi;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.awaitility.Awaitility.await;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest
public class ScheduledTasksTest {
    
    @MockitoSpyBean
	ScheduledTasks tasks;

	@Test
	public void reportCurrentTime() {
		await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
			verify(tasks, atLeast(1)).reportCurrentTime();
		});
	}
}
