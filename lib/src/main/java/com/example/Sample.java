package com.example;        // PackageElement

import java.util.List;

public class Sample         // TypeElement
        <T extends List> {  // TypeParameterElement

    private int num;        // VariableElement
    String name;            // VariableElement

    public Sample() {}      // ExecuteableElement

    public void setName(    // ExecuteableElement
            String name     // VariableElement
            ) {}
}
