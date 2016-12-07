/* This enum contains tokens for the lexer
 */


public enum JDLexerToken {

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

    H1 {
        @Override public String getValue() {return "#1";}
    },

    H2 {
        @Override public String getValue() {return "#2";}
    },

    H3 {
        @Override public String getValue() {return "#3";}
    },

    H4 {
        @Override public String getValue() {return "#4";}
    },

    H5 {
        @Override public String getValue() {return "#5";}
    },

    H6 {
        @Override public String getValue() {return "#6";}
    },
    HrzRule {
        @Override public String getValue() { return "***"; }
    },
    Ital {
        @Override public String getValue() { return "_"; }
    },
    Bold {
        @Override public String getValue() { return "*"; }
    },
    Strike {
        @Override public String getValue() { return "-"; }
    },
    Code {
        @Override public String getValue() { return "`"; }
    },
    UList {
        @Override public String getValue() { return "* "; }
    },
    OList {
        @Override public String getValue() { return "#. "; }
    },
    CBlock {
        @Override public String getValue() { return "```"; }
    },
    QBlock {
        @Override public String getValue() { return ">>"; }
    },
    LinkOpen {
        @Override public String getValue() { return "["; }
    },
    LinkClose {
        @Override public String getValue() { return "]"; }
    },
    ImgOpen {
        @Override public String getValue() { return "{"; }
    },
    ImgClose {
        @Override public String getValue() { return "}"; }
    },
    Split {
        @Override public String getValue() { return ":"; }
    },
    NewLine {
        @Override public String getValue() { return "\n"; }
    },
    Text {
    	@Override public void setValue(String value) { this.value = value; }
    	@Override public String getValue() { return this.value; }
    },
    EoF;

    protected String value;
	public void setValue(String value) { }
	public String getValue() { return null; }

    /*
    public static void main(String[] args){

        JDLexerToken tkn = JDLexerToken.Header;
        tkn.setValue("7");
        System.out.println(tkn.getValue());     // Output: 7

        tkn = JDLexerToken.HrzRule;
        System.out.println(tkn.getValue());     // Output: null
    }
    */
}
