package com.iot.parking_management.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MqttService {
    @Autowired
    private MqttClient mqttClient;

    public void publish(String topic, String payload) throws Exception{
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(2);
        mqttClient.publish(topic,message);

    }
}
