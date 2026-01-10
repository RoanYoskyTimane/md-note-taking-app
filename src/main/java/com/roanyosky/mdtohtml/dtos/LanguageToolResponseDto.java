package com.roanyosky.mdtohtml.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageToolResponseDto {
    private SoftwareDto software;
    private LanguageDto language;
    private List<MatchDto> matches;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MatchDto {
        private String message;
        private String shortMessage;
        private int offset;
        private int length;
        private List<ReplacementDto> replacements;
        private RuleDto rule;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReplacementDto {
        private String value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RuleDto {
        private String id;
        private String description;
        private CategoryDto category;
    }

    @Data
    public static class CategoryDto {
        private String id;
        private String name;
    }

    @Data
    public static class LanguageDto {
        private String name;
        private String code;
    }

    @Data
    public static class SoftwareDto {
        private String name;
        private String version;
    }
}
