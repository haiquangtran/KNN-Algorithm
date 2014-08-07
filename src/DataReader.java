import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class DataReader {
	private String trainingName;
	private String testSet;
	private ArrayList<Iris> trainingSet; 
	public static double sepalLengthRange;
	public static double sepalWidthRange;
	public static double petalLengthRange;
	public static double petalWidthRange;

	public DataReader(String trainingSet, String testSet){
		this.trainingName = trainingSet;
		this.testSet = testSet;
		//Load Training set
		loadTrainingSet();
		//load Test set
		loadTestSet();
	}

	public void loadTestSet(){
		//Read in test set
		File file = new File(testSet);
		//Statistics
		int total = 0;
		int correct = 0;
		//Ask for user input
		System.out.println("Please input k value:");
		Scanner input = new Scanner(System.in);
		int kValue = input.nextInt();
		//Read Test file
		try {
			Scanner scan = new Scanner(new FileReader(file));
			while (scan.hasNext()){
				//Make a new unidentified flower - Select test set data point
				final Iris unknown = new Iris(scan.nextDouble(),
						scan.nextDouble(), scan.nextDouble(), scan.nextDouble(), scan.next());

				//Sort array list according to the new test point
				Collections.sort(trainingSet, new Comparator<Iris>() {
					@Override
					public int compare(Iris o1, Iris o2) {
						return Double.compare(unknown.distanceTo(o1),  unknown.distanceTo(o2));
					}
				});
				//K neighbours - String = type of the class , Integer = tally of them/count
				HashMap<String, Integer> neighbours = new HashMap<String, Integer>();

				//Check k neighbours to see whos closest and record the statistics
				if (kValue > trainingSet.size()){kValue = trainingSet.size();}
				for (int i=0; i < kValue; i++){
					//put all the k neighbours in map
					if (neighbours.get(trainingSet.get(i).getType()) == null){
						neighbours.put(trainingSet.get(i).getType(), 1);
					} else {
						neighbours.put(trainingSet.get(i).getType(), neighbours.get(trainingSet.get(i).getType()) + 1);
					}
				}

				//For classification
				int max = Integer.MIN_VALUE;
				String classification = null;
				//Classification of majority class
				for (Map.Entry<String, Integer> k: neighbours.entrySet())
				{
					//Set the majority class
					if (k.getValue() > max){
						max = k.getValue();
						classification = k.getKey();
					}
				}


				//Check classification is correct
				if (unknown.getType().equals(classification)){
					correct++;
				} else {
					System.out.println("Incorrect classification: " + classification +" should be :" + unknown.getType());
				}

				//total in data
				total++;
			}

			scan.close();
			//Accuracy percentage
			double accuracy = (double)correct/(double)total;
			//Print out the statistics
			System.out.println("k value is " + kValue);
			System.out.println("Correct " + correct + " out of " + total);
			System.out.println("Classification accuracy " + String.format("%.4f", accuracy*100) + " % (4 dp)");
		} catch (Exception e){
			e.printStackTrace();
		} 
	}

	public void loadTrainingSet() {
		//Read in training set
		File file = new File(trainingName);
		//Create training set
		trainingSet = new ArrayList<Iris>();
		//Figure out the range
		double sepLengthMax = Double.MIN_VALUE;
		double sepLengthMin = Double.MAX_VALUE;
		double petalLengthMax = Double.MIN_VALUE;
		double petalLengthMin = Double.MAX_VALUE;
		double petalWidthMax = Double.MIN_VALUE;
		double petalWidthMin = Double.MAX_VALUE;
		double sepWidthMax = Double.MIN_VALUE;
		double sepWidthMin = Double.MAX_VALUE;
		try {
			Scanner scan = new Scanner(new FileReader(file));

			//Read in data
			while (scan.hasNext()){
				//Parse
				double sLength = scan.nextDouble();
				double sWidth =  scan.nextDouble();
				double pLength = scan.nextDouble();
				double pWidth = scan.nextDouble();
				String type = scan.next();
				//Make a new flower 
				Iris flower = new Iris(sLength, sWidth, pLength, pWidth, type);
				//add it to the training set
				trainingSet.add(flower);

				//Find the ranges of the attributes
				if (sepLengthMax < sLength){
					sepLengthMax = sLength;
				}
				if (sepWidthMax < sWidth){
					sepWidthMax = sWidth;
				}
				if (petalLengthMax < pLength){
					petalLengthMax = pLength;
				}
				if (petalWidthMax < pWidth){
					petalWidthMax = pWidth;
				}
				if (petalLengthMin > pLength){
					petalLengthMin = pLength;
				}
				if (petalWidthMin > pWidth){
					petalWidthMin = pWidth;
				}
				if (sepLengthMin > sLength){
					sepLengthMin = sLength;
				}
				if (sepWidthMin > sWidth){
					sepWidthMin = sWidth;
				}
			}

			//Set the ranges
			sepalLengthRange = sepLengthMax - sepLengthMin;
			sepalWidthRange = sepWidthMax - sepWidthMin;
			petalLengthRange = petalLengthMax - petalLengthMin;
			petalWidthRange = petalWidthMax - petalWidthMin;

			scan.close();

		} catch (Exception e){
			e.printStackTrace();
		}
	}


}
