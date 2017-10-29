import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int gridLength;
	private boolean[] openCells;

	private int topVirtualCell;
	private int bottomVirtualCell;

	private WeightedQuickUnionUF uf;

	// Constrcutor to initialize variables;
	public Percolation(int n)
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException(" N <=0");
		}
		else
		{
			this.gridLength = n;
			this.topVirtualCell = 0;
			this.bottomVirtualCell = (n*n)+1;
			this.openCells = new boolean[bottomVirtualCell + 1];
			this.uf = new WeightedQuickUnionUF(bottomVirtualCell + 1);

			openCells[topVirtualCell] = true;
			openCells[bottomVirtualCell] = true;

		}

	}

	public void open(int i, int j)
	{
		int arrayIndex = convertCoordinate(i, j);
		
		openCells[arrayIndex] = true;

		
		if (i == 1)
		{
			uf.union(arrayIndex, topVirtualCell);
		}
		else
		{
			if (openCells[arrayIndex - gridLength]) 
			{
				uf.union(arrayIndex, arrayIndex - gridLength);
			}
		}
		
		if (i == gridLength) 
		{
			uf.union(arrayIndex, bottomVirtualCell);
		} 
		else 
		{
			if (openCells[arrayIndex + gridLength]) 
			{
				uf.union(arrayIndex, arrayIndex + gridLength);
			}
		}
		
		if (j > 1) 
		{
			if (openCells[arrayIndex - 1]) 
			{
				uf.union(arrayIndex,  arrayIndex - 1);
			}
		}
		
		// check cell to the right and union if also open
		if (j < gridLength) 
		{
			if (openCells[arrayIndex + 1]) 
			{
				uf.union(arrayIndex,  arrayIndex + 1);
			}
		}


	}

	public boolean isOpen(int i, int j)
	{
		int arrayIndex = convertCoordinate(i, j);
		return openCells[arrayIndex];
	}


	public boolean isFull(int i, int j)
	{
		int arrayIndex = convertCoordinate(i, j);
		return uf.connected(arrayIndex, topVirtualCell);
	}

	public int numberOfOpenSites() 
	{
		int count = 0;
		for (int i = 1; i <= gridLength*gridLength; i++)
		{
			if (openCells[i])
			{
				count += 1;
			}
		}

		return count;
	}


	public boolean percolates()
	{
		return uf.connected(topVirtualCell, bottomVirtualCell);
	}


	// Method to map 2d matrix to 1d index
	private int convertCoordinate(int i, int j)
	{
			checkForValidIndex(i, j);
			return (i-1)*gridLength+j;
	}


	// Method to check if index is valid or not
	private void checkForValidIndex(int i, int j) 
	{
		if (i < 1 || j < 1 || i > gridLength || j > gridLength) 
		{
			throw new IndexOutOfBoundsException("Index out of bound");
		}
	}

}