package conversationEngineLine.yarncommands;

import java.util.HashMap;
import java.util.LinkedList;

import conversationEngineImporter.CEStory;
import conversationEngineImporter.ConversationNode;
import conversationEngineImporter.NPC;
import conversationEngineImporterInterfaces.ConversationLineInterface;
import conversationEngineLine.ConversationLine;

public class PlaySound extends ConversationLine implements ConversationLineInterface {

	String sound;
	
	public PlaySound() {
		super();
	}

	public PlaySound(String sound, ConversationNode node) {
		super();
		if(sound.charAt(0) == '/') {
			sound = sound.substring(1);//if the command starts with a / remove it.
		}
		this.sound = sound;
	}

	public String toCommand(HashMap<String, ConversationNode> nodes, CEStory ceStory, NPC npc, LinkedList<String> condition, String con) {
		return String.format("%srun stopsound @s\n%sat @e[type= villager, tag = %s] run playsound %s voice @s\n",con, con, npc.getTagName(), sound);
	}

	public String getYarnCommand() {
		return "playsound";
	}

	public ConversationLine tryParseArguments(String[] arguments, ConversationNode node) {
		if (arguments.length == 2) {
			return new PlaySound(arguments[1], node);
		} else {
			System.err.println("Error " + arguments[0]
					+ " is invalid. example: <<command|some custom command>> (use @s to select the player talkign with the npc)");
		}
		return null;
	}

}
