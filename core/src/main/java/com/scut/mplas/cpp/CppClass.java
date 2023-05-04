package com.scut.mplas.cpp;


import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple structure for storing key information about a Cpp class declaration.
 */
public class CppClass {
    public final String NAME;
    public final String NAMESPACE;
    private ArrayList<CppField> fields;
    private ArrayList<CppMethod> methods;


    public CppClass(String name, String namespace) {
        NAME = name;
        NAMESPACE = namespace;
        fields = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public void addField(CppField field) {
        fields.add(field);
    }

    public boolean hasField(String name) {
        for (CppField fld : fields)
            if (fld.NAME.equals(name))
                return true;
        return false;
    }

    public CppField[] getAllFields() {
        return fields.toArray(new CppField[fields.size()]);
    }

    public void addMethod(CppMethod mtd) {
        methods.add(mtd);
    }

    public boolean hasMethod(String name) {
        for (CppMethod mtd : methods)
            if (mtd.NAME.equals(name))
                return true;
        return false;
    }

    public CppMethod[] getAllMethods() {
        return methods.toArray(new CppMethod[methods.size()]);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("CLASS \n{\n");
        str.append("  NAME : \"").append(NAME).append("\",\n");
        str.append("  NAMESPACE : \"").append(NAMESPACE).append("\",\n");
        str.append("  FIELDS : \n  [\n");
        for (CppField fld : fields)
            str.append("    ").append(fld).append(",\n");
        str.append("  ],\n");
        str.append("  METHODS : \n  [\n");
        for (CppMethod mtd : methods)
            str.append("    ").append(mtd).append(",\n");
        str.append("  ]\n");
        str.append("}");
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CppClass))
            return false;
        CppClass cls = (CppClass) obj;
        return (this.NAME.equals(cls.NAME) && this.NAMESPACE.equals(cls.NAMESPACE));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.NAME);
        hash = 11 * hash + Objects.hashCode(this.NAMESPACE);
        return hash;
    }

}
