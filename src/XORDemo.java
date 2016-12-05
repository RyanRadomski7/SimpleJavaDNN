public class XORDemo{
	//XOR input
	static double[][] input = {
			{0, 0},
			{0, 1},
			{1, 0},
			{1, 1}
	};
	
	//XOR output
	static double[][] target = {
			{0},
			{1},
			{1},
			{0}
	};
	
	public static void main(String[] args) {
		Network net = new Network(2, 5, 1, 4, Network.TANH);
		net.setInput(input);
		net.setTarget(target);
		net.setLearnRate(.0100);

		net.trainAccuracy(.01, 10000);
		
		System.out.println("prediction");
		System.out.println(Matrix.toString(net.getOutput()));
		System.out.println("actual");
		System.out.println(Matrix.toString(target));
	}
}
