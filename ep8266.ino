
#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>

// Hardcode WiFi parameters as this isn't going to be moving around.
const char* ssid = "IOTdeviceAP";
const char* password = "SecretPassword";

// Start a TCP Server on port 5045
WiFiServer server(9999);
WiFiClient client;

void setup() {
  Serial.begin(115200);
  
  
  //Wait for connection
   Serial.print("Setting soft-AP ... ");
   WiFi.mode(WIFI_AP);
  boolean result = WiFi.softAP(ssid, password);
  if(result == true)
  {
    Serial.println("Ready");
  }
  else
  {
    Serial.println("Failed!");
  }

  
  // Start the TCP serveri
  server.begin();

  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);
}

void loop() {
  // listen for incoming clients
  
  client = server.available();
  if (client){
    
    Serial.println("Client connected");
    while (client.connected()){
      if (client.available())
      {
        // Read the incoming TCP command
        String command = client.readStringUntil('/');
        // Debugging display command
        Serial.println(command);
        //client.print(command);
      }
    }
  }
}


