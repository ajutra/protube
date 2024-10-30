package com.tecnocampus.LS2.protube_back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest({
		"pro_tube.store.dir=c:",
		"spring.datasource.url=jdbc:postgresql://localhost:5432/protube",
		"spring.datasource.username=root",
		"spring.datasource.password=secret"
})
class ProtubeBackApplicationTests {

	@Test
	void contextLoads() {
	}

}
