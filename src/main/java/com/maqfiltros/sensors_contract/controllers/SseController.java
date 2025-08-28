package com.maqfiltros.sensors_contract.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.maqfiltros.sensors_contract.sse.SseService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sse")
public class SseController {
    private final SseService sseService;

    public SseController(SseService sseService) {
        this.sseService = sseService;
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribe(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        return sseService.subscribe(clientIp);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For"); // Para casos de proxy/reverso
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
