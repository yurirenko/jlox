package com.yurirenko.jlox;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ScannerTest {
    private static final String inputString = """
            fn fibonacci(n) {
                if (n == 0) {
                    return 0;
                }
                // A comment
                if (n == 1) {
                    return 1;
                }
                /*
                if (n !== 20) {
                    return 2;
                }
                */
                return fibonacci(n - 1) + fibonacci(n - 2);
            }
            """;
    private static final ArrayList<Token> expectedTokens = new ArrayList<>();

    static {
        int line = 1;
        expectedTokens.add(new Token(TokenType.FN, "fn", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "fibonacci", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, line));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_BRACE, "{", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.IF, "if", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, line));
        expectedTokens.add(new Token(TokenType.EQUAL_EQUAL, "==", null, line));
        expectedTokens.add(new Token(TokenType.NUMBER, "0", 0.0, line));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_BRACE, "{", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.RETURN, "return", null, line));
        expectedTokens.add(new Token(TokenType.NUMBER, "0", 0.0, line));
        expectedTokens.add(new Token(TokenType.SEMICOLON, ";", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, line));
        // Newline
        line++;
        // Skip over comment
        line++;
        expectedTokens.add(new Token(TokenType.IF, "if", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, line));
        expectedTokens.add(new Token(TokenType.EQUAL_EQUAL, "==", null, line));
        expectedTokens.add(new Token(TokenType.NUMBER, "1", 1.0, line));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_BRACE, "{", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.RETURN, "return", null, line));
        expectedTokens.add(new Token(TokenType.NUMBER, "1", 1.0, line));
        expectedTokens.add(new Token(TokenType.SEMICOLON, ";", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, line));
        // Newline
        line++;
        // Skip over multi-line comment
        line += 5;
        // Newline
        expectedTokens.add(new Token(TokenType.RETURN, "return", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "fibonacci", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, line));
        expectedTokens.add(new Token(TokenType.MINUS, "-", null, line));
        expectedTokens.add(new Token(TokenType.NUMBER, "1", 1.0, line));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, line));
        expectedTokens.add(new Token(TokenType.PLUS, "+", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "fibonacci", null, line));
        expectedTokens.add(new Token(TokenType.LEFT_PAREN, "(", null, line));
        expectedTokens.add(new Token(TokenType.IDENTIFIER, "n", null, line));
        expectedTokens.add(new Token(TokenType.MINUS, "-", null, line));
        expectedTokens.add(new Token(TokenType.NUMBER, "2", 2.0, line));
        expectedTokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, line));
        expectedTokens.add(new Token(TokenType.SEMICOLON, ";", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, line));
        // Newline
        line++;
        expectedTokens.add(new Token(TokenType.EOF, "", null, line));
    }

    @Test
    void scanTokens() {
        List<Token> tokens = new Scanner(inputString).scanTokens();
        assertArrayEquals(expectedTokens.toArray(), tokens.toArray());
    }
}
