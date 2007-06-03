package com.ideanest.dscribe.opti;

import java.util.Random;

public class TestTSP {
	
	private static final Random random = new Random();
	
	static class TSPSolution implements Solution {
		final int[] route;
		final int[][] distances;
		double cost;
		TSPSolution(int[] flatDistances) {
			int numCities = ((int)Math.sqrt(flatDistances.length*8+1)-1)/2;
			route = new int[numCities];
			distances = new int[numCities][];
			int k=0;
			for(int i=0; i<numCities; i++) {
				route[i] = i;
				distances[i] = new int[i+1];
				for(int j=0; j<=i; j++) distances[i][j] = flatDistances[k++];
			}
			assert k == flatDistances.length;
		}
		private TSPSolution(int[] route, int[][] distances) {
			this.route = route;
			this.distances = distances;
		}
		int distance(int a, int b) {
			return a > b ? distances[a][b] : distances[b][a];
		}
		public double cost() {
			if (cost == 0.0) {
				for (int i=0; i<route.length; i++) cost += distance(route[i], route[(i+1) % route.length]);
			}
			return cost;
		}
		public TSPSolution randomNeighbor() {
			int[] newRoute = route.clone();
			int a = random.nextInt(route.length);
			int b;
			do {b = random.nextInt(route.length);} while(a == b);
			int c = newRoute[a]; newRoute[a] = newRoute[b]; newRoute[b] = c;
			return new TSPSolution(newRoute, distances);
		}
	}
	
	public static void main(String... args) {
		
		SimulatedAnnealingOptimizer<TSPSolution> sao =
			new SimulatedAnnealingOptimizer<TSPSolution>(
					new GeometricAnnealingStrategy(1000, 1, 0.95, 1000));
		
		final TSPSolution initialSolution = new TSPSolution(new int[]{
				  0, 257, 0, 187, 196, 0, 91, 228, 158, 0, 150, 112
				, 96, 120, 0, 80, 196, 88, 77, 63, 0, 130, 167, 59
				, 101, 56, 25, 0, 134, 154, 63, 105, 34, 29, 22, 0
				, 243, 209, 286, 159, 190, 216, 229, 225, 0, 185, 86, 124
				, 156, 40, 124, 95, 82, 207, 0, 214, 223, 49, 185, 123
				, 115, 86, 90, 313, 151, 0, 70, 191, 121, 27, 83, 47
				, 64, 68, 173, 119, 148, 0, 272, 180, 315, 188, 193, 245
				, 258, 228, 29, 159, 342, 209, 0, 219, 83, 172, 149, 79
				, 139, 134, 112, 126, 62, 199, 153, 97, 0, 293, 50, 232
				, 264, 148, 232, 203, 190, 248, 122, 259, 227, 219, 134, 0
				, 54, 219, 92, 82, 119, 31, 43, 58, 238, 147, 84, 53
				, 267, 170, 255, 0, 211, 74, 81, 182, 105, 150, 121, 108
				, 310, 37, 160, 145, 196, 99, 125, 173, 0, 290, 139, 98
				, 261, 144, 176, 164, 136, 389, 116, 147, 224, 275, 178, 154
				, 190, 79, 0, 268, 53, 138, 239, 123, 207, 178, 165, 367
				, 86, 187, 202, 227, 130, 68, 230, 57, 86, 0, 261, 43
				, 200, 232, 98, 200, 171, 131, 166, 90, 227, 195, 137, 69
				, 82, 223, 90, 176, 90, 0, 175, 128, 76, 146, 32, 76
				, 47, 30, 222, 56, 103, 109, 225, 104, 164, 99, 57, 112
				, 114, 134, 0, 250, 99, 89, 221, 105, 189, 160, 147, 349
				, 76, 138, 184, 235, 138, 114, 212, 39, 40, 46, 136, 96
				, 0, 192, 228, 235, 108, 119, 165, 178, 154, 71, 136, 262
				, 110, 74, 96, 264, 187, 182, 261, 239, 165, 151, 221, 0
				, 121, 142, 99, 84, 35, 29, 42, 36, 220, 70, 126, 55
				, 249, 104, 178, 60, 96, 175, 153, 146, 47, 135, 169, 0});
		
		int n = 200;
		double avg = 0;
		for (int i=0; i<n; i++) avg += sao.optimize(initialSolution).cost();
		System.out.println(avg/n);
	}
}