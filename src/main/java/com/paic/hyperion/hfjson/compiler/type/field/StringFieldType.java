package com.paic.hyperion.hfjson.compiler.type.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class StringFieldType extends FieldType {

    @Override
    public TypeName getTypeName() {
        return ClassName.get(String.class);
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return ClassName.get(String.class);
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        setter = replaceLastLiteral(setter, "$L.getValueAsString(null)");
        builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, String getter, boolean writeFieldNameForObject) {
        if (writeFieldNameForObject) {
            builder.addStatement("$L.writeStringField($S, $L)", JSON_GENERATOR_VARIABLE_NAME, fieldName, getter);
        } else {
            builder.addStatement("$L.writeString($L)", JSON_GENERATOR_VARIABLE_NAME, getter);
        }
    }
}
