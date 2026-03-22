package org.bstill66.Expression;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.bstill66.ParseUtil;

public class TestUtil {
    protected static ParseTree parseString(String prog) {
       return ParseUtil.parseString(prog);
    }


}
