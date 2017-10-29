import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private MinPQ<Node> pq1 ; //For the initial board
	private MinPQ<Node> pq2; //For the twin of initial board
	private boolean isSolvable;
	private int moves;
	private ArrayList<Board> solutionSteps=new ArrayList<>();
	private class Node implements Comparable<Node> {
		private Board board;
		private Node previous;
		int moves;

		public Node(Board board,Node previous,int moves){
			this.board=board;
			this.previous=previous;
			this.moves=moves;
		}

		public int compareTo(Node that) {
			return this.board.manhattan()+this.moves-that.board.manhattan()-that.moves;
		}
	}

	public Solver(Board initial) {
		pq1=new MinPQ<Node>();
		pq2=new MinPQ<Node>();
		isSolvable=false;
		Node node=new Node(initial,null,0);
		Node nodeTwin=new Node(initial.twin(),null,0);

		while(!node.board.isGoal() && !nodeTwin.board.isGoal()) {
			for(Board b:node.board.neighbors()) {
				if(node.previous==null || !b.equals(node.previous.board)){
					Node neighbor = new Node(b, node,node.moves+1);
                    pq1.insert(neighbor);
				}
			}

			for(Board b:nodeTwin.board.neighbors()) {
				if(nodeTwin.previous==null || !b.equals(nodeTwin.previous.board)){
					Node neighbor = new Node(b, nodeTwin,nodeTwin.moves+1);
                    pq2.insert(neighbor);
				}
			}

			node=pq1.delMin();
			nodeTwin=pq2.delMin();
			
		}

		if(node.board.isGoal()){
			isSolvable=true;
			moves=node.moves;
			while(node!=null){
				solutionSteps.add(0,node.board);
				node=node.previous;
			}
		}
	}

	public boolean isSolvable(){
		return isSolvable;
	}

	public int moves(){
		if(!isSolvable)
			return -1;
		return moves;
	}

	public Iterable<Board> solution(){
		if(!isSolvable)
			return null;
		return solutionSteps;
	}

	public static void main(String[] args) {
		  // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}