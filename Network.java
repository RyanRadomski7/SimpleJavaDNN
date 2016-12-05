public class Network {
	public static final int SIGMOID = 0;
	public static final int TANH = 1;
	
	private int transfer;
	
	private int numLayers;
	private int dataSize;
	private int inputSize;
	private int hiddenSize;
	private int outputSize;
	private double learnRate = 1; //learn rate of 1 means learn rate will have no effect on results
	
	private double dataSetIn[][]; //indices are [data sample][input]
	private double dataSetOut[][]; //indices are [data sample][output]
	
	//indices are [layer][data sample][neuron in layer]
	private double inputs[][][];
	private double errors[][][];
	private double deltas[][][];
	private double outs[][][];
	private double dOuts[][][];
	
	//indices are [layer][current layer neuron][next layer neuron]
	private double weightChanges[][][];
	private double connections[][][]; 	
	
	public Network(int inputSize, int hiddenSize, int outputSize, int layers, int transferFunction) {
		if(layers < 3) System.err.println("Network must have at least 3 layers");

		this.transfer = transferFunction;
		
		this.inputSize = inputSize;
		this.hiddenSize = hiddenSize;
		this.outputSize = outputSize;
		this.numLayers = layers;
		
		connections = new double[layers][][];
		weightChanges = new double[layers][][];
		
		connections[0] = new double[inputSize][hiddenSize];
		weightChanges[0] = new double[inputSize][hiddenSize];
		for(int i = 1; i < layers-2; i++) {
			connections[i] = new double[hiddenSize][hiddenSize];
			weightChanges[i] = new double[hiddenSize][hiddenSize];
		}
		connections[layers-2] = new double[hiddenSize][outputSize];
		weightChanges[layers-2] = new double[hiddenSize][outputSize];
		
		inputs = new double[numLayers][][];
		outs = new double[numLayers][][];
		dOuts = new double[numLayers][][];
		errors = new double[numLayers][][];
		deltas = new double[numLayers][][];
		
		initConnections();
	}

	public void trainAccuracy(double accuracy, int printFrequency) {
		double totalError = 1;
		int count = 0;
		while(true) {
			propForward();
			propBackward();
			updateWeights();
			if(count == printFrequency) {
				count = 0;
				totalError = totalError();
				System.out.println(totalError);
				if(totalError < accuracy) break;
			}
			count++;
		}
	}

	public double blindPredict(double[][] inputs, double[][] targets) {
		this.setInput(inputs);
		this.setTarget(targets);
		propForward();
		return totalError();
	}
	
	private void initConnections() {
		for(int cl = 0; cl < connections.length-1; cl++)
			Matrix.matrix2dRandomize(connections[cl]);
	}

	public void setInput(double[][] set) {
		this.dataSetIn = set;
		if(set[0].length != inputSize) {
			System.err.println("Dimensions of data set do not math up with network dimensions. Network inputs: " + inputs[0].length + " data set inputs " + set[0].length);
			return;
		}
		
		this.dataSize = set.length;
		int cl;
		
		//update input layer
		inputs[0] = new double[dataSize][inputSize];
		outs[0] = new double[dataSize][inputSize];
		dOuts[0] = new double[dataSize][inputSize];
		errors[0] = new double[dataSize][inputSize];
		deltas[0] = new double[dataSize][inputSize];
		
		//update hidden layers
		for(cl = 1; cl < numLayers-1; cl++) {
			inputs[cl] = new double[dataSize][hiddenSize];
			outs[cl] = new double[dataSize][hiddenSize];
			dOuts[cl] = new double[dataSize][hiddenSize];
			errors[cl] = new double[dataSize][hiddenSize];
			deltas[cl] = new double[dataSize][hiddenSize];
		}
		
		//update output layer
		inputs[cl] = new double[dataSize][outputSize];
		outs[cl] = new double[dataSize][outputSize];
		dOuts[cl] = new double[dataSize][outputSize];
		errors[cl] = new double[dataSize][outputSize];
		deltas[cl] = new double[dataSize][outputSize];
		
		//add data set to input layer
		for(int di = 0; di < dataSize; di++) {
			for(int cn = 0; cn < inputSize; cn++) {
				outs[0][di][cn] = dataSetIn[di][cn];
			}
		}
	}
	
	public void setTarget(double[][] set) {
		if(set.length != outs[0].length) {
			System.err.println("Dimensions of data set do not math up with network dimensions. Network outputs: " + outs[0].length + " data set outputs " + set.length);
			return;
		}

		this.dataSetOut = set;
	}
	
	public double totalError() {
		double sum = 0;
		int li = numLayers-1;
		
		for(int i = 0; i < dataSize; i++)
			for(int j = 0; j < outputSize; j++) sum += Math.abs(errors[li][i][j]);
	
		return sum / (dataSize*outputSize);
	}
	
	public void setLearnRate(double lr) {
		this.learnRate = lr;
	}
	
	public double[][] getOutput() {
		return outs[numLayers-1];
	}
	
	private void updateWeights() {
		for(int cl = 0; cl < numLayers-1; cl++) {
			Matrix.matrix2dTransposeNormDot(outs[cl], deltas[cl+1], weightChanges[cl]);
			Matrix.matrix2dAddScale(connections[cl], weightChanges[cl], connections[cl], learnRate);
		}
	}
	
	private void propBackward() { 
		int lastIndex = numLayers-1;
		Matrix.matrix2dSubtract(dataSetOut, outs[lastIndex], errors[lastIndex]);
		Matrix.matrix2dMult(errors[lastIndex], dOuts[lastIndex], deltas[lastIndex]);
		
		for(int cl = lastIndex-1; cl > 0; cl--) {
			Matrix.matrix2dNormTransposeDot(deltas[cl+1], connections[cl], errors[cl]);
			Matrix.matrix2dMult(errors[cl+1], dOuts[cl], deltas[cl]);
		}
	}

	private void propForward() {
		if(dataSetIn == null) {
			System.err.println("call setInput to initialize input data");
			return;
		}
		
		for(int cl = 1; cl < numLayers; cl++) {
			Matrix.matrix2dDot(outs[cl-1], connections[cl-1], inputs[cl]);
			switch(transfer) {
				case SIGMOID:
					Matrix.matrix2dSmoid(inputs[cl], outs[cl]);
					Matrix.matrix2dSmoidDeriv(outs[cl], dOuts[cl]);
					break;
				case TANH:
					Matrix.matrix2dTanh(inputs[cl], outs[cl]);
					Matrix.matrix2dTanhDeriv(outs[cl], dOuts[cl]);
					break;
			}
		}
	}
}
