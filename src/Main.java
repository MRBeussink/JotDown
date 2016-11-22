import java.io.*;

public class Main {

	 public static void main(String[] args) throws IOException {
		 
		String template;
		
		template = args[1];
		String outputfile = null;
		
		int i = 0;
		while(template.charAt(i) != '.'){
			outputfile = outputfile + template.charAt(i);
			i++;
		}
		BufferedReader infile = new BufferedReader(new FileReader(args[1]));
		PrintWriter outfile = new PrintWriter(new FileWriter(outputfile + ".html"));
		
		 String line;
		 
		 while((line = infile.readLine()) != null){
			 
			 
			 
		 }
		 
		 
		 
		 
		 
	 }
}
