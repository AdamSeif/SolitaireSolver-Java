
public class DomSolitaire {
	private StackADT<Domino>[] foundation;
	private StackADT<Domino>[] stack;
	private String name;
	private boolean debug;

	/*
	 * The constructor initializes foundation[] and stack[] using 2 for loops; by
	 * creating & naming stacks. The stack[] stacks are then filled by utilizing two
	 * nested for loops (one traversing the domino set & the other traversing
	 * stack[]) & a while loop (that fills each stack in stack[]).
	 */
	public DomSolitaire(int highestNum, int seed, String name) {
		this.name = name;
		foundation = new StackADT[highestNum + 1];
		stack = new StackADT[highestNum + 1];
		for (int i = 0; i < foundation.length; i++) {
			foundation[i] = new StackLL<Domino>("F" + i);
		}
		for (int i = 0; i < stack.length; i++) {
			stack[i] = new StackLL<Domino>("S" + i);
		}
		Domino[] set = Domino.getSet(highestNum);
		Domino.shuffle(set, seed);

		for (int i = 0; i < set.length; i++) {
			for (int j = 0; j < stack.length; j++) {
				while (stack[j].size() < j + 1) {
					stack[j].push(set[i]);
					i++;
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/*
	 * Operates exactly as the constructor, with the exception that instead of using
	 * highestNum to create the domino set, it uses stack.length - 1.
	 */
	public void reset(int seed) {
		for (int i = 0; i < foundation.length; i++) {
			foundation[i] = new StackLL<Domino>("F" + i);
		}
		Domino[] set = Domino.getSet(stack.length - 1);
		Domino.shuffle(set, seed);
		for (int i = 0; i < set.length; i++) {
			for (int j = 0; j < stack.length; j++) {
				while (stack[j].size() < j + 1) {
					stack[j].push(set[i]);
					i++;
				}
			}
		}
	}

	/*
	 * Creates a "winner" variable & sets it to true, then uses a for loop to
	 * traverse stack[]; if any of the stacks isn't empty, winner is set to false.
	 * "winner" is returned.
	 */
	public boolean winner() {
		boolean winner = true;
		for (int i = 0; i < stack.length; i++) {
			if (stack[i].isEmpty()) {
			} else
				winner = false;
		}
		return winner;
	}

	/*
	 * Peeks the top domino in each regular stack & checks whether it's a double
	 * brick; if it is, a move is created from that stack to the foundation stack
	 * which has an index equal to that domino's getHigh(). If the domino isn't a
	 * double brick, a move is created if the domino's getHigh() equals the index f
	 * & its getLow() is one lower than the domino on top of foundation[f].
	 */
	public UnorderedListADT<Move> findSFMoves() {
		UnorderedListADT<Move> legalMoves = new UnorderedList<Move>();
		for (int s = 0; s < stack.length; s++) {
			if (!stack[s].isEmpty()) {
				Domino current = stack[s].peek();
				if (current.getLow() == current.getHigh()) {
					for (int f = 0; f < foundation.length; f++) {
						if (current.getHigh() == f) {
							Move legalMove = new Move(stack[s], foundation[f]);
							legalMoves.addToFront(legalMove);
						}
					}
				} else {
					for (int f = 0; f < foundation.length; f++) {
						if (!foundation[f].isEmpty()) {
							if (current.getHigh() == foundation[f].peek().getHigh()) {
								if (current.isOneDown(foundation[f].peek())) {
									Move legalMove = new Move(stack[s], foundation[f]);
									legalMoves.addToRear(legalMove);
								}
							}
						}
					}
				}
			}
		}
		return legalMoves;
	}

	/*
	 * Peeks the top domino in each regular stack & checks whether its getLow() is
	 * equal to 0; if it is, a move is created from the stack to the first available
	 * empty stack.
	 */
	public UnorderedListADT<Move> findSESMoves() {
		UnorderedListADT<Move> legalMoves = new UnorderedList<Move>();
		for (int sFrom = 0; sFrom < stack.length; sFrom++) {
			if (!stack[sFrom].isEmpty()) {
				Domino current = stack[sFrom].peek();
				if (current.getLow() == 0) {
					for (int sTo = 0; sTo < stack.length; sTo++) {
						if (stack[sTo].isEmpty()) {
							Move legalMove = new Move(stack[sFrom], stack[sTo]);
							legalMoves.addToFront(legalMove);
						}
					}
				}
			}
		}
		return legalMoves;
	}

	/*
	 * Peeks the top domino in each regular stack & peeks the top of the other
	 * stacks; if the the two share one number in common, the other number is
	 * checked. A move is created if from the stack containing the domino which is
	 * one up to the stack containing the domino which is one down.
	 */
	public UnorderedListADT<Move> findSSMoves() {
		UnorderedListADT<Move> legalMoves = new UnorderedList<Move>();
		for (int sFrom = 0; sFrom < stack.length; sFrom++) {
			if (!stack[sFrom].isEmpty()) {
				Domino current = stack[sFrom].peek();
				for (int sTo = 0; sTo < stack.length; sTo++) {
					if (!stack[sTo].isEmpty()) {
						if (current.getHigh() == stack[sTo].peek().getHigh()) {
							if (current.isOneUp(stack[sTo].peek())) {
								Move legalMove = new Move(stack[sFrom], stack[sTo]);
								legalMoves.addToFront(legalMove);
							}
						} else if (current.getLow() == stack[sTo].peek().getHigh()) {
							if (current.isOneUp(stack[sTo].peek())) {
								Move legalMove = new Move(stack[sFrom], stack[sTo]);
								legalMoves.addToFront(legalMove);
							}
						}
					}
				}
			}
		}
		return legalMoves;

	}

	public void findMoves(StackADT<Move> st) {
		for (int i = 0; i < findSESMoves().size(); i++) {
			st.push(findSESMoves().removeFirst());
		}
		for (int i = 0; i < findSSMoves().size(); i++) {
			st.push(findSSMoves().removeFirst());
		}
		for (int i = 0; i < findSFMoves().size(); i++) {
			st.push(findSFMoves().removeFirst());
		}
	}

	public Move createMove(String from, String to) {
		int fromIndex = Integer.parseInt(from.substring(1));
		int toIndex = Integer.parseInt(to.substring(1));
		;
		if (to.startsWith("S")) {
			return new Move(stack[fromIndex], stack[toIndex]);
		} else
			return new Move(stack[fromIndex], foundation[toIndex]);
	}

	public String toString() {
		String text = this.name + " ";
		for (int i = 0; i < foundation.length; i++) {
			text += "F" + i + " " + foundation[i].size() + "/" + (i + 1) + "  ";
		}
		text += "\n";
		for (int i = 0; i < stack.length; i++) {
			text += stack[i].toString() + "\n";
		}
		return text;
	}

	public String showNamedContent() {
		String text = "";
		for (int i = 0; i < foundation.length; i++) {
			text += foundation[i].size();
		}
		text += "|";
		for (int i = 0; i < stack.length; i++) {
			text += ((StackLL<Domino>) stack[i]).showNamedContent();
		}
		return text;
	}

	/*
	 * Creates 3 stacks at the start, one which will remain empty, one that is
	 * filled temporarily, and the third will be returned (it is filled using the
	 * temporary stack). The while loop follows the instructions from the assignment
	 * handout verbatim. The final if statement will check if maxSteps was reached
	 * (it will return null if true), otherwise, it'll check if the game is in a
	 * winning state (it will push tempSequence onto finalSequence and return
	 * finalSequence); if the game isn't winning, emptySequence is returned.
	 */
	public StackADT<Move> findSolution(int maxSteps) {
		StackADT<Move> finalSequence = new StackLL<Move>();
		StackADT<Move> tempSequence = new StackLL<Move>();
		StackADT<Move> emptySequence = new StackLL<Move>();
		StackADT<Move> moves = new StackLL<Move>();
		findMoves(moves);
		int steps = 0;

		while (!moves.isEmpty() && !winner() && (steps + 1 < maxSteps)) {
			steps++;
			if (moves.peek().isCompleted()) {
				moves.peek().undoIt();
				moves.pop();
			} else if (!moves.peek().isCompleted()) {
				moves.peek().doIt();
				tempSequence.push(moves.peek());
				findMoves(moves);
			}

		}

		if (steps + 1 == maxSteps) {
			return null;
		} else if (winner()) {
			while (!tempSequence.isEmpty()) {
				finalSequence.push(tempSequence.pop());
			}
			return finalSequence;
		} else
			return emptySequence;
	}
}
