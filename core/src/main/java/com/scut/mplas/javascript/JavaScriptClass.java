package com.scut.mplas.javascript;

import com.scut.mplas.java.JavaClass;
import com.scut.mplas.java.JavaField;
import com.scut.mplas.java.JavaMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class JavaScriptClass {

    public final String NAME;
    public final String FILE;
    public final String PACKAGE;
    public final String EXTENDS;
    public final String[] IMPORTS;
    private String typeParameters;
    private String[] implementations;
    private ArrayList<JavaScriptField> fields;
    private ArrayList<JavaScriptMethod> methods;

    public JavaScriptClass(String name, String pkg, String extend, String filePath, String[] imports) {
        NAME = name;
        PACKAGE = pkg;
        FILE = filePath;
        EXTENDS = extend;
        IMPORTS = imports;
        implementations = null;
        fields = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public void setTypeParameters(String params){
        this.typeParameters = params;
    }

    public String getTypeParameters(){
        return this.typeParameters;
    }

    public void addField(JavaScriptField field) {
        fields.add(field);
    }

    public boolean hasField(String name) {
        for (JavaScriptField fld: fields)
            if (fld.NAME.equals(name))
                return true;
        return false;
    }

    public JavaScriptField[] getAllFields() {
        return fields.toArray(new JavaScriptField[fields.size()]);
    }

    public void addMethod(JavaScriptMethod mtd) {
        methods.add(mtd);
    }

    public boolean hasMethod(String name) {
        for (JavaScriptMethod mtd: methods)
            if (mtd.NAME.equals(name))
                return true;
        return false;
    }

    public JavaScriptMethod[] getAllMethods() {
        return methods.toArray(new JavaScriptMethod[methods.size()]);
    }

    public void setInterfaces(String[] intfs) {
        implementations = intfs;
    }

    public String[] getInterfaces() {
        return implementations;
    }

    @Override
    public String toString() {
        String extend = EXTENDS == null ? "null" : EXTENDS;
        StringBuilder str = new StringBuilder("CLASS \n{\n");
        str.append("  NAME : \"").append(NAME).append("\",\n");
        str.append("  PACKAGE : \"").append(PACKAGE).append("\",\n");
        str.append("  EXTENDS : \"").append(extend).append("\",\n");
        str.append("  IMPLEMENTS : ").append(Arrays.toString(implementations)).append(",\n");
        str.append("  FILE : \"").append(FILE).append("\",\n");
        str.append("  FIELDS : \n  [\n");
        for (JavaScriptField fld: fields)
            str.append("    ").append(fld).append(",\n");
        str.append("  ],\n");
        str.append("  METHODS : \n  [\n");
        for (JavaScriptMethod mtd: methods)
            str.append("    ").append(mtd).append(",\n");
        str.append("  ],\n");
        str.append("  IMPORTS : \n  [\n");
        for (String imprt: IMPORTS)
            str.append("    \"").append(imprt).append("\",\n");
        str.append("  ]\n");
        str.append("}");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JavaScriptClass))
            return false;
        JavaScriptClass cls = (JavaScriptClass) obj;
        return (this.NAME.equals(cls.NAME) && this.PACKAGE.equals(cls.PACKAGE));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.NAME);
        hash = 11 * hash + Objects.hashCode(this.PACKAGE);
        return hash;
    }
}
