/* This class represents the lexer.

 */
import java.io.*;

public class Lexer {

	private BufferedReader input;
	private String lexeme;
	private int position;
	LexerToken token;
	private String line = null;
	
	public Lexer (FileReader input) {
		this.input = new BufferedReader(input);
		position = 0;
		
	}
	public LexerToken lex() throws IOException{
		lexeme ="";
		
		getLexeme();
		
		switch(lexeme){
		case "#1":
			token = LexerToken.Header;
			token.setValue("1");
			break;
		case "#2":
			token = LexerToken.Header;
			token.setValue("2");
			break;
		case "#3":
			token = LexerToken.Header;
			token.setValue("3");
			break;
		case "#4":
			token = LexerToken.Header;
			token.setValue("4");
			break;
		case "#5":
			token = LexerToken.Header;
			token.setValue("5");
			break;
		case "#6":
			token = LexerToken.Header;
			token.setValue("6");
			break;
		case "newline":
			token = LexerToken.NewLine;
			break;
		case "unordered":
			token = LexerToken.UList;
			break;
		case "bold":
			token = LexerToken.Bold;
			break;
		case "***":
			token = LexerToken.HrzRule;
			break;
		case "_":
			token = LexerToken.Ital;
			break;
		case "ordered":
			token = LexerToken.OList;
			break;
		case "strike":
			token = LexerToken.Strike;
			break;
		case ">>":
			token = LexerToken.QBlock;
			break;
		case "[":
			token = LexerToken.LinkOpen;
			break;
		case "{":
			token = LexerToken.ImgOpen;
			break;
		case "]":
			token = LexerToken.LinkClose;
			break;
		case "}":
			token = LexerToken.ImgClose;
			break;
		case ":":
			token = LexerToken.Split;
			break;
		default:
			token = LexerToken.Text;
			token.setValue(lexeme);
		}
		return token;
	}
	private void getLexeme() throws IOException {
		if(line == null ){
			line = input.readLine();
			position = 0;
		}else if(line != null && position >= line.length()){
			lexeme += "newline";
			line = input.readLine();
			position = 0;
		}else if(line != null){
				switch(line.charAt(position)){
					case '\\':
						lexeme += line.charAt(position) + line.charAt(position +1);
						position += 2;
						while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']') && (line.charAt(position - 1) != '\\')) && position < line.length()){
								lexeme += line.charAt(position);
								position++;
						}
						break;
					case '#':
						switch(line.charAt(position +1)){
						case '1':
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
							break;
						case '2':
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
							break;
						case '3':
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
							break;
						case '4':
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
							break;
						case '5':
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
							break;
						case '6':
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
							break;
						}
					
						break;
					case '*':
							if(line.charAt(position + 1)== '*' && line.charAt(position + 2) == '*'){
								lexeme += "***";
							}else if(line.charAt(position + 1) == ' '){
								lexeme += "unordered";
								position++;
							}else{
								lexeme += "bold";
								position++;
							}
						break;
					case '_':
							if(line.charAt(position +1) != ' '){
								lexeme += "_";
								position++;
							}
						break;
					case '-':
							if (line.charAt(position +1) == ' '){
								lexeme += "ordered";
								position++;
							}else{
								lexeme += "strike";
								position++;
							}
						break;
					case '>':
							if(line.charAt(position +1) == '>'){
								lexeme += ">>";
							}else{
								lexeme += line.charAt(position);
								position++;
								while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{') && (line.charAt(position - 1) != '\\')) && position < line.length()){
									lexeme += line.charAt(position);
									position++;
								}
							}
						break;
					case '[':
							lexeme += "[";
							position++;
						break;
					case '{':
							lexeme += "{";
							position++;
						break;
					case ']':
						lexeme += "]";
						position++;
						break;
					case '}':
						lexeme += "}";
						position++;
						break;
					case ':':
						lexeme += ":";
						position++;
						break;
					default:
						while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') && (line.charAt(position - 1) != '\\')) && position < line.length()){
							lexeme += line.charAt(position);
							position++;
						}
						break;
			}
			
		}
		
	}
	
}
