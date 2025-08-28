package com.maqfiltros.sensors_contract.sse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class SseService {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String clientIp) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Timeout infinito

        emitter.onCompletion(() -> {
            emitters.remove(clientIp);
        });

        emitter.onTimeout(() -> {
            emitters.remove(clientIp);
            emitter.complete();
        });

        emitter.onError((e) -> {
            emitters.remove(clientIp);
            emitter.complete();
        });

        emitters.put(clientIp, emitter);
        return emitter;
    }

    public void sendEvent(String eventData) {
        for (String clientIp : emitters.keySet()) {
            SseEmitter emitter = emitters.get(clientIp);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().data(eventData));
                } catch (IOException e) {
                    emitter.complete();
                    emitters.remove(clientIp);
                }
            }
        }
    }

    // 🔄 Envia um keep-alive a cada 30 segundos para evitar timeout
    @Scheduled(fixedRate = 30_000)
    public void sendHeartbeat() {
        for (String clientIp : emitters.keySet()) {
            SseEmitter emitter = emitters.get(clientIp);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().comment("keep-alive"));
                } catch (IOException e) {
                    emitter.complete();
                    emitters.remove(clientIp);
                }
            }
        }
    }
}
