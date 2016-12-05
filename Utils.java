import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static void loadData(String filename, int startRead, int endRead, double[][] inputList, double[][] outputList) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			@SuppressWarnings("unused")
			int i, j, cat, out;
			for(i = 0; i < startRead; i++) br.readLine();

			while(i < endRead) {
				line = br.readLine();
				String[] variables = (String[]) line.split("\t");
				for(j = 1; j < variables.length; j++)
					inputList[i-startRead][j-1] = Double.parseDouble(variables[j]);
				
				cat = Integer.parseInt(variables[0]);
			
				for(j = 0; j < outputList[0].length; j++) {
					out = Integer.parseInt(variables[0]);
					if(j == out) outputList[i-startRead][j] = 1;
					else outputList[i-startRead][j] = 0;
				}
				
				i++;
			}
			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
