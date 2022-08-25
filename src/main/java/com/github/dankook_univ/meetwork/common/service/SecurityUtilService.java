package com.github.dankook_univ.meetwork.common.service;

import org.springframework.stereotype.Service;

@Service
public class SecurityUtilService {

    public String protectInputValue(String value) {
        if (value == null) {
            return null;
        }

        return value
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll("\"", "&quot;")
            .replaceAll("'", "&#x27;")
            .replaceAll("/", "&#x2F;")
            .replaceAll("\\(", "&#x28;")
            .replaceAll("\\)", "&#x29;");
    }
}
