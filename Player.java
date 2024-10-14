package entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	Graphics2D g2;

	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	public int choice;

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		this.keyH = keyH;

		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;

		attackArea.width = 40;
		attackArea.height = 40;
		
		setDefaultValues();
		getPlayerImage(choice);
		getPlayerAttackImage();
	}
	public void setDefaultValues() {

		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";

		//player stats
		maxLife = 6;
		life = maxLife;
	}
	public void getPlayerImage(int choice) {

		switch(choice) {
		case 0:
			up1 = setup("/player/gojo_up1",gp.tileSize,gp.tileSize);
			up2 = setup("/player/gojo_up2",gp.tileSize,gp.tileSize);
			down1 = setup("/player/gojo_down1",gp.tileSize,gp.tileSize);
			down2 = setup("/player/gojo_down2",gp.tileSize,gp.tileSize);
			left1 = setup("/player/gojo_left1",gp.tileSize,gp.tileSize);
			left2 = setup("/player/gojo_left2",gp.tileSize,gp.tileSize);
			right1 = setup("/player/gojo_right1",gp.tileSize,gp.tileSize);
			right2 = setup("/player/gojo_right2",gp.tileSize,gp.tileSize);
			break;
		case 1:
			up1 = setup("/player/megumi_up1",gp.tileSize,gp.tileSize);
			up2 = setup("/player/megumi_up2",gp.tileSize,gp.tileSize);
			down1 = setup("/player/megumi_down1",gp.tileSize,gp.tileSize);
			down2 = setup("/player/megumi_down2",gp.tileSize,gp.tileSize);
			left1 = setup("/player/megumi_left1",gp.tileSize,gp.tileSize);
			left2 = setup("/player/megumi_left2",gp.tileSize,gp.tileSize);
			right1 = setup("/player/megumi_right1",gp.tileSize,gp.tileSize);
			right2 = setup("/player/megumi_right2",gp.tileSize,gp.tileSize);
			break;
		}
	}

	public void getPlayerAttackImage() {

		attackUp1 = setup("/player/blue_up1",gp.tileSize,gp.tileSize*2);
		attackUp2 = setup("/player/blue_up2",gp.tileSize,gp.tileSize*2);
		attackDown1 = setup("/player/blue_down1",gp.tileSize,gp.tileSize*2);
		attackDown2 = setup("/player/blue_down2",gp.tileSize,gp.tileSize*2);
		attackLeft1 = setup("/player/blue_left1",gp.tileSize*2,gp.tileSize);
		attackLeft2 = setup("/player/blue_left2",gp.tileSize*2,gp.tileSize);
		attackRight1 = setup("/player/blue_right1",gp.tileSize*2,gp.tileSize);
		attackRight2 = setup("/player/blue_right2",gp.tileSize*2,gp.tileSize);
	}
	public void update() {
		
		if(attacking == true) {
			attacking();
		}

		else if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {

			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}

			collisionOn = false;
			gp.cChecker.checkTile(this);

			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);

			gp.eHandler.checkEvent();



			if(collisionOn == false && keyH.enterPressed == false) {

				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			gp.keyH.enterPressed = false;

			spriteCounter++;
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

	}
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <=15) {
			spriteNum = 1;
		}
		if(spriteCounter >15 && spriteCounter <=55) {
			spriteNum = 2;
		
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			switch(direction) {
			case "up": worldY -= attackArea.height;break;
			case "down": worldY += attackArea.height;break;
			case "left": worldX -= attackArea.width;break;
			case "right": worldX += attackArea.width;break;
			
			}
			
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
			
		}
		
		if(spriteCounter >25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}
	public void pickUpObject(int i) {

		if(i != 999) {

		}
	}
	public void interactNPC(int i) {

		if(gp.keyH.enterPressed == true) {
			if(i != 999) {

				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}

			else {
				gp.playSE(7);
				attacking = true;
			}

		}

	}
	public void contactMonster(int i) {
		if(i != 999) {
			if(invincible == false) {
				gp.playSE(6);
				life -= 1;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int i){
		if(i != 999) {
			
			if(gp.monster[i].invincible == false) {
				
				gp.playSE(5);
				
				gp.monster[i].life -= 2;
				gp.monster[i].invincible = true;
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
				}
			}
			
		}
		else {
			
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				if(spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;}
				if(spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;}
				if(spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackRight1;}
				if(spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		int tempX = screenX+solidArea.x;
		int tempY = screenY+solidArea.y;
		switch(direction) {
		case "up": tempY = screenY - attackArea.height;break;
		case "down": tempY = screenY + gp.tileSize;break;
		case "left": tempX = screenX - attackArea.width;break;
		case "right": tempX = screenX + gp.tileSize;break;
		}
		//g2.setColor(Color.red);
		//g2.setStroke(new BasicStroke(1));
		//g2.drawRect(tempX, tempY, attackArea.width, attackArea.height);

		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));



	}
}
