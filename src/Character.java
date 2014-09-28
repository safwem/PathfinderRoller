import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Character {
	
	private Random r = new Random();
	private List<Integer> stats;
	private String trope;
	private String race;
	private String archetype;
	
	public Character(boolean bbeg, boolean twod6, List<String> tropes, List<String> races, List<String> archetypes){
			stats = new ArrayList<Integer>();
			trope = getRandomMember(tropes);
			race = getRandomMember(races);
			archetype = getRandomMember(archetypes);
			for(int i = 0; i < 6; i++){
				if(bbeg){
					stats.add(d6plus12());
				}
				else if(twod6){
					stats.add(twod6plus6());
				}
				else{
					stats.add(bestoffour());
				}
			}
	}
	
	
	
	@Override
	public String toString(){
		return (String.format("%s %s %s : %d %d %d %d %d %d",
				trope, race, archetype, stats.get(0), 
				stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5)));
	}
	
	private String getRandomMember(List<String> inputList){		
		int roll = r.nextInt(inputList.size());
		return (inputList.get(roll));
	}
	
	private int twod6plus6(){
		int total = 6;
		total += d6();
		total += d6();
		return total;
	}
	
	private int d6plus12(){
	int total = 12;
	total += d6();
		return total;
	}
	
	private int bestoffour(){
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
	
	private int d6(){
		return r.nextInt(6)+1;	
	}
}
