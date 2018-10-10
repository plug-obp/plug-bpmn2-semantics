package sandbox.command.examples.executer;
import java.util.*;

public class Task {
	private List<String> string = new ArrayList<String>();
	private int pointer=0;
	
	public List<String> getString() {
		return string;
	}
	
	public void AddString (String... string) {
		Collections.addAll(this.string, string);
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	
	
	
	
	
}
