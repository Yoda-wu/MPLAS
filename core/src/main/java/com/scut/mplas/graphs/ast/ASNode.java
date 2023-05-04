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
        SOURCEELEMENTS("SOURCEELEMENTS"),
        SOURCEELEMENT("SOURCEELEMENT"),
        YIELD       ("YIELD"),
        VOID        ("VOID"),
        EMPTY       ("EMPTY"),
        FUNCTION    ("FUNCTION"),
        WITH        ("WITH"),
        CASEBLOCK   ("CASEBLOCK"),
        CASECLAUSES  ("CASECLAUSES"),
        IMPORTFROMBLOCK("IMPORTFROMBLOCK"),
        IMPORTNAMESPACE("IMPORTNAMESPACE"),
        IMPORTFROM   ("IMPORTFROM"),
        EXPORTFROMBLOCK("EXPORTFROMBLOCK"),
        MODULEITEMS  ("MODULEITEMS"),
        MODULEITEM  ("MODULEITEM"),
        ALIASNAME   ("ALIASNAME"),
        STATEMENTLIST("STATEMENTLIST"),
        THROW       ("THROW"),


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

        NAMESPACE  ("NAMESPACE"),
        NEW  ("NEW"),
        OPERATOR  ("OPERATOR"),
        PRIVATE  ("PRIVATE"),
        EXPORT  ("EXPORT"),

        FALSE  ("FALSE"),
        FLOAT  ("FLOAT"),
        FRIEND  ("FRIEND"),
        GOTO  ("GOTO"),
        INLINE  ("INLINE"),
        PRITECTED  ("PRITECTED"),
        PUBILC  ("PUBLIC"),

        REGISTER  ("REGISTER"),
        STATIC("STATIC"),
        THREAD_LOCAL("THREAD_LOCAL"),
        EXTERN  ("EXTERN"),
        MUTABLE  ("MUTABLE"),

        REINTERPRET_CAST  ("REINTERPRET_CAST"),
        EXCEPT("EXCEPT"),
        SPECIFIER("SPECIFIER"),
        VIRTUAL_SPECIFIER("VIRTUAL_SPECIFIER"),
        INITIALIZER("INITIALIZER"),
        HANDLERSEQ("HANDLERSEQ"),
        HANDLER("HANDLER"),
        TEMPLATE("TEMPLATE"),
        TEMPLATE_SPECIALIZATION("template_specialization"),
        ALIAS("ALIAS"),
        USING("USING"),
        STATIC_ASSERT("STATIC_ASSERT"),
        MESSAGE("MESSAGE"),
        BASE("BASE"),
        PROTECTED("PROTECTED"),
        BIT_FIELD("BIT_FIELD"),
        EXPRESSION("EXPRESSION"),

        BALANCEDTOKENSEQ("BALANCEDTOKENSEQ"),
        FORINITSTATEMENT("FORINITSTATEMENT"),
        FOR_RANGE_INIT("FOR_RANGE_INIT"),
        FOR_RANGE_INITER("FOR_RANGE_INITER"),
        CLASS_TYPE("CLASS_TYPE"),

        ASM("ASM"),
        // ---------------------- RUBY ----------------------
        RUBY_EXPRESSION("EXPRESSION"),
        RUBY_TERMINATOR("TERMINATOR"),
        RUBY_FUNCTION("FUNCTION"),
        RUBY_FUNCTION_DEF("FUNCTION_DEF"),
        RUBY_FUNCTION_CALL("FUNCTION_CALL"),
        RUBY_VAR("VAR"),
        RUBY_END("END"),
        RUBY_REQUIRE_BLOCK("REQUIRE_BLOCK"),
        RUBY_PIR_INLINE("PIR_INLINE"),
        RUBY_RVALUE("RVALUE"),
        RUBY_LVALUE("LVALUE"),
        RUBY_ARRAY_INIT("ARRAY_INIT"),
        RUBY_ARRAY_ASSIGN("ARRAY_ASSIGN"),
        RUBY_ASSIGNMENT("ASSIGNMENT"),

        RUBY_LITERAL_T("LITERAL"),
        RUBY_BOOL_T("BOOL"),
        RUBY_FLOAT_T("FLOAT"),
        RUBY_INT_T("INT"),
        RUBY_NIL_T("NIL"),
        RUBY_HASH_EXP("HASH_EXP"),
        RUBY_MODULE("MODULE"),
        RUBY_BEGIN("BEGIN"),
        RUBY_BEGIN_RESCUE("BEGIN_RESCUE"),
        RUBY_RESCUE("RESCUE"),
        RUBY_ENSURE("ENSURE"),
        RUBY_CASE("CASE"),
        RUBY_CASE_BODY("CASE_BODY"),
        RUBY_WHEN("WHEN"),

        RUBY_GLOBAL_GET("GLOBAL_GET"),
        RUBY_INSTANCE_GET("INSTANCE_GET"),
        RUBY_GLOBAL_SET("GLOBAL_SET"),
        RUBY_INSTANCE_SET("INSTANCE_SET"),
        RUBY_RAISE("RAISE"),
        RUBY_GLOBAL_RESULT("GLOBAL_RESULT"),
        RUBY_RESULT("RESULT"),
        RUBY_SUPER("SUPER"),
        RUBY_RETRY("RETRY"),
        RUBY_ELSEIF("ELSIF"),
        RUBY_UNLESS("UNLESS"),
        RUBY_LOOP_EXPR("LOOP_EXPR"),
        RUBY_GLOBAL_ID("GLOBAL_ID");
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
