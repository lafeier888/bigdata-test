package enumtest;

public enum ColorEnum {
    Red("red"),
    Green("green");

    private String color;

    private ColorEnum(String title) {
        this.color = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
