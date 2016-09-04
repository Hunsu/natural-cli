package org.naturalcli;

public interface Converter<T> {

    T convert(String... args);

}
