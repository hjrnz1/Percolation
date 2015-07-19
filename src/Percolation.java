public class Percolation {

	private int size;
	private int[][] grid;
	private int top;
	private int bot;
	private WeightedQuickUnionUF UF;
	private WeightedQuickUnionUF BW;

	public Percolation(int N) // create N-by-N grid, with all sites blocked
	{

		if (N <= 0) throw new IllegalArgumentException("N must be greater than 0");

		size = N;		
		grid = new int[N + 1][N + 1]; // add one to N so that indices are 1 to N
		UF = new WeightedQuickUnionUF((N * N) + 2); // add two for virtual sites at top and bottom
		BW = new WeightedQuickUnionUF((N * N) + 2); 

		top = Xyto1d(size, size) + 1; // UF array positions of top and bottoml
		bot = Xyto1d(size, size) + 2;
	}

	public void open(int i, int j) // open site (row i, column j) if it is not
	// open already
	{
		validateindex(i, j);
		grid[i][j] = 1;

		if (i - 1 != 0) {
			if (isOpen(i - 1, j)) {
				UF.union((Xyto1d(i, j)), (Xyto1d(i - 1, j)));
				BW.union((Xyto1d(i, j)), (Xyto1d(i - 1, j)));
			}
		}

		if (j - 1 != 0) {
			if (isOpen(i, j - 1)) {
				UF.union((Xyto1d(i, j)), (Xyto1d(i, j - 1)));
				BW.union((Xyto1d(i, j)), (Xyto1d(i, j - 1)));
			}
		}

		if (j + 1 <= size) {
			if (isOpen(i, j + 1)) {
				UF.union((Xyto1d(i, j)), (Xyto1d(i, j + 1)));
				BW.union((Xyto1d(i, j)), (Xyto1d(i, j + 1)));
			}
		}

		if (i + 1 <= size) {
			if (isOpen(i + 1, j)) {
				UF.union((Xyto1d(i, j)), (Xyto1d(i + 1, j)));
				BW.union((Xyto1d(i, j)), (Xyto1d(i + 1, j)));
			}
		}

		// if this site is in the top row, union with top virtual (N+1)
		if (i == 1) {

			UF.union((Xyto1d(i, j)), top);
			BW.union((Xyto1d(i, j)), top);
		}

		// if this site is in the bottom row, union with bottom virtual (N+2)
		if (i == size && !percolates()) {
			UF.union((Xyto1d(i, j)), bot);
		}

		if (i == size && isFull(i,j)) {
			BW.union((Xyto1d(i, j)), bot);
		}

	}

	public boolean isOpen(int i, int j) {
		validateindex(i, j);
		if (grid[i][j] == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull(int i, int j) // is site (row i, column j) full?
	{
		validateindex(i, j);
		if (BW.connected((Xyto1d(i, j)), top)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean percolates() { // does the system percolate?
		if (UF.connected(top, bot)) {
			return true;
		} else {
			return false;
		}
	}

	private void validateindex(int i, int j) {
		if (i <= 0 || i > size || j <= 0 || j > size)
			throw new IndexOutOfBoundsException("Index provided out of bounds");
	}

	private int Xyto1d(int i, int j) {
		int x = 0;
		x = i + (j * size) - (size + 1);
		if (x < 0 || x > ((size * size) - 1))
			throw new IndexOutOfBoundsException("Index " + x
					+ " provided out of bounds");
		return x;
	}

	public static void main(String[] args) {	
	}
}