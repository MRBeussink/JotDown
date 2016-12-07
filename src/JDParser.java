import java.lang.annotation.IncompleteAnnotationException;
import java.util.Vector;


/**
 * Created by Mark on 12/6/16.
 */
public class JDParser {

    private JDLexer lexer;
    private Vector<JDLexerToken> tokens;
    private int currentPosition;
    private int nextPosition;
    private StringBuilder builder;
    
    public JDParser(JDLexer lexer) {
        this.lexer = lexer;
    }

    public String parse() {

        JDLexerToken token; // = lexer.lex();

         tokens = new Vector<JDLexerToken>();
         
        /*
        do {
            token = lexer.lex();

            System.out.print(token.name() + " ");

            if (token == JDLexerToken.NewLine) {
                System.out.println();
            }

        } while (token != JDLexerToken.EoF);

        return "";
        */
        do {
        	
        	token = lexer.lex();
        	tokens.addElement(token);
        	
        	System.out.print(token.name() + " ");

            if (token == JDLexerToken.NewLine) {
                System.out.println();
            }
        	
        }while(token != JDLexerToken.EoF);
        
        StringBuilder output = new StringBuilder();
         try {
        	 output.append(body());
         } catch (IncompleteBodyException a){
        	 System.out.print("body");
         }
        return output.toString();
    }
    private String body() throws IncompleteBodyException{
    	
    	StringBuilder output = new StringBuilder();
    	
    	try {
    		
    		output.append(line());
    	} catch (IncompleteLineException a) {
    		
    		throw new IncompleteBodyException("Could not complete body");
    	}
    	
    	return output.toString();
    }
    
    private String line() throws IncompleteLineException {
    	StringBuilder output = new StringBuilder();
    	
    	try {
    		output.append("<p>");
    		
    		
    		output.append(paragraph());
    		
    		if (tokens.elementAt(nextPosition) == JDLexerToken.NewLine){
    			output.append("</p>");
    		}
    		
    		else {
    			throw new IncompleteLineException("");
    		}
    	} catch (IncompleteParagraphException a) {
    		
    		try {
    			output.append(header());
    		}catch (IncompleteHeaderException b){
    			
    		}
    		throw new IncompleteLineException("Could not complete line");
    	}
    	
    	return output.toString();
    }
    
    private String paragraph() throws IncompleteParagraphException{
    	StringBuilder output = new StringBuilder();
    	
    	try {
    		output.append(text());
    	} catch (IncompleteTextException a) {
    		
    		throw new IncompleteParagraphException("Could not complete paragraph");
    	}
    	
    	
    	return output.toString();
    }
    
    private String header() throws IncompleteHeaderException{
    	StringBuilder output = new StringBuilder();
    	
    	String paragraph = "";
    	switch (tokens.elementAt(nextPosition)) {
    	
    		case H1:
    			try {
    				nextPosition++;
    				paragraph = paragraph();
    			} catch (IncompleteParagraphException a){
    				throw new IncompleteHeaderException("Could not complete header");
    			}
    			output.append("<h1>" + paragraph + "</h1>");
    			break;
    		case H2:
    			try {
    				nextPosition++;
    				paragraph = paragraph();
    			} catch (IncompleteParagraphException a){
    				throw new IncompleteHeaderException("Could not complete header");
    			}
    			output.append("<h2>" + paragraph + "</h2>");
    			break;
    		case H3:
    			try {
    				nextPosition++;
    				paragraph = paragraph();
    			} catch (IncompleteParagraphException a){
    				throw new IncompleteHeaderException("Could not complete header");
    			}
    			output.append("<h3>" + paragraph + "</h3>");
    			break;
    		case H4:
    			try {
    				nextPosition++;
    				paragraph = paragraph();
    			} catch (IncompleteParagraphException a){
    				throw new IncompleteHeaderException("Could not complete header");
    			}
    			output.append("<h4>" + paragraph + "</h4>");
    			break;
    		case H5:
    			try {
    				nextPosition++;
    				paragraph = paragraph();
    			} catch (IncompleteParagraphException a){
    				throw new IncompleteHeaderException("Could not complete header");
    			}
    			output.append("<h5>" + paragraph + "</h5>");
    			break;
    		case H6:
    			try {
    				nextPosition++;
    				paragraph = paragraph();
    			} catch (IncompleteParagraphException a){
    				throw new IncompleteHeaderException("Could not complete header");
    			}
    			output.append("<h6>" + paragraph + "</h6>");
    			break;
    		default:
    			throw new IncompleteHeaderException("Could not complete header");
    	}
    	
    	currentPosition = nextPosition;
    	return output.toString();
    }
    
    private String link(){
    	StringBuilder output = new StringBuilder();
    	
    	return "";
    }
    
    private String image(){
    	StringBuilder output = new StringBuilder();
    	
    	return "";
    }
    
    private String text() throws IncompleteTextException{
    	
    	StringBuilder output = new StringBuilder();
    	
    	if (tokens.elementAt(nextPosition) == JDLexerToken.Text){
    		
    		output.append(tokens.elementAt(nextPosition).getValue());
    		nextPosition++;
    		
    		try {
    			output.append(text());
    		} catch (IncompleteTextException a){
    			
    		}
    	}
    	
    	else {
    		throw new IncompleteTextException("Could not complete text");
    	}
    	
    	currentPosition = nextPosition;
    	return output.toString();
    }
    
	public class IncompleteLineException extends Exception {
		
		public IncompleteLineException(String message) {
			super(message);
		}
	}
	
public class IncompleteHeaderException extends Exception {
		
		public IncompleteHeaderException (String message) {
			super(message);
		}
	}
	
public class IncompleteParagraphException extends Exception {
	
	public IncompleteParagraphException(String message) {
		super(message);
	}
	
}

public class IncompleteTextException extends Exception {
	
	public IncompleteTextException(String message) {
		super(message);
	}
}
public class IncompleteBodyException extends Exception {
	
	public IncompleteBodyException(String message) {
		super(message);
}
}
}
