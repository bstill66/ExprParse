package org.example;


import com.beust.jcommander.Parameter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.bstill66.Expression.PrvLexer;
import org.bstill66.Expression.PrvParser;

import com.beust.jcommander.JCommander;

public class ValidateExpression {

    public static class Args {
        @Parameter(names = "-help", help = true)
        public boolean help;

        @Parameter(names = "-i",description="File name to validate")
        String inpFile = null;

        @Parameter(names = "-debug", description = "Debug mode")
        public boolean debug = false;
    }


    public boolean parseFile(String fname) throws Exception {

            // 1. Create a CharStream from the input file
            CharStream input = CharStreams.fromFileName(fname);

            // 2. Create a lexer to turn the character stream into a token stream
            PrvLexer lexer = new PrvLexer(input);

            // 3. Create a CommonTokenStream from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 4. Create a parser from the token stream
            PrvParser parser = new PrvParser(tokens);

            // 5. Start the parsing process by calling your grammar's entry point rule
            //    (e.g., 'prog' or 'startRule'). This returns a ParseTree.
            ParseTree tree = parser.program(); // Assuming 'prog' is the start rule in Expr.g4

            // 6. (Optional) Walk the parse tree using a listener or visitor to process the data
            // ParseTreeWalker walker = new ParseTreeWalker();
            // MyListener listener = new MyListener();
            // walker.walk(listener, tree);

        return false;
    }

    public static void main(String[] argv) {

        Args  args = new Args();
        JCommander jc = new JCommander(args);
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        // Parse arguments

        ValidateExpression  e = new ValidateExpression();
        try {
            e.parseFile(args.inpFile);
        }
        catch (Exception _) {

        }

    }

}
