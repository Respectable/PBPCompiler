
public class Processor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser parser = new Parser();
		
		for(String s : args)
		{
			System.out.println("Parsing " + s);
			parser.parse(s);
		}
	}

}
