package com.coolc.colorgen;

public class Output {
	public static void printRGB(String h, int red, int green, int blue) {
		System.out.printf("hex value: %s\n", h);
		System.out.printf("rgb value: %d, %d, %d\n", red, green, blue);
	}
	
	public static void printRGBA(String h, int red, int green, int blue, int alpha) {
		  System.out.printf("hex value: %s\n", h);
		  System.out.printf("rgba value: %d, %d, %d, %d\n", red, green, blue, alpha);
	}
	
	public static void printA(int alpha) {
		System.out.printf("alpha value: %d\n", alpha);
	}
	
	public static void printHelp() {
		System.out.printf("g    generates a new color\n");
		System.out.printf("ga   generates a new color with an alpha value\n");
		System.out.printf("gm   generates more than one color\n");
		System.out.printf("gma  generates more than one color with an alpha value\n");
		System.out.printf("a    generates an alpha value\n");
		System.out.printf("d    debugger\n");
		System.out.printf("exit exits the program\n");
		System.out.printf("?    help\n");
	}
	
	public static void printStart() {
		System.out.println("color generator v1.2");
		System.out.println("? for help");
	}
	
	public static void printExit() {
		System.out.println("exiting...");
	}
}
