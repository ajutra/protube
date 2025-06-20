package com.tecnocampus.LS2.protube_back;

import com.tecnocampus.LS2.protube_back.domain.processor.VideoProcessor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final Environment env;
    private final VideoProcessor videoProcessor;

    @Override
    public void run(ApplicationArguments args) {
        // Should your backend perform any task during the bootstrap, do it here
    }

    @PostConstruct
    public void init() {
        // Ensure that the tables are created before executing the loadInitialData method
        // This is necessary because the loadInitialData method uses the tables
        if (Boolean.TRUE.equals(env.getProperty("pro_tube.load_initial_data", Boolean.class))) {
            loadInitialData();
        }
    }

    private void loadInitialData() {
        // Load initial data here
        final var rootDir = env.getProperty("pro_tube.store.dir");
        assert rootDir != null;
        videoProcessor.processVideosFrom(rootDir);
    }
}
