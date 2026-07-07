package ru.one.stream.internetsercher.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResult {
    private String title;
    private String url;
    private boolean isValid;
    private boolean corsSupported;

    public ValidationResult(String title, String url, boolean isValid, boolean corsSupported) {
        this.title = title;
        this.url = url;
        this.isValid = isValid;
        this.corsSupported = corsSupported;
    }

    public ValidationResult(String originalUrl, boolean isValid) {
        this.url = originalUrl;
        this.isValid = isValid;
    }

    public static ValidationResult invalid(String url) {
        return new ValidationResult(url, false);
    }

}


