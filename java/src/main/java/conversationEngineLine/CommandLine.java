package conversationEngineLine;

import java.util.HashMap;
import java.util.LinkedList;

import conversationEngineInporter.ConverzationNode;

public class CommandLine extends ConversationLine {

	String command;

	public CommandLine(String command, ConverzationNode node) {
		super(node);
		this.command = command;
	}

	public String toCommand(HashMap<String, ConverzationNode> nodes, LinkedList<String> condition) {
		return String.format("run %s", command);
	}

}
