package com.niit.StarTaskz.model.collaboration_workspace.groups;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GroupSerializer extends JsonSerializer<UserGroup> {
    @Override
    public void serialize(UserGroup group, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("id");
        jsonGenerator.writeObject(group.getId());
        jsonGenerator.writeFieldName("workSpace");
        jsonGenerator.writeObject(group.getWorkSpace());
        jsonGenerator.writeFieldName("groupName");
        jsonGenerator.writeObject(group.getGroupName());
        jsonGenerator.writeFieldName("members");
        jsonGenerator.writeObject(group.getMembers());
        jsonGenerator.writeFieldName("messages");
        jsonGenerator.writeObject(group.getMessages());

        jsonGenerator.writeEndObject();
    }

}
