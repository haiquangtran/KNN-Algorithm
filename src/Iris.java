
public class Iris {
	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private String type;
	
	
	public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String type){
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.type = type;
	}
	

	public double distanceTo(Iris other){
		double total = 0;
		total += (Math.pow(this.sepalLength-other.sepalLength, 2)/Math.pow(DataReader.sepalLengthRange, 2));
		total += (Math.pow(this.sepalWidth-other.sepalWidth, 2)/Math.pow(DataReader.sepalWidthRange, 2));
		total += (Math.pow(this.petalLength-other.petalLength, 2)/Math.pow(DataReader.petalLengthRange, 2));
		total += (Math.pow(this.petalWidth-other.petalWidth, 2)/Math.pow(DataReader.petalWidthRange, 2));
		
		//total = sqrt ((a - b)^2 / R^2)
		return  Math.sqrt(total);
	}
	
	public String getType(){
		return type;
	}
}
