package com.scut.mplas.javascript;

public class JavaScriptField {

    public final String NAME;
    public final String TYPE;
    public final boolean STATIC;
    public final String MODIFIER;

    public JavaScriptField(String modifier, boolean isStatic, String type, String name) {
        NAME = name;
        TYPE = type;
        STATIC = isStatic;
        MODIFIER = modifier;
    }

    @Override
    public String toString() {
        String modifier = MODIFIER == null ? "null" : MODIFIER;
        if (STATIC) {
            if (modifier.equals("null"))
                modifier = "static";
            else
                modifier += " static";
        }
        StringBuilder str = new StringBuilder();
        str.append("{ MODIFIER : \"").append(modifier).append("\", ");
        str.append("TYPE : \"").append(TYPE).append("\", ");
        str.append("NAME : \"").append(NAME).append("\" }");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JavaScriptField))
            return false;
        JavaScriptField f = (JavaScriptField) obj;
        return (NAME.equals(f.NAME) && TYPE.equals(f.TYPE));
    }
}
