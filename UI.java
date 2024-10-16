package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;


public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; //0: first screen, 1: second screen
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		}catch(FontFormatException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//hud
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		//g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(purisaB);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPausedScreen();
		}
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
	}
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//maxlife
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+= gp.tileSize;
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			g2.setColor(new Color(30,30,30));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 125F));
			String text = "Jujutsu Island";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			Color back = new Color(110,10,10);
			Color front = new Color(255,255,255);
			
			g2.setColor(back);
			g2.drawString(text, x+5, y+5);
			
			g2.setColor(front);
			g2.drawString(text, x, y);
			
			
			x = gp.screenWidth/2 - (gp.tileSize*4)/2;
			y += gp.tileSize*1;
			g2.drawImage(gp.player.left2, x, y, gp.tileSize*4,gp.tileSize*4, null);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,36F));
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*5;
			g2.setColor(back);
			g2.drawString(text, x+3, y+3);
			g2.setColor(front);
			g2.drawString(text, x, y);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
			if(commandNum  == 0) {
				g2.drawString("  >", x-gp.tileSize, y+1);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,36F));
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.setColor(back);
			g2.drawString(text, x+3, y+3);
			g2.setColor(front);
			g2.drawString(text, x, y);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
			if(commandNum  == 1) {
				g2.drawString("  >", x-gp.tileSize, y+1);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,36F));
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.setColor(back);
			g2.drawString(text, x+3, y+3);
			g2.setColor(front);
			g2.drawString(text, x, y);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
			if(commandNum  == 2) {
				g2.drawString("  >", x-gp.tileSize, y+1);
			}
		}
		
		else if(titleScreenState == 1) {
			
			g2.setColor(Color.white);
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			
			String text = "Select your character!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Gojo";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Megumi";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Yuji";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y+gp.tileSize);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y+gp.tileSize);
			}
		}
		
	}
	public void drawPausedScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSE";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
		
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}

}
