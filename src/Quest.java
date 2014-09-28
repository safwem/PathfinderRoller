import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Quest {

	Random r = new Random();
	String majorLocation;
	List<String> minorLocations;
	List<Character> NPCs;
	Character BBEG;
	


	public Quest(List<String> tropes, List<String> BBEGtropes, List<String> races, List<String> archetypes, List<String> locations, List<String> allMinorLocations){
		BBEG = new Character(true, false, BBEGtropes, races, archetypes);
		NPCs = new ArrayList<Character>();
		do{
			NPCs.add(new Character(false, true, tropes, races, archetypes));			
		}while(r.nextInt(100) > 50);
		majorLocation = getRandomMember(locations);
		minorLocations = new ArrayList<String>();
		do{
			minorLocations.add(getRandomMember(allMinorLocations));
		}while(r.nextInt(100)>66);
	}
	
	public String getMajorLocation() {
		return majorLocation;
	}


	public List<String> getMinorLocations() {
		return minorLocations;
	}


	public List<Character> getNPCs() {
		return NPCs;
	}


	public Character getBBEG() {
		return BBEG;
	}

	
	private String getRandomMember(List<String> inputList){	
		int roll = r.nextInt(inputList.size());
		return (inputList.get(roll));
	}
}
