package com.paic.hyperion.hfjson.compiler;

import com.squareup.javapoet.TypeName;

import java.util.Map;
import java.util.TreeMap;

import static com.paic.hyperion.hfjson.annotation.HFJsonObject.FieldDetectionPolicy;
import static com.paic.hyperion.hfjson.annotation.HFJsonObject.FieldNamingPolicy;

public class JsonObjectHolder {

    public final String packageName;
    public final String injectedClassName;
    public final TypeName objectTypeName;
    public final boolean isAbstractClass;
    public final TypeName parentInjectedTypeName;
    public final FieldDetectionPolicy fieldDetectionPolicy;
    public final FieldNamingPolicy fieldNamingPolicy;
    public String onCompleteCallback;
    public String preSerializeCallback;

    // Using a TreeMap now to keep the entries sorted. This ensures that code is
    // always written the exact same way, no matter which JDK you're using.
    public final Map<String, JsonFieldHolder> fieldMap = new TreeMap<>();
    public boolean fileCreated;

    public JsonObjectHolder(String packageName, String injectedClassName, TypeName objectTypeName, boolean isAbstractClass, TypeName parentInjectedTypeName, FieldDetectionPolicy fieldDetectionPolicy, FieldNamingPolicy fieldNamingPolicy) {
        this.packageName = packageName;
        this.injectedClassName = injectedClassName;
        this.objectTypeName = objectTypeName;
        this.isAbstractClass = isAbstractClass;
        this.parentInjectedTypeName = parentInjectedTypeName;
        this.fieldDetectionPolicy = fieldDetectionPolicy;
        this.fieldNamingPolicy = fieldNamingPolicy;
    }
}
