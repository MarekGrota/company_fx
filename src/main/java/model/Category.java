package model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    karoseria("karoseria"),
    zawieszenie("zawieszenie"),
    silnik("silnik");

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }
}
