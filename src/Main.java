import java.io.IOException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Needs to take in two command line arguments 
		if (args.length != 2){
			System.out.println("Needs two command line arguments");
		} else {
			//first argument is training set, second argument is test set
			DataReader reader = new DataReader(args[0], args[1]);
		}
	}

}
