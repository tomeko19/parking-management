package com.iot.parking_management.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    private static final String MQTT_BROKER = "tcp://localhost:1883";

    private static final String CLIENT_ID = "parking_management_client";

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(MQTT_BROKER, CLIENT_ID, new MemoryPersistence());
        mqttClient.connect();
        return mqttClient;
    }
}
