package com.smibii.flashables.commands.arguments;

import com.google.gson.JsonObject;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class LightModeArgumentInfo implements ArgumentTypeInfo<LightModeArgument, LightModeArgumentInfo.Template> {
    @Override
    public void serializeToNetwork(Template template, FriendlyByteBuf buffer) {
        // No extra data to serialize
    }

    @Override
    public Template deserializeFromNetwork(FriendlyByteBuf buffer) {
        return new Template();
    }

    @Override
    public void serializeToJson(Template pTemplate, JsonObject pJson) {

    }

    @Override
    public Template unpack(LightModeArgument argument) {
        return new Template();
    }

    public class Template implements ArgumentTypeInfo.Template<LightModeArgument> {
        @Override
        public LightModeArgument instantiate(CommandBuildContext pContext) {
            return new LightModeArgument();
        }

        @Override
        public ArgumentTypeInfo<LightModeArgument, ?> type() {
            return LightModeArgumentInfo.this;
        }
    }
}
