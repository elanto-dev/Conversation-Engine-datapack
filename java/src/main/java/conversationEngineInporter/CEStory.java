package conversationEngineInporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;
import static java.nio.file.StandardCopyOption.*;

/**
 * the Conversation Engine Story class stores the different npc's and is called
 * to generate the datapack.
 * 
 * @author Sijmen_v_b
 * 
 */
public class CEStory {
	private LinkedList<NPCGroup> groups;

	public CEStory(LinkedList<NPCGroup> groups) {
		this.groups = groups;
	}

	public void generateDatapack() {
		String name = "exported datapack";
		deletePreviousDatapack(name);
		loadEmptyDatapack(name);
		createGroupFolder(name);
		createMessagesFolder(name);
		createVillagerFolder(name);
	}

	// ---- delete previous datapack ----

	/**
	 * deletes previous datapack with same name if it exists.
	 * 
	 * @param name
	 */
	private void deletePreviousDatapack(String name) {
		try {
			deleteDirectory(System.getProperty("user.dir") + "\\" + name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("There was no previous datapack to delete.");
		}
	}

	// ---- delete previous datapack ----
	// ---- load empty datapack ----

	/**
	 * copys the empty datapack to the root directory
	 * 
	 * @param name
	 */
	private void loadEmptyDatapack(String name) {
		try {
			copyDirectory(System.getProperty("user.dir") + "\\src\\main\\resources\\datapack empty",
					System.getProperty("user.dir") + "\\" + name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ---- load empty datapack ----
	// ---- create group folder ----

	private void createGroupFolder(String name) {
		createDirectory(name + "\\data\\conversation_engine\\functions\\group");
		createGroupFunction(name);
		createOpenFunction(name);
		createCloseFunction(name);

	}

	private void createOpenFunction(String name) {
		for (NPCGroup npcGroup : groups) {
			String s = npcGroup.createOpenFunction();

			// try to save the file.
			try {
				PrintWriter out = new PrintWriter(
						String.format("%s\\data\\conversation_engine\\functions\\group\\%03d.mcfunction", name,
								npcGroup.getGroupId()));
				out.write(s);
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void createCloseFunction(String name) {
		for (NPCGroup npcGroup : groups) {
			String s = npcGroup.createCloseFunction();

			// try to save the file.
			try {
				PrintWriter out = new PrintWriter(
						String.format("%s\\data\\conversation_engine\\functions\\group\\close_%03d.mcfunction", name,
								npcGroup.getGroupId()));
				out.write(s);
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void createGroupFunction(String name) {
		// start the function with some comments
		String s = "# run by server\n\n# this is for grouping of villagers so we don't have to check each villager each tick only each group.\n\n# check if there is a conversation in a group:\n";
		for (NPCGroup npcGroup : groups) {// for each group create the execute command with the correct group id.
			s += String.format(
					"execute if score CE_mannager CE_group_%02d matches 1 run function conversation_engine:group/%03d\n",
					npcGroup.getGroupId(), npcGroup.getGroupId());
		}

		// try to save the file.
		try {
			PrintWriter out = new PrintWriter(name + "\\data\\conversation_engine\\functions\\group\\group.mcfunction");
			out.write(s);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ---- create group folder ----
	// ---- create messages folder ----

	private void createMessagesFolder(String name) {
		createTalkFunction();
		// for every villager create their named messages folder.
	}

	private void createTalkFunction() {

	}

	// ---- create messages folder ----
	// ---- create villager folder ----

	private void createVillagerFolder(String name) {
		createKillFolder();
		createSummonFolder();
	}

	private void createKillFolder() {
		createkillAllFunction();
		// for every villager create kill function
	}

	private void createkillAllFunction() {

	}

	private void createSummonFolder() {
		// for every villager create summon function
	}

	// ---- create villager folder ----
	// ---- create directory ----

	private void createDirectory(String dirpath) {
		File f = new File(dirpath);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	// ---- create directory ----
	// ---- copy directory ---- source: https://www.baeldung.com/java-copy-directory

	public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
			throws IOException {
		Files.walk(Paths.get(sourceDirectoryLocation)).forEach(source -> {
			Path destination = Paths.get(destinationDirectoryLocation,
					source.toString().substring(sourceDirectoryLocation.length()));
			try {
				Files.copy(source, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	// ---- copy directory ---- source: https://www.baeldung.com/java-copy-directory
	// ---- delete directory ---- source:
	// https://softwarecave.org/2018/03/24/delete-directory-with-contents-in-java/

	private void deleteDirectory(String dirpath) throws IOException {
		File file = new File(dirpath);
		deleteDirectoryRecursionJava6(file);
	}

	private void deleteDirectoryRecursionJava6(File file) throws IOException {
		if (file.isDirectory()) {
			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					deleteDirectoryRecursionJava6(entry);
				}
			}
		}
		if (!file.delete()) {
			throw new IOException("Failed to delete " + file);
		}
	}
	// ---- delete directory ---- source:
	// https://softwarecave.org/2018/03/24/delete-directory-with-contents-in-java/
}
