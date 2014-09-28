import java.util.List;
import java.util.Random;


public class Campaign {

	Random r = new Random();
	String world;
	String twist;
	
	public Campaign(List<String> worldSettings, List<String> plotTwists){
		world = getRandomMember(worldSettings);
		twist = getRandomMember(plotTwists);
	}
	
	
	public String getWorld() {
		return world;
	}


	public String getTwist() {
		return twist;
	}


	private String getRandomMember(List<String> inputList){		
		int roll = r.nextInt(inputList.size());
		return (inputList.get(roll));
	}
	
}
