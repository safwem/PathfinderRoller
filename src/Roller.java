import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* 
 * working
 */

public class Roller extends JPanel implements MouseListener{
	
	boolean goodOnly = true;
	
	
	
	
	
	
	ArrayList<String> archetypes = populateList("./archetypes.txt");
	ArrayList<String> races  = populateList("./races.txt");
	ArrayList<String> alignments = populateList("./alignments.txt");
	ArrayList<String> tropes = populateList("./characterTropes.txt");
	ArrayList<String> bbegTropes = populateList("./bbegTropes.txt");
	
	String players[] = new String[5];
	BufferedImage rerollButton;
	Random generator = new Random( System.currentTimeMillis());
	boolean updated = true;
	
	int d6(){
		return generator.nextInt(6)+1;
	
	}
	
	 int bestoffour(){
		int[] rolls = {0,0,0,0};
		int lowestIndex = 0;
		for (int i = 0; i < 4; i++){
			rolls[i] = d6();
			if (rolls[i] < rolls[lowestIndex])
				lowestIndex = i;
		}
		int total = 0;
		for (int i = 0; i < 4; i++){
			if (i!= lowestIndex)
				total+= rolls[i];
		}
		return total;	
	}
	int twod6plus6(){
			int total = 6;
			total += d6();
			total += d6();
			return total;
		}
	int d6plus12(){
		int total = 12;
		total += d6();
		return total;
	}
	void println(int[] stats){
		for(int i = 0; i < 6;i++){
				System.out.print(stats[i] + " ");
				if (i == 5)
					System.out.println();
		}
	}
	
	int[] rollSix(){
		int stats[] = {0,0,0,0,0,0};
		for(int i = 0; i < 6; i++)
			stats[i] = twod6plus6();	
		return stats;
	}
	String rollSixString(){
		int stats[] = rollSix();
		return(stats[0] + " " + stats[1] + " " + stats[2] + " " + stats[3] + " " + stats[4] + " " + stats[5]);
	}
	
	String rollSixStronger(){
		int stats[] = {0,0,0,0,0,0};
		for(int i = 0; i < 6; i++)
			stats[i] = d6plus12();	
		
		return(stats[0] + " " + stats[1] + " " + stats[2] + " " + stats[3] + " " + stats[4] + " " + stats[5]);
	}
	
	ArrayList populateList(String fileName){
		BufferedReader br = null;
		ArrayList <String> thisOne = new ArrayList();
		
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fileName));
			while ((sCurrentLine = br.readLine()) != null)
				thisOne.add(sCurrentLine);
	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)br.close();
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return thisOne;
	}

	
	String rollCharacter(){
		int archetypeRoll = generator.nextInt(archetypes.size());
		int raceRoll = generator.nextInt(races.size());
		int tropeRoll = generator.nextInt(tropes.size());
	
		return(		tropes.get(tropeRoll)+" - "+  races.get(raceRoll)+ " " + archetypes.get(archetypeRoll) + " " + rollSixString());
		
	}
	String rollBBEG(){
		int archetypeRoll = generator.nextInt(archetypes.size());
		int raceRoll = generator.nextInt(races.size());
		int tropeRoll = generator.nextInt(bbegTropes.size());
		return(		bbegTropes.get(tropeRoll)+ " - " +  races.get(raceRoll)+ " " + archetypes.get(archetypeRoll) + " " + rollSixStronger());
		
	}
	
	void reroll(){
		for(int i = 0; i<4; i++)
			players[i] = rollCharacter();
		players[4] = rollBBEG();
		updated = true;
	}
	
	public Roller(){
		try {
			rerollButton = ImageIO.read(new File("reroll.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reroll();
		addMouseListener(this);
		
	}
	
	public void paint(Graphics g){

		g.clearRect(0, 0, 450, 300);
		g.drawString("Your party:",20,15);
		g.drawString(players[0],5,35);
		g.drawString(players[1],5,55);
		g.drawString(players[2],5,75);
		g.drawString(players[3],5,95);
		g.drawString("BBEG: ",20,115);
		g.drawString(players[4],5,135);

		g.drawImage(rerollButton,100,150,this);
		g.drawString("Pathfinder party roller by Dan  -- May 22 2013 ed.",10,265);
		updated = false;
	}
	
	public static void main(String[] args){
		
		Roller r = new Roller();
		JFrame window = new JFrame(); //the only jframe
		
		window.getContentPane().add(r); //add the grid to the frame
		window.setBackground(Color.gray); //you shouldnt ever actually see the background
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //putting a comment here
		window.setSize(450, 335); //magic numbers xy, need 1344 min to have 20x20 of the 64x64 tiles though
		window.setVisible(true); 

		
		boolean cont = true;
		while(cont){
			if(r.updated)
				r.repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int xloc = e.getX();
		int yloc = e.getY();
		//System.out.println("clicked");
		if(xloc > 100 && xloc < 400 && yloc > 150 && yloc < 400){
		//	System.out.println("Clicked on box");
			reroll();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
