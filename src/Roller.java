import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 
 * working
 */

public class Roller extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean bestOfFour = false;
	private int partySize = 4;
	private Quest currentQuest;
	private List<Character> party;
	private Campaign currentSetting;
	protected JTextField filenameField;
	protected JButton toggleRollButton, rerollButton, exportButton, rerollQuestButton;
	private ArrayList<String> races  = populateList("./races.txt");
	private ArrayList<String> archetypes = populateList("./archetypes.txt");
	private ArrayList<String> tropes = populateList("./characterTropes.txt");
	private ArrayList<String> bbegTropes = populateList("./bbegTropes.txt");
	private ArrayList<String> plotTwists = populateList("./plotTwists.txt");
	private ArrayList<String> locations = populateList("./locations.txt");
	private ArrayList<String> worldSettings = populateList("./worldSettings.txt");
	private ArrayList<String> minorLocations = populateList("./minorLocations.txt");
	
	
	private void rerollParty(int partySize){
		party = new ArrayList<Character>();
		for(int i = 0; i < partySize; i++){
			party.add(new Character(false, bestOfFour, tropes, races, archetypes));
		}
	}

	private void rerollQuest(){
		currentQuest = new Quest(tropes, bbegTropes, races, archetypes, locations, minorLocations);
	}

	private void rerollCampaign(){
		currentSetting = new Campaign(worldSettings,plotTwists);
	}
	
	
	private ArrayList<String> populateList(String fileName){
		BufferedReader br = null;
		ArrayList<String> allStrings = new ArrayList<String>();
		
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fileName));
			while ((sCurrentLine = br.readLine()) != null){
				allStrings.add(sCurrentLine);
			}
	
		} 
		catch (IOException e) {
			System.out.println("failed to load file");
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
		System.out.println("Loaded " + allStrings.size() + " strings from file " + fileName);
		return allStrings;
	}

	
	
	
	
	private void updateInterface(){
	
		if(!bestOfFour){
			toggleRollButton.setText("change to best of four");
		}
		else{
			toggleRollButton.setText("change to 2d6 + 6");
		}
	}
	
	private void exportTextFile(String filename){
		PrintWriter writer;
		try {
		writer = new PrintWriter(filename, "UTF-8");
		
		writer.println("Campaign setting:");
		writer.println(currentSetting.getWorld());
		writer.println("With twist:");
		writer.println(currentSetting.getTwist());
		writer.println("Your party:");
		for(Character thisCharacter:party){
			writer.println(thisCharacter.toString());
		}
		writer.println("Your quest:");
		writer.println("Major location:");
		writer.println(currentQuest.getMajorLocation());
		writer.println("Minor locations:");
		for(String location:currentQuest.getMinorLocations()){
			writer.println(location);
		}
		writer.println("NPCs:");
		for(Character npc:currentQuest.getNPCs()){
			writer.println(npc.toString());
		}
		writer.println("BBEG:");
		writer.println(currentQuest.getBBEG().toString());
		writer.println();
		
		writer.println("\n\nParty generated by Dan's Pathfinder Party roller!");
		writer.println("Sept 25 2014 edition build 0.21");
		writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Roller(){
				
		toggleRollButton = new JButton("change to best of four");		
		toggleRollButton.addActionListener(this);
		toggleRollButton.setActionCommand("toggleRoll");
		add(toggleRollButton);	

		filenameField = new JTextField("filename.txt");
		filenameField.addActionListener(this);
		add(filenameField);
		
		exportButton = new JButton("Export");
		exportButton.addActionListener(this);
		exportButton.setActionCommand("export");
		add(exportButton);
		
		rerollButton = new JButton("reroll!");
		rerollButton.addActionListener(this);
		rerollButton.setActionCommand("reroll");
		add(rerollButton);
		
		rerollQuestButton = new JButton("get new quest");
		rerollQuestButton.addActionListener(this);
		rerollQuestButton.setActionCommand("rerollQuest");
		add(rerollQuestButton);
		
		rerollCampaign();
		rerollParty(partySize);
		rerollQuest();
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		int xLoc = 5;
		int yLoc = 65;
		g.drawString("Campaign setting:",xLoc+15,yLoc);
		yLoc+=20;
		addUrlButton(currentSetting.getWorld(),"tvTropes",xLoc,yLoc,150,20);
		yLoc+=20;
		g.drawString("Plot twist:",xLoc+15,yLoc);
		yLoc+=20;
		addUrlButton(currentSetting.getTwist(),"tvTropes",xLoc,yLoc,150,20);
	}
	
	private void oldPaint(Graphics g){
		int xLoc = 5;
		int yLoc = 65;
		g.drawString("Campaign setting:",xLoc+15,yLoc);
		yLoc+=20;
		g.drawString(currentSetting.getWorld(),xLoc,yLoc);
		yLoc+=20;
		g.drawString("Plot twist:",xLoc+15,yLoc);
		yLoc+=20;
		g.drawString(currentSetting.getTwist(), xLoc, yLoc);
		yLoc+=40;
		g.drawString("Your party:",xLoc+15,yLoc);
		yLoc+=20;
		for(Character player: party){
			g.drawString(player.toString(),xLoc,yLoc);
			yLoc+=20;
		}
		yLoc+=20;
		g.drawString("Current quest location:",xLoc+15,yLoc);
		yLoc+=20;
		g.drawString(currentQuest.getMajorLocation(),xLoc,yLoc);
		yLoc+=20;
		g.drawString("Minor locations:",xLoc+15,yLoc);
		yLoc+=20;
		for(String location:currentQuest.getMinorLocations()){
			g.drawString(location,xLoc,yLoc);
			yLoc+=20;
		}
		yLoc+=20;
		g.drawString("NPCS:",xLoc+15,yLoc);
		yLoc+=20;
		for(Character NPC: currentQuest.getNPCs()){
			g.drawString(NPC.toString(),xLoc,yLoc);
			yLoc+=20;
		}
		yLoc+=20;
		g.drawString("BBEG: ",xLoc+15,yLoc);
		yLoc+=20;
		g.drawString(currentQuest.getBBEG().toString(),xLoc,yLoc);

		g.drawString("Pathfinder party roller by Dan  -- Sep 25 2014 ed.",10,yLoc+40);
		g.drawString("Contact: pathfinderRoller@mail.com",10,yLoc+60);
	}
	
	public static void main(String[] args){
		
		Roller r = new Roller();
		JFrame window = new JFrame(); //the only jframe
		
		window.getContentPane().add(r); //add the grid to the frame
		window.setBackground(Color.gray); //you shouldnt ever actually see the background
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //putting a comment here
		window.setSize(1000, 700); //magic numbers xy, need 1344 min to have 20x20 of the 64x64 tiles though
		window.setVisible(true); 

		boolean cont = true;
		while(cont){
				r.repaint();
		}
		
	}
	
	protected static ImageIcon createImageIcon(String path) {
	    return new ImageIcon(path);
	}
	
	private String getUrl(String search, String suffix){
		StringBuilder newString = new StringBuilder("http://www.google.com/search?q=");
		newString.append(search);
		newString.append(" ");
		newString.append(suffix);
		newString.append("&btnI");
		return newString.toString();		
	}
	
	private void openLink(String url){
		URI uri;
		try {
			uri = new URI(url);
		
			if(Desktop.isDesktopSupported()){
				try{
					Desktop.getDesktop().browse(uri);
				}
				catch(IOException e){
					System.out.println("Failed to open url");
				}
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		if(arg0.getActionCommand().equals("toggleRoll")){
			System.out.println("Toggled roll type");
			bestOfFour = !bestOfFour;
		}
		else if(arg0.getActionCommand().equals("export")){
			System.out.println("Exporting!");;
			exportTextFile(filenameField.getText());
		}
		else if(arg0.getActionCommand().equals("reroll")){
			System.out.println("Rerolling");
			rerollParty(partySize);
			rerollQuest();
			rerollCampaign();
		}
		else if(arg0.getActionCommand().equals("rerollQuest")){
			rerollQuest();
		}
		else{
			System.out.println("Opening link...");
			openLink(arg0.getActionCommand());
		}
		
		updateInterface();
	}
	
	private void addUrlButton(String text, String type, int xLoc, int yLoc, int width, int height){
		JButton newButton = new JButton(text);
		newButton.setBounds(xLoc,yLoc,width,height);
		newButton.setActionCommand(getUrl(text,type));
		add(newButton);
	}

}
