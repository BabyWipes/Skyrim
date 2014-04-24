package com.bobacadodl.JSONChatLib;

/**
 * User: bobacadodl
 * Date: 10/27/13
 * Time: 8:04 PM
 */
public enum JSONChatColor {
    WHITE("white", "f"),
    YELLOW("yellow", "e"),
    LIGHT_PURPLE("light_purple", "d"),
    RED("red", "c"),
    AQUA("aqua", "b"),
    GREEN("green", "a"),
    BLUE("blue", "9"),
    DARK_GRAY("dark_gray", "8"),
    GRAY("gray", "7"),
    GOLD("gold", "6"),
    DARK_PURPLE("dark_purple", "5"),
    DARK_RED("dark_red", "4"),
    DARK_AQUA("dark_aqua", "3"),
    DARK_GREEN("dark_green", "2"),
    DARK_BLUE("dark_blue", "1"),
    BLACK("black", "0");
    private final String color;
    private final String code;

    JSONChatColor(String color, String code) {
        this.color = color;
        this.code = code;
    }

    String getColorString() {
        return color;
    }
    
    public String getColorCode()
    {
    	return this.code;
    }
}
