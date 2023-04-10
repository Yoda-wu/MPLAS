/*** In The Name of Allah ***/
package com.scut.mplas.graphs.ast;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class type of Abstract Syntax (AS) nodes.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class ASNode {

    /**
     * Enumeration of different types for AS nodes.
     */
    public enum Type {
        ROOT        ("ROOT"),
        IMPORTS     ("IMPORTS"),
        IMPORT      ("IMPORT"),
        PACKAGE     ("PACKAGE"),
        NAME        ("NAME"),
        MODIFIER    ("MODIFIER"),
        CLASS       ("CLASS"),
        EXTENDS     ("EXTENDS"),
        IMPLEMENTS  ("IMPLEMENTS"),
        INTERFACE   ("INTERFACE"),
        STATIC_BLOCK("STATIC-BLOCK"),
        CONSTRUCTOR ("CONSTRUCTOR"),
        FIELD       ("FIELD"),
        TYPE        ("TYPE"),
        METHOD      ("METHOD"),
        RETURN      ("RETURN"),
        PARAMS      ("PARAMS"),
        BLOCK       ("BLOCK"),
        IF          ("IF"),
        CONDITION   ("COND"),
        THEN        ("THEN"),
        ELSE        ("ELSE"),
        VARIABLE    ("VAR"),
        INIT_VALUE  ("INIT"),
        STATEMENT   (""),
        FOR         ("FOR"),
        FOR_INIT    ("INIT"),
        FOR_UPDATE  ("UPDATE"),
        FOR_EACH    ("FOR-EACH"),
        IN          ("IN"),
        WHILE       ("WHILE"),
        DO_WHILE    ("DO-WHILE"),
        TRY         ("TRY"),
        RESOURCES   ("RESOURCES"),
        CATCH       ("CATCH"),
        FINALLY     ("FINALLY"),
        SWITCH      ("SWITCH"),
        CASE        ("CASE"),
        DEFAULT     ("DEFAULT"),
        LABELED     ("LABELED"),
        SYNC        ("SYNCHRONIZED"),
        //JavaScript的相关关键字
        VOID        ("VOID"),
        EMPTY       ("EMPTY"),
        FUNCTION    ("FUNCTION"),
        WITH        ("WITH"),
        CASEBLOCK   ("CASEBLOCK"),
        CASECLAUSES  ("CASECLAUSES"),
        //C++的相关关键字
        AUTO  ("AUTO"),
        BOOL  ("BOOL"),
        CHAR  ("CHAR"),
        BREAK ("BREAK"),
        CONST  ("CONST"),
        CONST_CASE  ("CONST_CASE"),
        DELETE  ("DELETE"),
        CONTINUE  ("CONTINUE"),
        DO  ("DO"),
        DOUBLE  ("DOUBLE"),
        DYNAMIC_CAST  ("DYNAMIC_CAST"),
        ENUM  ("ENUM"),
        EXPLICIT  ("EXPLICIT"),
        INT  ("INT"),
        LONG  ("LONG"),
        MUTABLE  ("MUTABLE"),
        NAMESPACE  ("NAMESPACE"),
        NEW  ("NEW"),
        OPERATOR  ("OPERATOR"),
        PRIVATE  ("PRIVATE"),
        EXPORT  ("EXPORT"),
        EXTERN  ("EXTERN"),
        FALSE  ("FALSE"),
        FLOAT  ("FLOAT"),
        FRIEND  ("FRIEND"),
        GOTO  ("GOTO"),
        INLINE  ("INLINE"),
        PRITECTED  ("PRITECTED"),
        PUBILC  ("PUBLIC"),
        REGISTER  ("REGISTER"),
        REINTERPRET_CAST  ("REINTERPRET_CAST"),

        ASM  ("ASM"),
        // ---------------------- RUBY ----------------------
        RUBY_EXPRESSION ("EXPRESSION"),
        RUBY_TERMINATOR("terminator"),
        RUBY_FUNCTION_DEF("function_definition"),
        RUBY_FUNCTION_INLINE("function_inline_call"),
        RUBY_REQUIRE_BLOCK("require_block"),
        RUBY_PIR_INLINE("pir_inline"),
        RUBY_RVALUE("rvalue"),
        RUBY_GLOBAL_GET("global_get"),
        RUBY_GLOBAL_SET("global_set"),
        RUBY_GLOBAL_RESULT("global_result"),
        RUBY_GLOBAL_ID("global_id")

        ;
        public final String label;

        private Type(String lbl) {
            label = lbl;
        }

        @Override
        public String toString() {
            return label;
        }
    }


    private Map<String, Object> properties;

    public ASNode(Type type) {
        properties = new LinkedHashMap<>();
        setLineOfCode(0);
        setType(type);
    }

    public final void setType(Type type) {
        properties.put("type", type);
    }

    public final Type getType() {
        return (Type) properties.get("type");
    }

    public final void setLineOfCode(int line) {
        properties.put("line", line);
    }

    public final int getLineOfCode() {
        return (Integer) properties.get("line");
    }

    public final void setCode(String code) {
        properties.put("code", code);
    }

    public final String getCode() {
        return (String) properties.get("code");
    }

    public final void setNormalizedCode(String normal) {
        if (normal != null)
            properties.put("normalized", normal);
    }

    public final String getNormalizedCode() {
        String normalized = (String) properties.get("normalized");
        if (normalized != null && !normalized.isEmpty())
            return normalized;
        return (String) properties.get("code");
    }

    public final void setProperty(String key, Object value) {
        properties.put(key.toLowerCase(), value);
    }

    public Object getProperty(String key) {
        return properties.get(key.toLowerCase());
    }

    public Set<String> getAllProperties() {
        return properties.keySet();
    }

    @Override
    public String toString() {
        String code = getCode();
        if (code == null || code.isEmpty())
            return getType().label;
        if (getType().label.isEmpty())
            return getLineOfCode() + ":  " + code;
        return getType().label + ": " + code;
    }
}
