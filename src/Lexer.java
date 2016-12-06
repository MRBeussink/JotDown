/* This class represents the lexer.

 */
import java.io.*;

public class Lexer {

	
		
	private BufferedReader input;
	private String lexeme;
	private int position;
	LexerToken token;
	private String line = null;
	private boolean bold = false, italic = false, strike = false, qBlock = false, imgOpen = false, split = false, imgClose = false,
			linkOpen = false, linkClose = false;
	
	
			//The constructor 
	public Lexer (FileReader input) {
		this.input = new BufferedReader(input);
		position = 0;
		
	}
	
	
			//This Method Calls getLexeme() then matches the lexeme String to a enum w/wo value
	public LexerToken lex() {
		lexeme ="";
		
		getLexeme();
		
		
		//Matches the lexeme string to a case and Assigns an enum to Token
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
		case "code":
			token = LexerToken.Code;
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
		case "":
			token = LexerToken.EoF;
		default:
			token = LexerToken.Text;
			token.setValue(lexeme);
		}
		
		
		return token;
	}
	
	
	
							//This Method Obtains the Lexeme
	private void getLexeme() {
		if(line == null ){
			try {
				line = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			position = 0;
		}else if(line != null && position >= line.length()){
			lexeme += "newline";
			try {
				line = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			position = 0;
		}else if(line != null && position < (line.length() -1)){
				switch(line.charAt(position)){
					case '\\':
						lexeme += line.charAt(position +1);
						position += 2;
						while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){
								
								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
						}
						break;
					case '#':
						if(line.charAt(position +1) == (1 | 2 | 3 | 4 | 5 | 6)){
							lexeme += line.charAt(position) + line.charAt(position +1);
							position += 2;
						}else{
							lexeme += line.charAt(position);
							position++;
							while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
								
							}
						}
					
					
						break;
					case '*':
							if(line.charAt(position + 1)== '*' && line.charAt(position + 2) == '*'){
								lexeme += "***";
							}else if(line.charAt(position + 1) == ' ' && line.charAt(0) == '*' && position == 0){
								lexeme += "unordered";
								position++;
							}else{
								if(bold){
									if(line.charAt(position - 1) != ' '){
										lexeme += "bold";
										bold = false;
									}
								}else{
									for (int i = position +1;i < line.length(); i++){
										if(line.charAt(i) == '*'){
											if(line.charAt(i - 1) != ' '){
												if(line.charAt(position - 1) == ' ' || position == 0){
													lexeme += "bold";
													position++;
													bold = true;
												}
											}
										}
									}
								}
								if(!lexeme.equals("bold")){
									lexeme += line.charAt(position);
									position++;
									while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

										if(line.charAt(position) == '\\'){
											lexeme += line.charAt(position +1);
											position += 2;
										}else{
											lexeme += line.charAt(position);
											position++;
										}
									}
								}
							}
						break;
					case '_':
							if(italic){
								if(line.charAt(position -1) != ' '){
									lexeme += "_";
									position++;
									italic = false;
								}else{
									lexeme += line.charAt(position);
									position++;
								
									while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

										if(line.charAt(position) == '\\'){
											lexeme += line.charAt(position +1);
											position += 2;
										}else{
											lexeme += line.charAt(position);
											position++;
										}
									
									}
								}
							}else{
								if(line.charAt(position +1) != ' ' && line.charAt(position -1) == ' '){
									for(int i = position +1; i < line.length(); i++){
										if(line.charAt(i) == '_' && line.charAt(i - 1) != ' '){
											lexeme += "_";
											position++;
											italic = true;
										}
									}if(!italic){
										lexeme += line.charAt(position);
										position++;
										while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

											if(line.charAt(position) == '\\'){
												lexeme += line.charAt(position +1);
												position += 2;
											}else{
												lexeme += line.charAt(position);
												position++;
											}
											
										}
									}
								}else{
									lexeme += line.charAt(position);
									position++;
								
									while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

										if(line.charAt(position) == '\\'){
											lexeme += line.charAt(position +1);
											position += 2;
										}else{
											lexeme += line.charAt(position);
											position++;
										}
									
									}
								}
							}
						break;
					case '-':
							if (line.charAt(position +1) == ' ' && position == 0){
								lexeme += "ordered";
								position++;
							}else {
								if(strike){
									if (line.charAt(position -1) != ' '){
										lexeme += "strike";
										strike = false;
									}else{
										lexeme += line.charAt(position);
										position++;
										while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

											if(line.charAt(position) == '\\'){
												lexeme += line.charAt(position +1);
												position += 2;
											}else{
												lexeme += line.charAt(position);
												position++;
											}
											
										}
									}
								}else{
									for (int i = position +1; i < line.length();i++){
										if(line.charAt(i) == '-' && line.charAt(i - 1) != ' '){
											lexeme += "strike";
											position++;
											strike = true;
										}
									}if (!strike){
										lexeme += line.charAt(position);
										position++;
										while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

											if(line.charAt(position) == '\\'){
												lexeme += line.charAt(position +1);
												position += 2;
											}else{
												lexeme += line.charAt(position);
												position++;
											}
											
										}
									}
									
								}
							}
						break;
					case '>':
							if(line.charAt(position +1) == '>'){
								if(qBlock){
									lexeme += ">>";
								}else{
									for(int i = position + 2; i < line.length(); i++){
										if(line.charAt(i) == '>' && line.charAt(i + 1) == '>'){
											lexeme += ">>";
											qBlock = true;
										}
									}if (!qBlock){
										lexeme += line.charAt(position);
										position++;
										while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

											if(line.charAt(position) == '\\'){
												lexeme += line.charAt(position +1);
												position += 2;
											}else{
												lexeme += line.charAt(position);
												position++;
											}
										
										}
									}
								}
							}else{
								lexeme += line.charAt(position);
								position++;
								while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

									if(line.charAt(position) == '\\'){
										lexeme += line.charAt(position +1);
										position += 2;
									}else{
										lexeme += line.charAt(position);
										position++;
									}
									
								}
							}
						break;
					case '[':
							if(linkOpen){
								lexeme += line.charAt(position);
								position++;
								while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

									if(line.charAt(position) == '\\'){
										lexeme += line.charAt(position +1);
										position += 2;
									}else{
										lexeme += line.charAt(position);
										position++;
									}
									
								}
							}
							else{	
								for(int i = position + 1; i < line.length(); i++){
									if(line.charAt(i) == ':'){
										split = true;
									}else if (line.charAt(i) == ']'){
										linkClose = true;
									}
									
								}if(split && linkClose && !imgOpen){
									lexeme += "[";
									position++;
									linkOpen = true;
									split = false;
									linkClose = false;
								}else{
									lexeme += line.charAt(position);
									position++;
									while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

										if(line.charAt(position) == '\\'){
											lexeme += line.charAt(position +1);
											position += 2;
										}else{
											lexeme += line.charAt(position);
											position++;
										}
										
									}
								}
								
							}
						break;
					case '\'':
						if(line.charAt(position -1) == ' '){
							for (int i = position +1; i < line.length(); i++){
								if(line.charAt(i) == '\''){
									lexeme += "code";
									position++;
								}
							}
						}else{
							lexeme += line.charAt(position);
							if(position +1 < line.length()){
								position++;
							}
							while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position <= (line.length() -1)){

								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
								
							}
						}
							
						break;
					case '{':
						if(imgOpen){
							lexeme += line.charAt(position);
							position++;
							while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
								
							}
						}
						else{	
							for(int i = position + 1; i < line.length(); i++){
								if(line.charAt(i) == ':'){
									split = true;
								}else if (line.charAt(i) == ']'){
									imgClose = true;
								}
								
							}if(split && imgClose && !linkOpen){
								lexeme += "{";
								position++;
								imgOpen = true;
								split = false;
								imgClose = false;
							}else{
								lexeme += line.charAt(position);
								position++;
								while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

									if(line.charAt(position) == '\\'){
										lexeme += line.charAt(position +1);
										position += 2;
									}else{
										lexeme += line.charAt(position);
										position++;
									}
									
								}
							}
							
						}
						break;
					case ']':
						if (linkOpen && split){
							lexeme += "]";
							position++;
							split = false;
							imgOpen = false;
						}else{
							lexeme += line.charAt(position);
							position++;
							while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
								
							}
						}
						
						break;
					case '}':
						if (imgOpen && split){
							lexeme += "}";
							position++;
							split = false;
							imgOpen = false;
						}else{
							lexeme += line.charAt(position);
							position++;
							while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
								
							}
						}
						break;
					case ':':
						if(imgOpen || linkOpen){
							lexeme += ":";
							position++;
							split = true;
						}else{
							lexeme += line.charAt(position);
							position++;
							while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position < line.length()){

								if(line.charAt(position) == '\\'){
									lexeme += line.charAt(position +1);
									position += 2;
								}else{
									lexeme += line.charAt(position);
									position++;
								}
								
							}
						}
						break;
					default:
						lexeme += line.charAt(position);
						if(position +1 < line.length()){
							position++;
						}
						while((line.charAt(position) != ('#'| '*' | '-' | '_'| '>' | '[' | '{' | '}' | ']' | ':') || (line.charAt(position - 1) == '\\')) && position <= (line.length() -1)){

							if(line.charAt(position) == '\\'){
								lexeme += line.charAt(position +1);
								position += 2;
							}else{
								lexeme += line.charAt(position);
								position++;
							}
							
						}
						break;
			}
			
		}
		
	}
	
}
