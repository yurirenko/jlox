package com.yurirenko.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output_dir>");
            System.exit(64);
        }
        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));
    }

    private static void defineAst(
            String outputDir,
            String baseName,
            List<String> types
    ) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("package com.yurirenko.jlox;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class " + baseName + " {");
            defineVisitor(writer, baseName, types);
            int i = 0;
            for (String type : types) {
                String className = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, baseName, className, fields);
                if (i < types.size() - 1) {
                    writer.println();
                }
                i++;
            }
            writer.println();
            writer.println(" ".repeat(4) + "abstract <R> R accept(Visitor<R> visitor);");
            writer.println("}");
        }
    }

    private static void defineVisitor(
            PrintWriter writer,
            String baseName,
            List<String> types
    ) {
        writer.println(" ".repeat(4) + "interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println(" ".repeat(8) + "R visit" + typeName + baseName
                    + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }
        writer.println(" ".repeat(4) + "}");
    }

    private static void defineType(
            PrintWriter writer,
            String baseName,
            String className,
            String fieldList
    ) {
        writer.println(" ".repeat(4) + "static class " + className + " extends "
                + baseName + " {");
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            writer.println(" ".repeat(8) + "final " + field + ";");
        }
        writer.println();
        // Constructor
        writer.println("        " + className + "(" + fieldList + ") {");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println(" ".repeat(12) + "this." + name + " = " + name + ";");
        }

        writer.println(" ".repeat(8) + "}");
        writer.println();
        writer.println(" ".repeat(8) + "@Override");
        writer.println(" ".repeat(8) + "<R> R accept(Visitor<R> visitor) {" );
        writer.println(" ".repeat(12) + "return visitor.visit" + className + baseName + "(this);");
        writer.println(" ".repeat(8) + "}");
        writer.println(" ".repeat(4) + "}");
    }
}
