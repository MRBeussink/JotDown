/* This enum contains tokens for the lexer
 */


public enum LexerToken {

    /*
        * headers
        * horizontal rule
        * emphasis/code
        * lists
        * blocks
        * Links
        * Images
        * Tables?
        * New Line
     */

    Header {
    	@Override public void setValue(String value) { this.value = value; }
    	@Override public String getValue() { return this.value; }
    },
    HrzRule,
    Ital, Bold, Strike, Code,
    UList, OList,
    CBlock, QBlock,
    LinkOpen, LinkClose,
    ImgOpen, ImgClose, Split,
    NewLine,
    Text {
    	@Override public void setValue(String value) { this.value = value; }
    	@Override public String getValue() { return this.value; }
    },
    EoF;
    
    protected String value;
	public void setValue(String value) { }
	public String getValue() { return null; }

    public static void main(String[] args){

        LexerToken tkn = LexerToken.Header;
        tkn.setValue("7");
        System.out.println(tkn.getValue());     // Output: 7

        tkn = LexerToken.HrzRule;
        System.out.println(tkn.getValue());     // Output: null
    }
}
