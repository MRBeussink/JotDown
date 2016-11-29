import java.io.*;

public class Main {

	 public static void main(String[] args) throws IOException {
		 
		String template;
		StringBuilder output;
		template = args[1];
		String outputfile = null;
		
		int i = 0;
		while(template.charAt(i) != '.'){
			outputfile = outputfile + template.charAt(i);
			i++;
		}
		FileReader infile = new FileReader(args[1]);
		FileWriter outfile = new FileWriter(outputfile + ".html");
		
		Parser prsr = new Parser(infile);
		prsr.parse();
		
		outfile = prsr.getOutput();
		 
		 
		 
		 
	 }
}
