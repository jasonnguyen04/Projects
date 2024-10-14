package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Man extends Entity{

	public NPC_Man(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	public void getImage() {

		int choice = 1;

		switch(choice) {
		case 0:
			up1 = setup("/npc/gojo_up1",gp.tileSize,gp.tileSize);
			up2 = setup("/npc/gojo_up2",gp.tileSize,gp.tileSize);
			down1 = setup("/npc/gojo_down1",gp.tileSize,gp.tileSize);
			down2 = setup("/npc/gojo_down2",gp.tileSize,gp.tileSize);
			left1 = setup("/npc/gojo_left1",gp.tileSize,gp.tileSize);
			left2 = setup("/npc/gojo_left2",gp.tileSize,gp.tileSize);
			right1 = setup("/npc/gojo_right1",gp.tileSize,gp.tileSize);
			right2 = setup("/npc/gojo_right2",gp.tileSize,gp.tileSize);
			break;
		case 1:
			up1 = setup("/npc/megumi_up1",gp.tileSize,gp.tileSize);
			up2 = setup("/npc/megumi_up2",gp.tileSize,gp.tileSize);
			down1 = setup("/npc/megumi_down1",gp.tileSize,gp.tileSize);
			down2 = setup("/npc/megumi_down2",gp.tileSize,gp.tileSize);
			left1 = setup("/npc/megumi_left1",gp.tileSize,gp.tileSize);
			left2 = setup("/npc/megumi_left2",gp.tileSize,gp.tileSize);
			right1 = setup("/npc/megumi_right1",gp.tileSize,gp.tileSize);
			right2 = setup("/npc/megumi_right2",gp.tileSize,gp.tileSize);
			break;
		}
	}
	public void setDialogue() {
		
		dialogue[0] = "With this treasure I summon Big Maho";
		dialogue[1] = "Big Maho got that pole on him!";
		dialogue[2] = "Gojo cuh";
		dialogue[3] = "Where Yuji at?";
	}
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			
			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
		
	}
	public void speak() {
		
		super.speak();
	}

}
