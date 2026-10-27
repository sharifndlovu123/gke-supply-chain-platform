package com.example.releases;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReleaseController {

    record Release(String service, String version) {}

    // The pipeline sets GIT_SHA at build time, so you can see which commit is running.
    @Value("${GIT_SHA:local}")
    private String gitSha;

    private final List<Release> releases = new CopyOnWriteArrayList<>();

    @PostMapping("/releases")
    public Release record(@RequestBody Release release) {
        releases.add(release);
        return release;
    }

    @GetMapping("/releases")
    public List<Release> list() {
        return releases;
    }

    @GetMapping("/version")
    public String version() {
        return gitSha;
    }
}
