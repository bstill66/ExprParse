package org.example;



import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.bstill66.Expression.PrvLexer;
import org.bstill66.Expression.PrvParser;
import org.bstill66.ParseUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.diogonunes.jcolor.Attribute.*;


public class ValidateExpression {

    final static AnsiFormat SUCCESS = new AnsiFormat(GREEN_TEXT());
    final static AnsiFormat FAILURE = new AnsiFormat(RED_TEXT());


    public static Namespace parse(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("ValidateExpression").build()
                .defaultHelp(true)
                .description("Validate Rule Expression");

        parser.addArgument("-s")
                .dest("stringFlag")
                .setDefault(false)
                .type(Boolean.class)
                .action(Arguments.storeTrue())
                .help("Interpret arguments as strings vs. files");

        parser.addArgument("infiles")
                .nargs("*")
                .help("Input Files to Validate");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        }
        catch (ArgumentParserException ex) {
            parser.handleError(ex);
            System.exit(1);
        }

        return ns;
    }



    private static String fmtColor(AnsiFormat fmt,String tmpl, Object...args) {
        String res = String.format(tmpl,args);
        return res;
    }

    public static void main(String[] argv) {


        // Parse arguments
        Namespace params = parse(argv);

        try {
            List<String> infiles = params.getList("infiles");

            for (String inpFile : infiles) {
                try {
                    ParseTree ptree = null;
                    if (params.getBoolean("stringFlag")) {
                        ptree = ParseUtil.parseString(inpFile);
                    }
                    else {
                        ptree = ParseUtil.parseFile(inpFile);
                    }

                    if (ptree != null) {
                        System.out.printf(fmtColor(SUCCESS,"Success: %s",inpFile));
                    }
                    else {
                        System.out.printf(fmtColor(FAILURE,"Failed : %s",inpFile));
                    }
                }
                catch (FileNotFoundException fnf) {
                    System.out.printf(fmtColor(FAILURE,"Failed : %s   - File Not Found", inpFile));
                }
                catch (IOException io) {
                    System.out.printf(fmtColor(FAILURE,"Failed : %s  - I/O Exception",inpFile));
                }

                System.out.println();
            }
        }
        catch (Exception _) {

        }

    }

}
