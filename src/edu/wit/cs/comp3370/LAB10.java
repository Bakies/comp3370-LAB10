package edu.wit.cs.comp3370;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/* Calculates the reconstruction matrix of the Floyd-Warshall algorithm for
 * all-pairs shortest paths 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 10
 * 
 */

public class LAB10 {
	
	public static Vertex[][] FindAllPaths(Graph g) {
		int n = g.getVertices().length;
		double[][] dist = new double[n][n];
		Vertex[][] next = new Vertex[n][n];

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				dist[x][y] = Double.POSITIVE_INFINITY;
			}
		}
		for (Vertex v : g.getVertices()) {
			dist[v.ID][v.ID] = 0;
			next[v.ID][v.ID] = null;
		}

		for (Edge e : g.getEdges()) {
			dist[e.src.ID][e.dst.ID] = e.cost;
			next[e.src.ID][e.dst.ID] = e.dst;
		}

		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}

		return next;
	}

	private static void printTable(Object[][] os) {
		for (Object[] c : os) {
			for (Object o : c) {
				System.out.printf("%5s ", o);
			}
			System.out.println();
		}
	}

	private static void printDoubleTable(double[][] ds) {
		for (double[] c : ds) {
			for (double d : c) {
				System.out.printf("%5f ", d);
			}
			System.out.println();
		}

	}
	private static double dist(Vertex s, Vertex d) {
		return Math.sqrt(Math.pow(s.x - d.x, 2) + Math.pow(s.y - d.y, 2));
	}
	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/
	

	// reads in an undirected graph from a specific file formatted with one
	// x/y node coordinate per line:
	private static Graph InputGraph(String vFile, String eFile) {
		
		Graph g = new Graph();
		// vFile is list of (x coord, y coord) tuples
		try (Scanner f = new Scanner(new File(vFile))) {
			while(f.hasNextDouble())
				g.addVertex(f.nextDouble(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + vFile + ". Exiting.");
			System.exit(0);
		}
		
		// eFile is list of (src ID, dst ID, cost) tuples
		try (Scanner f = new Scanner(new File(eFile))) {
			while(f.hasNextInt())
				g.addEdge(f.nextInt(), f.nextInt(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + eFile + ". Exiting.");
			System.exit(0);
		}
		
		return g;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String vFile, eFile;
		
		System.out.printf("Enter <vertices file> <edges file>\n");
		System.out.printf("(e.g: verts/small1 edges/small1)\n");
		vFile = s.next();
		eFile = s.next();

		// read in vertices
		Graph g = InputGraph(vFile, eFile);
		
		Vertex paths[][] = FindAllPaths(g);
		
		System.out.println("next array:");
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths.length; j++) {
				if (paths[i][j] == null)
					System.out.printf("%3s","x");
				else
					System.out.printf("%3d",paths[i][j].ID);
			}
			System.out.println();
		}
		s.close();

	}

}
