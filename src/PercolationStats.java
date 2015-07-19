
public class PercolationStats {
	private double[] results; //array to collect percolation result
	private int exp;
	
	public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
	{
		
		if (N <= 0 || T <= 0) throw new IllegalArgumentException("Inputs cannot be <= 0 ");
		
		exp = T;		
		inputvalidation(N, T); //throw exception if either input is =< 0		
		results = new double[T];
		
		for (int x = 0; x < T; x++) {
			
			Percolation perc = new Percolation(N);
			
			double count = 0;
			double result = 0;
			
			while (!perc.percolates()) {
				
				
				int i = StdRandom.uniform(1, N+1);
				int j = StdRandom.uniform(1, N+1);
				
				if (!perc.isOpen(i, j)) {
					perc.open(i,j);
					count++;
				}
			}
			
			result = (count/(N*N));
			results[x] = result;	
			
		}
	}

	private void inputvalidation(int N, int T) {
		if (N <= 0 || T <= 0) { throw new IndexOutOfBoundsException("Index provided out of bounds"); } 
	}
	
	public double mean() {
		return StdStats.mean(results);
	}
	
   public double stddev() {
	   if (exp == 1) return Double.NaN;
	   return StdStats.stddev(results);
   }
   
   public double confidenceLo() {
	   return mean() - 1.96*stddev()/Math.sqrt(exp);
   }
   
   public double confidenceHi() {
	   return mean() + 1.96*stddev()/Math.sqrt(exp);
   }

   public static void main(String[] args) {
	    int N = StdIn.readInt();
	    int T = StdIn.readInt();
	   
	   	PercolationStats stats = new PercolationStats(N, T);
	    StdOut.printf("mean = %f\n", stats.mean());
	    StdOut.printf("stddev = %f\n", stats.stddev());
	    StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
	    
   };
   
}    // test client (described below)
	
