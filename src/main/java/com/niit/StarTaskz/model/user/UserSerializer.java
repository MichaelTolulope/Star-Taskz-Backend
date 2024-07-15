package com.niit.StarTaskz.model.user;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeFieldName("id");
        jsonGenerator.writeObject(user.getId());
        jsonGenerator.writeFieldName("firstName");
        jsonGenerator.writeObject(user.getFirstName());
        jsonGenerator.writeFieldName("lastName");
        jsonGenerator.writeObject(user.getLastName());
        jsonGenerator.writeFieldName("email");
        jsonGenerator.writeObject(user.getEmail());
        jsonGenerator.writeFieldName("workSpaces");
        jsonGenerator.writeObject(user.getWorkSpaces());
        jsonGenerator.writeFieldName("dateOfBirth");
        jsonGenerator.writeObject(user.getDateOfBirth());
        jsonGenerator.writeFieldName("userTasks");
        jsonGenerator.writeObject(user.getUserTasks());
    }
}
