
/**
 * Created by markbeussink on 11/29/16.
 */
public enum ParserState {

    Body {

        @Override
        protected ParserState transition(LexerToken token) {

            ParserState state = super.transition(token);

            if (state == null){
                switch (token) {

                    case HrzRule:
                        state = HorizontalRule;
                        break;

                    case Header:
                        state = Header;
                        break;

                    case Ital:
                        state = Ital;
                        break;

                    case Bold:
                        state =  Bold;
                        break;

                    case Strike:
                        state = Strike;
                        break;

                    case Code:
                        state = Code;
                        break;

                    case QBlock:
                        state = QuoteBlock;
                        break;

                    case CBlock:
                        state = CodeBlock;
                        break;

                    case OList:
                        state = OrderedList;
                        break;

                    case UList:
                        state = UnorderedList;
                        break;

                    case LinkOpen:
                        state = Link;
                        break;

                    case ImgOpen:
                        state = Image;
                        break;

                    default:
                        break;
                }
            }

            return state;
        }
    },

    HorizontalRule { },

    Header { },

    Ital {

        @Override public boolean allowsNesting() { return true; }

        @Override public boolean isClosingState(ParserState state) { return state.equals(this); }
    },

    Bold {

        @Override public boolean allowsNesting() { return true; }

        @Override public boolean isClosingState(ParserState state) { return state.equals(this); }
    },

    Strike {

        @Override public boolean allowsNesting() { return true; }

        @Override public boolean isClosingState(ParserState state) { return state.equals(this); }
    },

    Code {

        @Override public boolean allowsEmphasis() { return false; }

        @Override public boolean isClosingState(ParserState state) { return state.equals(this); }
    },

    QuoteBlock {

        @Override public boolean isClosingState(ParserState state) { return state.equals(QuoteBlock); }

        /*
        @Override public ParserState transition(LexerToken token) {

            switch (token) {
                // Emphasis

                case Ital:
                    return Ital;

                case Bold:
                    return Bold;

                case Strike:
                    return Strike;

                // Text
                case Text:
                    return QuoteBlockText;

                default:
                    return null;
            }
        }
        */
    },

    /*
    QuoteBlockText {

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

            }
        }
    },

    QuoteBlockClose {

        @Override public ParserState transition(LexerToken token) {

        }
    },

    */
    CodeBlock {

        @Override public boolean allowsEmphasis() { return false; }

        @Override public boolean isClosingState(ParserState state) { return state.equals(ParserState.CodeBlockClose); }

        @Override public ParserState transition(LexerToken token) {

            switch (token) {
                case Text:
                    return CodeBlockText;
                default:
                    return null;
            }
        }
    },


    CodeBlockText {

        @Override ParserState transition(LexerToken token) {

            switch (token) {
                case Text:
                    return CodeBlockText;
                case CBlock:
                    return CodeBlockClose;
            }
        }
    },

    CodeBlockClose {

        @Override ParserState transition(LexerToken token) {

        }
    },


    OrderedList { },

    UnorderedList { },

    Link {

        @Override public boolean isClosingState(ParserState state) { return state.equals(LinkClose); }

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case Text:
                    return LinkText;
                default:
                    return null;
            }
        }
    },

    LinkText {

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case Split:
                    return LinkSplit;
                default:
                    return null;
            }
        }
    },

    LinkSplit {

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case Text:
                    return LinkURL;
                default:
                    return null;
            }
        }
    },

    LinkURL{

        @Override public ParserState transition(LexerToken token) {

            switch (token) {
                case LinkClose:
                    return LinkClose;
                default:
                    return null;
            }
        }
    },

    LinkClose { },


    Image {

        @Override public boolean isClosingState(ParserState state) { return state.equals(ImageClose); }

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case Text:
                    return ImageText;
                default:
                    return null;
            }
        }
    },

    ImageText {

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case Split:
                    return ImageSplit;
                default:
                    return null;
            }
        }
    },

    ImageSplit {

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case Text:
                    return ImageURL;
                default:
                    return null;
            }
        }
    },

    ImageURL{

        @Override public ParserState transition(LexerToken token) {

            switch (token) {

                case ImgClose:
                    return LinkClose;
                default:
                    return null;
            }
        }
    },

    ImageClose { },

    Text {

        @Override protected ParserState transition(LexerToken token) {

            ParserState state = super.transition(token);

            if (token == null) {
                switch (token) {

                    case Ital:
                        return Ital;

                    case Bold:
                        return Bold;

                    case Strike:
                        return Strike;

                    case Code:
                        return Code;

                    case QBlock:
                        return QuoteBlock;

                    case CBlock:
                        return CodeBlock;

                    default:
                        break;
                }
            }

            return state;
        }
    },

    NewLine {

        @Override protected ParserState transition(LexerToken token) {
            return Body.transition(token);
        }
    },

    End { };

    protected ParserState transition(LexerToken token) {
        switch (token) {
            case Text:
                return Text;
            case NewLine:
                return NewLine;
            case EoF:
                return End;
            default:
                break;
        }

        return null;
    }

    private LexerToken token;

    public void setToken(LexerToken token) {
        this.token = token;
    }

    public LexerToken getToken() {
        return this.token;
    }

    public boolean allowsNesting() {
        return false;
    }

    public boolean allowsEmphasis() {
        return true;
    }

    public boolean isClosingState(ParserState state) {
        if (state.equals(NewLine) || state.equals(End))
            return true;

        return false;
    }


    // public class FSMTransitionException extends Exception { }
}
