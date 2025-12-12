package enums;

public enum GlobalObjectProperties {

    DISTRACTED("растеряный"),
    CONCENTRATED("сконцентрированный"),
    HASA("имеет"),
    DONTHASA("не имеет");

    private final String prop;

    private GlobalObjectProperties(String prop) {
        this.prop = prop;
    }

    public String getProp() {
        return this.prop;
    }

}
