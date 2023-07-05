package com.coolc.colorgen;

import java.awt.Canvas;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import com.coolc.colorgen.Output;

public class Main extends Canvas implements Runnable {	
	private static final long serialVersionUID = 1L; // why do we need this still...
	private Thread t;
	private JFrame fr; // fr 
	private boolean running = false;
	public static final int w_width = 200;
	public static final int w_height = w_width / 4 * 3;
	public static Scanner s;
	public static Random r;
	public static int re = 0; 
	public static int gr = 0;
	public static int b = 0;
	public static int a = 0;
	public static String input, hexval;
	public static int amount;
	public static int storedred, storedgreen, storedblue;

	public Main() {
		Dimension size = new Dimension(w_width * 1, w_height * 1);
		setPreferredSize(size); 
		
		fr = new JFrame();
	}
	
	public static int generateColor() {
		r = new Random();
		return r.nextInt(256);
	}
		
	public static String convertToHex(int c) {
		String hexvalue = Integer.toHexString(c);
		if (hexvalue.equals("0")) {
			hexvalue = "00";
		}
		if (hexvalue.length() == 1) {
			hexvalue = "0" + hexvalue;
		}
		return hexvalue;
	}
	
	public static String makeHexString(String hr, String hg, String hb, String ha) {
		return "#" + hr + hg + hb + ha;
	}
	
	public static String makeHexStringNoAlpha(String hr, String hg, String hb) {
		return "#" + hr + hg + hb;
	}
	
	public synchronized void start() {
		running = true;
		t = new Thread(this, "window");
		t.start();
		s = new Scanner(System.in);
	}
		
	public void run() {
		while (running) {
			update();
			render();
		}
	}
	
	public void update() {
		input = s.nextLine();
		if(input.equals("g")) {
			re = generateColor();
			gr = generateColor();
			b = generateColor();
			a = 255;
			hexval = makeHexStringNoAlpha(convertToHex(re), convertToHex(gr), convertToHex(b));
			Output.printRGB(hexval, re, gr, b);
		} else if(input.equals("gm")) {
			System.out.printf("how many do you want:");
			amount = s.nextInt();
			for (int i = 0; i < amount; ++i) {
			  re = generateColor(); 
			  gr = generateColor(); 
			  b = generateColor(); 
			  a = 255;
			  hexval = makeHexStringNoAlpha(convertToHex(re), convertToHex(gr),
			  convertToHex(b)); 
			  Output.printRGB(hexval, re, gr, b);
			}
		} else if(input.equals("gma")) {
			System.out.printf("how many do you want:");
			amount = s.nextInt();
			for (int i = 0; i < amount; ++i) {
			  re = generateColor(); 
			  gr = generateColor(); 
			  b = generateColor(); 
			  a = generateColor();;
			  hexval = makeHexString(convertToHex(re), convertToHex(gr), convertToHex(b), convertToHex(a)); 
			  Output.printRGBA(hexval, re, gr, b, a);
			}
		} else if(input.equals("ga")) {
			re = generateColor();
			gr = generateColor();
			b = generateColor();
			a = generateColor();
			hexval = makeHexString(convertToHex(re), convertToHex(gr), convertToHex(b), convertToHex(a));
			Output.printRGBA(hexval, re, gr, b, a);
		} else if(input.equals("a")) {
			a = generateColor();
			Output.printA(a);
		} else if(input.equals("d")) {
			System.out.printf("debug:\nv 1.1\ngc %s\nga %d\n\n", hexval, a);
		} else if (input.equals("exit")) {
			Output.printExit();
			fr.dispose();
			System.exit(0);
		} else if(input.equals("?")) {
			re = 0x1d;
			gr = 0x00;
			b = 0x58;
			a = 100;
			Output.printHelp();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
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
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String [] args) {
		ImageIcon ico = new ImageIcon("/icon.png");
		Output.printStart();
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
		m.fr.setIconImage(ico.getImage());
		m.start();
	}
}