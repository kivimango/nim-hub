package com.kivimango.nimhub.data;

import java.util.Objects;

public final class TagDto {
    private final String tag;

    private TagDto(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static TagDto of(String tag) {
        return new TagDto(tag);
    }

    static TagDto of(Tag tag) {
        return new TagDto(tag.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto tagDto = (TagDto) o;
        return tag.equals(tagDto.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
