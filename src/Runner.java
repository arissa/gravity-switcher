import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Runner {

	private int xPos;
	private int yPos;
	private int dy;
	private static int gravity = 9;
	private final static int GRAVITY = gravity;
	private int width = 76;
	private int height = 76;
	private boolean isFlipping;

	private Image sprite0;
	private Image sprite1;
	private Image sprite2;
	private Image sprite3;
	private Image sprite4;
	private Image sprite5;
	private Image sprite6;
	private Image sprite7;
	private Image sprite8;
	private Image sprite9;
	
	private Image reverseSprite0;
	private Image reverseSprite1;
	private Image reverseSprite2;
	private Image reverseSprite3;
	private Image reverseSprite4;
	private Image reverseSprite5;
	private Image reverseSprite6;
	private Image reverseSprite7;
	private Image reverseSprite8;
	private Image reverseSprite9;

	
	private ArrayList<Image> sprites = new ArrayList<Image>();
	private ArrayList<Image> upsideDownSprites = new ArrayList<Image>();
	
	public Runner(int x, int y){
		xPos = x;
		yPos = y;
		
		// right side up sprites
		sprite0 = new ImageIcon(this.getClass().getResource("/sprite0.png")).getImage();
		sprite1 = new ImageIcon(this.getClass().getResource("/sprite1.png")).getImage();
		sprite2 = new ImageIcon(this.getClass().getResource("/sprite2.png")).getImage();
		sprite3 = new ImageIcon(this.getClass().getResource("/sprite3.png")).getImage();
		sprite4 = new ImageIcon(this.getClass().getResource("/sprite4.png")).getImage();
		sprite5 = new ImageIcon(this.getClass().getResource("/sprite5.png")).getImage();
		sprite6 = new ImageIcon(this.getClass().getResource("/sprite6.png")).getImage();
		sprite7 = new ImageIcon(this.getClass().getResource("/sprite7.png")).getImage();
		sprite8 = new ImageIcon(this.getClass().getResource("/sprite8.png")).getImage();
		sprite9 = new ImageIcon(this.getClass().getResource("/sprite9.png")).getImage();
		
		//add sprites to arraylist
		sprites.add(sprite0);
		sprites.add(sprite1);
		sprites.add(sprite2);
		sprites.add(sprite3);
		sprites.add(sprite4);
		sprites.add(sprite5);
		sprites.add(sprite6);
		sprites.add(sprite7);
		sprites.add(sprite8);
		sprites.add(sprite9);

		//upside down sprites
		reverseSprite0 = new ImageIcon(this.getClass().getResource("/reverseSprite0.png")).getImage();
		reverseSprite1 = new ImageIcon(this.getClass().getResource("/reverseSprite1.png")).getImage();
		reverseSprite2 = new ImageIcon(this.getClass().getResource("/reverseSprite2.png")).getImage();
		reverseSprite3 = new ImageIcon(this.getClass().getResource("/reverseSprite3.png")).getImage();
		reverseSprite4 = new ImageIcon(this.getClass().getResource("/reverseSprite4.png")).getImage();
		reverseSprite5 = new ImageIcon(this.getClass().getResource("/reverseSprite5.png")).getImage();
		reverseSprite6 = new ImageIcon(this.getClass().getResource("/reverseSprite6.png")).getImage();
		reverseSprite7 = new ImageIcon(this.getClass().getResource("/reverseSprite7.png")).getImage();
		reverseSprite8 = new ImageIcon(this.getClass().getResource("/reverseSprite8.png")).getImage();
		reverseSprite9 = new ImageIcon(this.getClass().getResource("/reverseSprite9.png")).getImage();


		//add sprites
		upsideDownSprites.add(reverseSprite0);
		upsideDownSprites.add(reverseSprite1);
		upsideDownSprites.add(reverseSprite2);
		upsideDownSprites.add(reverseSprite3);
		upsideDownSprites.add(reverseSprite4);
		upsideDownSprites.add(reverseSprite5);
		upsideDownSprites.add(reverseSprite6);
		upsideDownSprites.add(reverseSprite7);
		upsideDownSprites.add(reverseSprite8);
		upsideDownSprites.add(reverseSprite9);
	

	}
	
	
	public void draw(Graphics g, int frame, boolean upright) {
		
		g.drawImage(getImage(frame, upright), xPos, yPos, null);
		/*if (upright){
			g.drawRect(xPos +40, yPos + 60, width-65, height - 60);

		}
		else {
			g.drawRect(xPos + 40, yPos, width-65, height - 65);
		}*/
	}

	
	public Image getImage(int frame, boolean upright) {
		if (upright) {
			return sprites.get(frame);
		}
		else{
			return upsideDownSprites.get(frame);
		}
	}
	public void flipGravity(){
		gravity = -gravity;
	}
	
	public void setYPos(int y) {
		yPos = y;
	}

	/*public void resetGravity() {
		setDY(GRAVITY);
	}*/
	public void move(boolean isFalling){

		if (isFalling || isFlipping){
			setDY(gravity);
		}
		else {
			setDY(0);
		}
		
		yPos += dy;
	}
	
	public int getSpriteHeight() {
		return height;
	}

	public int getSpriteWidth() {
		return width;
	}
	public void reset(int x, int y){
		xPos = x;
		yPos = y;
	}

	public void setFlipping(boolean flipping){
		isFlipping = flipping;
	}
	public boolean isFlipping() {
		return isFlipping;
	}
	
	public void setDY(int newDY){
		dy = newDY;
	}

	
	public Rectangle getBounds(boolean gravityIsDownward){
		if (gravityIsDownward) {
			return new Rectangle(xPos +40, yPos + 65, width-65, height - 65);
		}
		else {
			return new Rectangle(xPos + 40, yPos, width-65, height - 65);
		}
	}
	
	
} 