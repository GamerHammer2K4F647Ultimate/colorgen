package com.coolc.colorgen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	public static final int w_width = 200;
	public static final int w_height = w_width / 4 * 3;
	public static Scanner s;
	
	private static final long serialVersionUID = 1L; // why do we need this still...
	private Thread t;
	private JFrame fr; // fr 
	private boolean running = false;
	
	public Main() {
		Dimension size = new Dimension(w_width * 1, w_height * 1);
		setPreferredSize(size); 
		
		fr = new JFrame();
	}
	
	public static Random r;
//	public int red, green, blue;
	public String hred, hgreen, hblue;
	
	public static int generateColor() {
		r = new Random();
		return r.nextInt(256);
	}
		
	public static String convertToHex(int c) {
		String hexvalue = Integer.toHexString(c);
		if (hexvalue.equals("0")) {
			hexvalue = "00";
		}
		if (hexvalue.length() != 2) {
			hexvalue.concat("0");
		}
		return hexvalue;
	}
	
	public static String makeHexString(String hr, String hg, String hb, String ha) {
		return "#" + hr + hg + hb + ha;
	}
	
	public static String makeHexStringNoAlpha(String hr, String hg, String hb) {
		return "#" + hr + hg + hb;
	}
	
	public static int re = 0; 
	public static int gr = 0;
	public static int b = 0;
	public static int a = 0;
	
	public synchronized void start() {
		running = true;
		t = new Thread(this, "window");
		t.start();
		s = new Scanner(System.in);
	}
	
	public static String input, hexval;
	public static int howManyToGenerate;
	public static int storedred, storedgreen, storedblue;
	
	public void run() {
		while (running) {
			update();
			render();
			input = s.nextLine();		
			
			if(input.equals("g")) {
				re = generateColor();
				gr = generateColor();
				b = generateColor();
				a = 255;
				hexval = makeHexStringNoAlpha(convertToHex(re), convertToHex(gr), convertToHex(b));
				System.out.printf("hex value: %s\n", hexval);
				System.out.printf("rgb value: %d, %d, %d\n", re, gr, b);
			} else if(input.equals("gm")) {
				System.out.printf("how many do you want:");
				howManyToGenerate = s.nextInt();
				for (int i = 0; i < howManyToGenerate; ++i) {
				  re = generateColor(); 
				  gr = generateColor(); 
				  b = generateColor(); 
				  a = 255;
				  hexval = makeHexStringNoAlpha(convertToHex(re), convertToHex(gr),
				  convertToHex(b)); 
				  System.out.printf("hex value: %s\n", hexval);
				  System.out.printf("rgb value: %d, %d, %d\n", re, gr, b);
				}
			} else if(input.equals("gma")) {
				System.out.printf("how many do you want:");
				howManyToGenerate = s.nextInt();
				for (int i = 0; i < howManyToGenerate; ++i) {
				  re = generateColor(); 
				  gr = generateColor(); 
				  b = generateColor(); 
				  a = generateColor();;
				  hexval = makeHexString(convertToHex(re), convertToHex(gr), convertToHex(b), convertToHex(a)); 
				  System.out.printf("hex value: %s\n", hexval);
				  System.out.printf("rgba value: %d, %d, %d, %d\n", re, gr, b, a);
				}
			} else if(input.equals("ga")) {
				re = generateColor();
				gr = generateColor();
				b = generateColor();
				a = generateColor();
				hexval = makeHexString(convertToHex(re), convertToHex(gr), convertToHex(b), convertToHex(a));
				System.out.printf("hex value: %s\n", hexval);
				System.out.printf("rgba value: %d, %d, %d, %d\n", re, gr, b, a);
			} else if(input.equals("a")) {
				a = generateColor();
				System.out.printf("alpha value: %d\n", a);
			} else if(input.equals("d")) {
				a = generateColor();
				System.out.printf("debug:\nv 1.1\ngc %s\nga %d\n\n", hexval, a);
			} else if (input.equals("exit")) {
				fr.dispose();
				System.exit(0);
			} else if(input.equals("?")) {
				re = 0x1d;
				gr = 0x00;
				b = 0x58;
				a = 100;
				System.out.printf("g    generates a new color\n");
				System.out.printf("ga   generates a new color with an alpha value\n");
				System.out.printf("gm   generates more than one color\n");
				System.out.printf("gma  generates more than one color with an alpha value\n");
				System.out.printf("a    generates an alpha value\n");
				System.out.printf("d    debugger\n");
				System.out.printf("exit exits the program\n");
				System.out.printf("?    help\n");
			}
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy(); // bs = bullshit ????
		if(bs == null) {
			createBufferStrategy(3); // because triple buffer
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Color c = new Color(re, gr, b, a);
		g.setColor(c);
		g.fillRect(0, 0, w_width, w_height);
		g.dispose();
		bs.show();
	}
	
	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			t.join();
		} catch (InterruptedException e) {
			System.out.println("error\n");
			e.printStackTrace();
			System.exit(1);
		}
	}
	

	
	public static void main(String [] args) {
		System.out.println("color generator v1.1");
		System.out.println("? for help");
		a = 255;
		
		s = new Scanner(System.in);
		Main m = new Main();
		m.fr.setResizable(false);
		m.fr.setTitle("color generator");
		m.fr.add(m);
		m.fr.pack();
		m.fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.fr.setLocationRelativeTo(null);
		m.fr.setVisible(true);
		
		m.start();
		
	}
}
