package model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    KAROSERIA("karoseria"),
    ZAWIESZENIE("zawieszenie"),
    SILNIK("silnik");

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }
}
