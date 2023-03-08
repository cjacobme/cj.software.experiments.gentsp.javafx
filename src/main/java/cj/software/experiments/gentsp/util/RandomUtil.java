package cj.software.experiments.gentsp.util;

import org.springframework.stereotype.Service;

@Service
public class RandomUtil {
    public double nextRandomValue() {
        return Math.random();
    }
}
