/* This class represents the lexer.

 */
import java.io.*;

public class Lexer {

	private FileReader input;
	private String lexeme;
	private int position;
	LexerToken token;
	
	public Lexer (FileReader input) {
		this.input = input;
		position = 0;
	}
	public void lex() throws IOException{
		
		lexeme ="";
		
		getLexeme();
		
	}
	public void getLexeme() throws IOException {
		int check1 = '\0', check2 = '\0', check3 = '\0';
		while((check1 = input.read()) != -1){
			switch(check1){
			case '#':
					token = LexerToken.Header;
				break;
			case '*':
				break;
			case '_':
				break;
			case '-':
				break;
			case '\'':
				break;
			case '>':
				break;
			case '[':
				break;
			case '{':
				break;
			}
			
			
		}
		
	}
	
}
