package com.scut.mplas.cpp;

/**
 * A simple structure for storing key information about a Cpp field.
 */
public class CppField {
    public final String NAME;
    public final String TYPE;
    public final String SPECIFIER;

    public CppField(String specifier,String type,String name)
    {
        NAME=name;
        TYPE=type;
        SPECIFIER=specifier==null?"":specifier;
    }

    @Override
    public String toString() {
        String specifier = SPECIFIER == "" ? "null" : SPECIFIER;
        StringBuilder str = new StringBuilder();
        str.append("{ SPECIFIER : \"").append(specifier).append("\", ");
        str.append("TYPE : \"").append(TYPE).append("\", ");
        str.append("NAME : \"").append(NAME).append("\" }");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CppField))
            return false;
        CppField f = (CppField) obj;
        return (NAME.equals(f.NAME) && TYPE.equals(f.TYPE));
    }
}
