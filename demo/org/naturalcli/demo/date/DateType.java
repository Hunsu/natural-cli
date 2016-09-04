package org.naturalcli.demo.date;

public enum DateType {

    UNIX("unix"),
    GREGORIAN("gregorian");

    private String name;

    private DateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DateType from(String value) {
        for (DateType dateType : values())
            if (dateType.getName().equals(value))
                return dateType;
        throw new IllegalArgumentException(String.format("%s was not found", value));
    }

}
