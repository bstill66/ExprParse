package org.bstill66.Expression;

import org.junit.jupiter.api.Test;

import static org.bstill66.Expression.TestUtil.parseString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestParse {

    @Test
    public void parseList() {

    }

    @Test
    public void parseNumber() {

    }

    @Test
    public void testString() {

    }

    @Test
    public void parseEidr() {

    }

    @Test
    public void parseLike() {
        assertNotNull(parseString("x ~= \"regex\""));
    }
}
