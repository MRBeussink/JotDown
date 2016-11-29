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

    Header,
    HrzRule,
    Ital, Bold, Strike, Code,
    UList, OList,
    CBlock, QBlock,
    LinkOpen, LinkClose,
    ImgOpen, ImgClose, Split,
    NewLine
}
