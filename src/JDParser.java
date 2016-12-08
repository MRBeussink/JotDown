import java.lang.annotation.IncompleteAnnotationException;
import java.util.Vector;


/**
 * Created by Mark on 12/6/16.
 */

import javax.sound.sampled.Line;
import java.io.*;

public class JDParser {

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    JDLexer lexer;
    JDLexerToken nextToken;

=======
>>>>>>> Stashed changes
    private JDLexer lexer;
    private Vector<JDLexerToken> tokens;
    private int currentPosition;
    private int nextPosition;
    private StringBuilder builder;
    
<<<<<<< Updated upstream
=======
>>>>>>> origin/master
>>>>>>> Stashed changes
    public JDParser(JDLexer lexer) {
        this.lexer = lexer;
    }

    public JDParser() { this.lexer = null; }


    public class JDParseError extends Exception {

        public JDParseError(String errorMessage) { super(errorMessage); }
    }

        public class BodyError extends Exception{

            public BodyError(String errorMessage){
                super(errorMessage);
            }
        }

        public class LineError extends Exception{

<<<<<<< Updated upstream
=======
            public LineError(String errorMessage){
                super(errorMessage);
            }
        }

        public class ParagraphError extends Exception{

            public ParagraphError(String errorMessage){
                super(errorMessage);
            }
        }

        public class HeaderError extends Exception{

            public HeaderError(String errorMessage){
                super(errorMessage);
            }
        }

        public class LinkError extends Exception{

            public LinkError(String errorMessage){
                super(errorMessage);
            }
        }

        public class ImageError extends Exception{

            public ImageError(String errorMessage){
                super(errorMessage);
            }
        }

        public class TextError extends Exception{
            public TextError(String errorMessage){
                super(errorMessage);
            }}


    // Entry point for recursive parsing
    public void parse() throws JDParseError {

        try {
            tryBody();
        } catch (BodyError e) {

            throw new JDParseError("Parsing error: " + e);
        }
    }


    private void tryBody() throws BodyError {

<<<<<<< HEAD
=======
>>>>>>> Stashed changes
         tokens = new Vector<JDLexerToken>();
         
        /*
>>>>>>> origin/master
        do {

            // get next token
            nextToken = lexer.lex();

            // check if beginning of <Line>
            if (doesTokenOpenForLine(nextToken)) {

                try {

                    tryLine();
                } catch (LineError e) {

                    throw new BodyError("<Body> --> " + e);
                }
            }

        } while (nextToken != JDLexerToken.EoF);
    }


    private void tryLine() throws LineError {

        if (doesTokenOpenForHeader(nextToken)) {

            try {
                tryHeader();
            } catch (HeaderError e) {

                throw new LineError("<Line> -x-> " + e);
            }

        }


        else if (doesTokenOpenForParagraph(nextToken)) {

            try {

                tryParagraph();
            } catch (ParagraphError e) {

                throw new LineError("<Line> -x-> " + e );
            }
        }


        else {
            switch (nextToken) {

                // hrzRule newLine
                case HrzRule:

                    nextToken = lexer.lex();

                    if (nextToken == JDLexerToken.NewLine)
                        nextToken = lexer.lex();

                    else
                        throw new LineError("hrzRule -x-> newLine");

                    break;

                // quoteBlock <Paragraph> newLine
                case QBlock:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForParagraph(nextToken)) {

                        try {

                            tryParagraph();
                        } catch (ParagraphError e) {

                            throw new LineError("<Line> --> quoteBlock -x-> " + e );
                        }

                        /*
                        if (nextToken == JDLexerToken.NewLine) {

                            nextToken = lexer.lex();
                        }


                        else
                            throw new LineError("quoteBlock --> <Paragraph> -x-> newLine ");
                        */
                    }

                    else
                        throw new LineError("quoteBlock -x-> <Paragraph>");

                    break;

                case CBlock:

                    nextToken = lexer.lex();

                    if (nextToken == JDLexerToken.NewLine) {

                        nextToken = lexer.lex();
                    }

                    else throw new LineError("codeBlock -x-> newLine");

                    if (doesTokenOpenForText(nextToken)) {

                        tryText();

                        //nextToken = lexer.lex();
                    }

