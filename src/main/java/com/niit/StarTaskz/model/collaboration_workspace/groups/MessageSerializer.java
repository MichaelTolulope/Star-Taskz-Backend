package com.niit.StarTaskz.model.collaboration_workspace.groups;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MessageSerializer extends JsonSerializer<Message> {
    @Override
    public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("id");
        jsonGenerator.writeObject(message.getId());
        jsonGenerator.writeFieldName("senderId");
        jsonGenerator.writeObject(message.getSenderId());
        jsonGenerator.writeFieldName("messageContent");
        jsonGenerator.writeObject(message.getMessageContent());
        jsonGenerator.writeFieldName("readBy");
        jsonGenerator.writeObject(message.getReadBy());
        jsonGenerator.writeFieldName("dateTime");
        jsonGenerator.writeObject(message.getMessageDateTime());

        jsonGenerator.writeEndObject();
    }
}
