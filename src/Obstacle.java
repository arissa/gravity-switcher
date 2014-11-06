import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
  
  
public class Obstacle {
	 
    int xPos, yPos; // position of obstacle
    int width = 80;
    int height = 15;
    int tier;
    int dx = -7;
    int panelHeight;
    int tier0;
    int tier1;
    int tier2;
    
	Image brickWallImage;
    
    
    Obstacle(int x, int tier, int panelHeight){
        xPos = x;
        this.tier = tier;
        this.panelHeight = panelHeight;
        brickWallImage = new ImageIcon(this.getClass().getResource("/brickwall.png")).getImage();
        tier0 = panelHeight/8 - height;
        tier1 = panelHeight/2 - height;
        tier2 = 7* panelHeight/8 - height;
    }
      
    public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void move(){
    	xPos += dx;
    }
    
    public int getXPos() {
		return xPos;
	}

	public void setXPos(int x) {
		xPos = x;
	}

	public int getYPos() {
		return yPos;
	}
	public void setYPos(int y){
		yPos = y;
	}
	public void draw(Graphics g) {
         g.setColor(new Color(0,0,0));
         if (tier == 0){
        	 setYPos(tier0);
        	 g.drawImage(brickWallImage, xPos, yPos, xPos + width, yPos + height, 0, 0, width, height, null);
        	 g.drawRect(xPos,yPos, width, height);
         }
         else if (tier == 1){
        	 setYPos(tier1); 
        	 g.drawImage(brickWallImage, xPos, yPos, xPos + width, yPos + height, 0, 0, width, height, null);
        	 g.drawRect(xPos,yPos, width, height);

         }
         else{
           	setYPos(tier2);  
           	g.drawImage(brickWallImage, xPos, yPos, xPos + width, yPos + height, 0, 0, width, height, null);
       	 g.drawRect(xPos,yPos, width, height);

         }
         
    }
    
    public boolean rightEdgeIsOffscreen(){
    	if (getXPos() < -50){
    		return true;
    	}
    	else return false;
    }
    
    public Rectangle getBounds(){
    	return new Rectangle(xPos,yPos, width, height);
    	
    }
   
} 