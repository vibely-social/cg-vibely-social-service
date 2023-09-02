package com.cg_vibely_social_service.service;

import jakarta.servlet.http.HttpServletRequest;

public interface IPService {
    String getClientIpAddr(HttpServletRequest request);
}
