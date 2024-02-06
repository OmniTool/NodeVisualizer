package hex.multinode.visualizer.model.dto;

import jakarta.validation.constraints.NotBlank;

public record NodeDTO(
        String id,
        @NotBlank
        String title,
        String contentText) {
    public static NodeDTO of(String title, String text) {
        return new NodeDTO(null, title, text);
    }

    public static NodeDTO of(String id, String title, String text) {
        return new NodeDTO(id, title, text);
    }

}
