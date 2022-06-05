package com.yurirenko.jlox;

class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal + "@" + line;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        Token other = (Token) obj;
        return type == other.type
                && lexeme.equals(other.lexeme)
                && compareLiterals(other)
                && line == other.line;
    }

    private boolean compareLiterals(Token other) {
        if (literal == other.literal) {
            return true;
        }
        if (other.literal == null) {
            return false;
        }

        if (literal.getClass() == Double.class &&
                other.literal.getClass() == Double.class) {
            return literal.equals(other.literal);
        }

        return false;
    }
}
