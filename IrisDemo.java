public class IrisDemo {
	public static void main(String[] args) {
		
		double[][] input = new double[140][4];
		double[][] target = new double[140][3];
		
		double[][] blindData = new double[10][4];
		double[][] blindTarget = new double[10][3];

		Utils.loadData("iris.txt", 0, 140, input, target);
		Utils.loadData("iris.txt", 140, 150, blindData, blindTarget);

		Network net = new Network(4, 15, 3, 4, Network.SIGMOID);
		net.setInput(input);
		net.setTarget(target);
		net.setLearnRate(.0010);

		net.trainAccuracy(.05, 1000);
		
		net.blindPredict(blindData, blindTarget);
		
		System.out.println("predicted");
		System.out.println(Matrix.toString(net.getOutput()));
		System.out.println("actual");
		System.out.println(Matrix.toString(blindTarget));
	}

}
