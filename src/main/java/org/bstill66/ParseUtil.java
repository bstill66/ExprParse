package org.bstill66;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.bstill66.Expression.PrvLexer;
import org.bstill66.Expression.PrvParser;


public class ParseUtil  {
    public static class ParseErrorListener extends BaseErrorListener {
        public int errors = 0;

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg, RecognitionException e) {
            // Your custom error handling logic here
            //System.err.println("Error at line " + line + ":" + charPositionInLine + " - " + msg);
            errors += 1;
        }
    }

    private static ParseTree  parse(CharStream input) {

        // 2. Create a lexer to turn the character stream into a token stream
        PrvLexer lexer = new PrvLexer(input);

        // Create a CommonTokenStream from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create a parser from the token stream
        PrvParser parser = new PrvParser(tokens);

        // Setup Bail out strategy
        parser.removeErrorListeners();
        ParseErrorListener errlistener = new ParseErrorListener();
        parser.setErrorHandler(new BailErrorStrategy());

        lexer.removeErrorListeners();
        lexer.addErrorListener(errlistener);

        try {
            ParseTree ptree = parser.program();
            if (errlistener.errors == 0)
                return ptree;
        }
        catch (ParseCancellationException e) {
            return null; // An exception means validation failed
        }

        return null;
    }

    public static ParseTree parseFile(String fname) throws Exception {

        // 1. Create a CharStream from the input file
        CharStream input = CharStreams.fromFileName(fname);

        return parse(input);
    }

    public static ParseTree parseString(String program) {
        CharStream cs = CharStreams.fromString(program);

        return parse(cs);
    }
}
