import edu.princeton.cs.algs4.LinkedStack;

public class Board {
	private int[][] blocks;
	private int n;

	public Board(int[][] blocks ) {
		n=blocks.length;
		this.blocks=new int[n][n];

		for (int i=0;i<n;i++)
			for (int j=0;j<n;j++)
				this.blocks[i][j]=blocks[i][j];

	}

	public int dimension() {
		return n;
	}

	public int hamming() {
		int num,hamming=0;
		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				num=i*n+j+1;
				if (blocks[i][j]!=0 && blocks[i][j]!=num){
					hamming++;
				}
			}
		}
		return hamming;
	}

	public int manhattan() {
		int manhattan=0;

		for(int i=0;i< n;i++){
			for(int j=0;j< n;j++){
				if(blocks[i][j]==0) continue;

				int iValue=(blocks[i][j]-1)/n;
				int jValue=(blocks[i][j]-1)%n;

				manhattan+=Math.abs(i-iValue)+Math.abs(j-jValue);	
			}
		}
		return manhattan;
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	public Board twin() {
		int[][] twinBlocks=new int[n][n];

		for (int i=0;i<n;i++)
			for (int j=0;j<n;j++)
				twinBlocks[i][j]=blocks[i][j];

		if(twinBlocks[0][0]!=0 && twinBlocks[0][1]!=0){
			int temp=twinBlocks[0][0];
			twinBlocks[0][0]=twinBlocks[0][1];
			twinBlocks[0][1]=temp;
		}else {
			int temp=twinBlocks[1][0];
			twinBlocks[1][0]=twinBlocks[1][1];
			twinBlocks[1][1]=temp;
		}	

		return new Board(twinBlocks);
	}

	public boolean equals(Object y) {
		if (y==this) return true;
		if( y == null || !(y instanceof Board)) return false;
		Board that = (Board) y;

		if(that.blocks.length != n) return false;

		for (int i=0;i<n;i++){
			for (int j=0;j<n;j++){
				if(that.blocks[i][j]!=this.blocks[i][j])
					return false;
			}
		}

		return true;
	}

	public Iterable<Board> neighbors() {
		LinkedStack<Board> neighs = new LinkedStack<>();

        // find position of blank tile
        int r = 0;
        int c = 0;
 
        for (int i = 0; i < n; i++) { 
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    r = i;
                    c = j;
                    break;
                }
            }
        }

        if (r > 0) {
            Board left=new Board(blocks);
            left.blocks[r][c] = left.blocks[r - 1][c];
            left.blocks[r - 1][c] = 0;
            neighs.push(left);
        }

        if (c > 0) {
            Board top=new Board(blocks);
            top.blocks[r][c] = top.blocks[r][c - 1];
            top.blocks[r][c - 1] = 0;
            neighs.push(top);
        }

        if (r < n - 1) {
            Board right=new Board(blocks);
            right.blocks[r][c] = right.blocks[r + 1][c];
            right.blocks[r + 1][c] = 0;
            neighs.push(right);
        }
        
        if (c < n - 1) {
            Board down=new Board(blocks);
            down.blocks[r][c] = down.blocks[r][c + 1];
            down.blocks[r][c + 1] = 0;
            neighs.push(down);
        }

        return neighs;
	}

	// string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args){

    }

}