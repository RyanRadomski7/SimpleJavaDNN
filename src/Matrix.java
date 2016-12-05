import java.util.Random;

/*
 * The matrix class is a set of matrix math functions designed to be as fast as possible given 2d arrays
 * Functions are void and instead write directly to an input matrix
 */
public class Matrix {
	//TODO add functionality to do matrix math on matrices stored in 1d arrays for more efficient caching
	//     add functionality for multithreading for speedups on large matrices

	private static Random rand = new Random();
	
	//subtracts each corresponding element of m1 and m2 and stores the resutl in m3
	public static void matrix2dAdd(double[][] m1, double[][] m2, double[][] m3) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m3[i][j] = m1[i][j] + m2[i][j];
	}
	
	//subtracts each corresponding element of m1 and m2 and stores the result in m3
	public static void matrix2dSubtract(double[][] m1, double[][] m2, double[][] m3) {
			for(int i = 0; i < m1.length; i++)
				for(int j = 0; j < m1[i].length; j++) m3[i][j] = m1[i][j] - m2[i][j];
	}
	
	//adds each corresponding element in m1 and m2 and scales by a scalar then stores the result in m3
	public static void matrix2dAddScale(double[][] m1, double[][] m2, double[][] m3, double scale) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m3[i][j] = m1[i][j] + m2[i][j]*scale;
	}
	
	//sets every element in m1 to a random double between -1 and 1
	public static void matrix2dRandomize(double[][] m1) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m1[i][j] = 2*rand.nextDouble() - 1;
	}
	
	//stores the dot product of m1 and m2 in m3
	public static void matrix2dDot(double[][] m1, double[][] m2, double[][] m3) {
		double sum = 0;

		for(int i = 0; i < m1.length; i++) {
			for(int j = 0; j < m2[0].length; j++) {
				sum = 0;
				for(int k = 0; k < m1[0].length; k++) sum += m1[i][k] * m2[k][j];
				m3[i][j] = sum;
			}
		}
	}
	
	//computes the sigmoid of every element in m1 and stores it in the corresponding element in m2
	public static void matrix2dSmoid(double[][] m1, double[][] m2) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m2[i][j] = 1 / (1 + Math.exp(-m1[i][j]));
	}
	
	//@param m1, a matrix full of sigmoid results
	//@param m2, a matrix to store the derivative of the sigmoid results
	public static void matrix2dSmoidDeriv(double[][] m1, double[][] m2) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m2[i][j] = m1[i][j] * (1 - m1[i][j]);
	}

	//computes the hyperbolic tangent of every element in m1 and stores the result in m2
	public static void matrix2dTanh(double[][] m1, double[][] m2) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m2[i][j] = Math.tanh(m1[i][j]);
	}
	
	//@param m1, a matrix of hyperbolic tangent results
	//@param m2, a matrix to store the derivative hyperbolic tangents
	public static void matrix2dTanhDeriv(double[][] m1, double[][] m2) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m2[i][j] = 1 - Math.pow(m1[i][j], 2);
	}
	
	//multiplies every corresponding element of m1 and m2 and stores the product in m3
	public static void matrix2dMult(double[][] m1, double[][] m2, double[][] m3) {
		for(int i = 0; i < m1.length; i++)
			for(int j = 0; j < m1[i].length; j++) m3[i][j] = m1[i][j] * m2[i][j];
	}

	//takes the dot product of m1 and the transpose of m2 and stores the result in m3
	public static void matrix2dTransposeNormDot(double[][] m1, double[][] m2, double[][] m3) {
		double sum;
		
		for(int i = 0; i < m1[0].length; i++) {
			for(int j = 0; j < m2[0].length; j++) {
				sum = 0;
				for(int k = 0; k < m1.length; k++) sum += m1[k][i] * m2[k][j];
				m3[i][j] = sum;
			}
		}
	}
	
	//takes the dot product of m1 and the transpose of m2 and stores the result in m3
	public static void matrix2dNormTransposeDot(double[][] m1, double[][] m2, double[][] m3) {
		double sum = 0;
		
		for(int i = 0; i < m1.length; i++) {
			for(int j = 0; j < m2.length; j++) {
				sum = 0;
				for(int k = 0; k < m1[0].length; k++) sum += m1[i][k] * m2[j][k];
				m3[i][j] = sum;
			}
		}
	}
	
	//parses a 2x2 matrix as a string in a readable format
	public static String toString(double[][] m) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i, j;

		for(i = 0; i < m.length-1; i++) {
			for(j = 0; j < m[i].length; j++) sb.append(Math.round(m[i][j]) + ", ");
			sb.append("\n ");
		}
			
		for(j = 0; j < m[i].length-1; j++) sb.append(Math.round(m[i][j]) + ", ");
		sb.append(Math.round(m[i][j]) + "]");
		
		return sb.toString();
	}
}
