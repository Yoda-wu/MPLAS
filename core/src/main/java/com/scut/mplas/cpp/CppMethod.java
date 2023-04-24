package com.scut.mplas.cpp;

import java.util.Arrays;
import java.util.Objects;

/**
 *  A simple structure for storing key information about a Cpp Function.
 */
public class CppMethod {
    public final String NAME;
    public final String NAMESPACE;
    public final String SPECIFIER;
    public final String RET_TYPE;
    public final int LINE_OF_CODE;
    public final String[] ARG_TYPES;

    public CppMethod(String specifier,String name,String namespace,String retType,String[] argTypes,int line)
    {
        SPECIFIER=specifier;
        NAME=name;
        NAMESPACE=namespace;
        RET_TYPE=retType;
        ARG_TYPES=argTypes;
        LINE_OF_CODE=line;
    }

    @Override
    public String toString() {
        String specifier = SPECIFIER == "" ? "null" : SPECIFIER;
        String retType = RET_TYPE == "" ? "null" : RET_TYPE;
        String args = ARG_TYPES == null ? "null" : Arrays.toString(ARG_TYPES);
        StringBuilder str = new StringBuilder();
        str.append("{ SPECIFIER : \"").append(specifier).append("\", ");
        str.append("TYPE : \"").append(retType).append("\", ");
        str.append("NAME : \"").append(NAME).append("\", ");
        str.append("NAMESPACE : \"").append(NAMESPACE).append("\", ");
        str.append("ARGS : ").append(args).append(", ");
        str.append("LINE : \"").append(LINE_OF_CODE).append("\" }");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CppMethod))
            return false;
        CppMethod m = (CppMethod) obj;
        return (NAMESPACE.equals(m.NAMESPACE) && NAME.equals(m.NAME) && RET_TYPE.equals(m.RET_TYPE) &&
                LINE_OF_CODE == m.LINE_OF_CODE && Arrays.equals(ARG_TYPES, m.ARG_TYPES));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.NAME);
        hash = 31 * hash + Objects.hashCode(this.NAMESPACE);
        hash = 31 * hash + Objects.hashCode(this.RET_TYPE);
        hash = 31 * hash + this.LINE_OF_CODE;
        hash = 31 * hash + Arrays.deepHashCode(this.ARG_TYPES);
        return hash;
    }
}