                    else throw new LineError("codeBlock --> newLine -x-> <Text>");

                    if (nextToken == JDLexerToken.NewLine) {

                        nextToken = lexer.lex();

                    }

                    else throw new LineError("codeBlock --> newLine --> <Text> -x-> newLine");


                    break;

                case OList:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForParagraph(nextToken)) {

                        try {

                            tryParagraph();
                        } catch (ParagraphError e) {

                            throw new LineError("<Line> --> orderedList -x-> " + e );
                        }

                        /*
                        if (nextToken == JDLexerToken.NewLine) {

                            nextToken = lexer.lex();
                        }


                        else
                            throw new LineError("orderedList --> <Paragraph> -x-> newLine ");
                        */
                    }

                    else
                        throw new LineError("orderedList -x-> <Paragraph>");

                    break;

                case UList:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForParagraph(nextToken)) {

                        try {

                            tryParagraph();
                        } catch (ParagraphError e) {

                            throw new LineError("<Line> --> unorderedList -x->" + e );
                        }

                        /*
                        if (nextToken == JDLexerToken.NewLine) {

                            nextToken = lexer.lex();
                        }

                        else
                            throw new LineError("unorderedList --> <Paragraph> -x-> newLine ");
                         */
                    }

                    else
                        throw new LineError("unorderedList -x-> <Paragraph>");

                    break;

                default:
                    throw new LineError("Could not complete <Line>");
            }
        }
    }


    private void tryHeader() throws HeaderError {

        nextToken = lexer.lex();

        if (doesTokenOpenForParagraph(nextToken)) {

            //nextToken = lexer.lex();

            try {

                tryParagraph();
            } catch (ParagraphError e) {

                throw new HeaderError("<Header> -x-> " + e);
            }
            /*
            if (nextToken == JDLexerToken.NewLine) {

                nextToken = lexer.lex();
            }

            else throw new HeaderError("<Header> --> <Paragraph> -x-> newLine");
            */
        }
    }


    private void tryParagraph() throws ParagraphError {

        if (doesTokenOpenForLink(nextToken)) {

            //nextToken = lexer.lex();

            try {

                tryLink();
            } catch (LinkError e) {

                throw new ParagraphError("Paragraph -x-> " + e);
            }
        }

        else if (doesTokenOpenForImage(nextToken)) {

            //nextToken = lexer.lex();

            try {

                tryImage();
            } catch (ImageError e) {

                throw new ParagraphError("Paragraph -x-> " + e);
            }
        }

        else if (doesTokenOpenForText(nextToken)) {

            //nextToken = lexer.lex();

            tryText();
        }

        else {

            switch (nextToken) {

                case Bold:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("bold -x-> <Paragraph>");


                    if (nextToken == JDLexerToken.Bold) {

                        nextToken = lexer.lex();

                    } else throw new ParagraphError("bold --> <Paragraph> -x-> bold");

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("bold --> <Paragraph> --> bold -x-> <Paragraph?");

                    break;

                case Ital:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("ital -x-> <Paragraph>");


                    if (nextToken == JDLexerToken.Ital) {

                        nextToken = lexer.lex();

                    } else throw new ParagraphError("ital --> <Paragraph> -x-> ital");

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("ital --> <Paragraph> --> ital -x-> <Paragraph>");

                    break;

                case Strike:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("strike -x-> <Paragraph>");


                    if (nextToken == JDLexerToken.Strike) {

                        nextToken = lexer.lex();

                    } else throw new ParagraphError("strike --> <Paragraph> -x-> strike");

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("strike --> <Paragraph> --> strike -x-> <Paragraph>");

                    break;

                case Code:

                    nextToken = lexer.lex();

                    if (doesTokenOpenForText(nextToken)) {

                        //nextToken = lexer.lex();

                        tryText();

                    } else throw new ParagraphError("code -x-> <Text>");


                    if (nextToken == JDLexerToken.Code) {

                        nextToken = lexer.lex();

                    } else throw new ParagraphError("code --> <Text> -x-> code");

                    if (doesTokenOpenForParagraph(nextToken)) {

                        //nextToken = lexer.lex();

                        tryParagraph();

                    } else throw new ParagraphError("code --> <Text> --> code -x-> <Paragraph>");

                    break;


                case NewLine:

                    nextToken = lexer.lex();
                    break;

                default:

                    throw new ParagraphError("Could not complete paragraph.");
            }
        }
    }


    private void tryLink() throws LinkError {

        nextToken = lexer.lex();

        if (doesTokenOpenForText(nextToken)) {

            tryText();
        }

        else throw new LinkError("linkOpen -x-> <Text>");

        if (nextToken == JDLexerToken.Split) {

            nextToken = lexer.lex();
        }

        else throw new LinkError("linkOpen --> <Text> -x-> split");

        if (doesTokenOpenForText(nextToken)) {

            tryText();
        }

        else throw new LinkError("linkOpen --> <Text> --> split -x-> <Text>");

        if (nextToken == JDLexerToken.LinkClose) {

            nextToken = lexer.lex();
        }

        else throw new LinkError("linkOpen --> <Text> --> split -x-> <Text> -x-> linkClose");

        nextToken = lexer.lex();
    }

    private void tryImage() throws ImageError {

        nextToken = lexer.lex();

        if (doesTokenOpenForText(nextToken)) {

            tryText();
        }

        else throw new ImageError("imageOpen -x-> <Text>");

        if (nextToken == JDLexerToken.Split) {

            nextToken = lexer.lex();
        }

        else throw new ImageError("imageOpen --> <Text> -x-> split");

        if (doesTokenOpenForText(nextToken)) {

            tryText();
        }

        else throw new ImageError("imageOpen --> <Text> --> split -x-> <Text>");

        if (nextToken == JDLexerToken.ImgClose) {

            nextToken = lexer.lex();
        }

        else throw new ImageError("imageOpen --> <Text> --> split -x-> <Text> -x-> imageClose");

        nextToken = lexer.lex();
    }


    private void tryText() {

        nextToken = lexer.lex();

        if (doesTokenOpenForText(nextToken)) {

            tryText();
        }

        //nextToken = lexer.lex();
    }




    private boolean doesTokenOpenForLine(JDLexerToken t) {

        boolean result = false;

        if (doesTokenOpenForHeader(t))
            result = true;

        else if (doesTokenOpenForParagraph(t)){
            result = true;
        }

        else {

            switch (t) {

                case HrzRule:
                case QBlock:
                case CBlock:
                case OList:
                case UList:
                    result = true;
                    break;

                default:
                    break;
            }
        }

        return result;
    }


    private boolean doesTokenOpenForParagraph(JDLexerToken t) {

        boolean result = false;

        if (doesTokenOpenForImage(t))
            result = true;

        else if (doesTokenOpenForLink(t))
            result = true;

        else if (doesTokenOpenForText(t))
            result = true;

        else {

            switch (t) {

                case Bold:
                case Ital:
                case Strike:
                case Code:
                case NewLine:
                    result = true;
                    break;

                default:
                    break;
            }
        }

        return result;
    }



    private boolean doesTokenOpenForHeader(JDLexerToken t) {

        boolean result;

        switch (t) {

            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case H6:
                result = true;
                break;
                // return true;
            default:
                result = false;
                break;
        }

        return result;
    }


    private boolean doesTokenOpenForLink(JDLexerToken t) {

        return t == JDLexerToken.LinkOpen;
    }

    private boolean doesTokenOpenForImage(JDLexerToken t) {

        return t == JDLexerToken.ImgOpen;
    }


    private boolean doesTokenOpenForText(JDLexerToken t) {

        return t == JDLexerToken.Text;
    }


    public static void main (String[] args) {

        JDLexerToken testToken = JDLexerToken.Text;

        JDParser testParser = new JDParser();

        System.out.println(testParser.doesTokenOpenForLine(testToken));

        System.out.println(testParser.doesTokenOpenForParagraph(testToken));

        System.out.println(testParser.doesTokenOpenForHeader(testToken));

        System.out.println(testParser.doesTokenOpenForLink(testToken));

<<<<<<< HEAD
        System.out.println(testParser.doesTokenOpenForImage(testToken));

        System.out.println(testParser.doesTokenOpenForText(testToken));
=======
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
<<<<<<< Updated upstream
=======
>>>>>>> origin/master
>>>>>>> Stashed changes
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
