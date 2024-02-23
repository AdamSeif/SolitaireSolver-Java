
public class Move {
	private StackADT<Domino> from;
	private StackADT<Domino> to;
	private boolean completed;
	private String name;

	public Move(StackADT<Domino> from, StackADT<Domino> to) {
		this(from, to, "m");
	}

	public Move(StackADT<Domino> from, StackADT<Domino> to, String name) {
		this.from = from;
		this.to = to;
		this.name = name;
		completed = false;
	}

	public void doIt() {
			to.push(from.pop());
			completed = true;
		
	}

	public void undoIt() {
			from.push(to.pop());
			completed = false;
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Move) {
			if (this.to.equals(((Move) obj).to) && this.from.equals(((Move) obj).from)) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public String toString() {
		String text = name;
		if (from instanceof Named) {
			text += ((Named) from).getName();
		} else {
			text += from.toString();
		}
		text += "->";
		if (to instanceof Named) {
			text += ((Named) to).getName();
		} else {
			text += to.toString();
		}
		if (isCompleted() == true) {
			text += "!";
		} else if (isCompleted() == false) {
			text += "?";
		}
		return text;
	}
}
