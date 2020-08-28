
#include <WiFi.h>
#include <FirebaseESP32.h>
#include <LiquidCrystal_I2C.h>
#include <Wire.h> 

#define USE_ARDUINO_INTERRUPTS true    // Set-up low-level interrupts for most acurate BPM math.
#define FIREBASE_HOST "dissertationkae.firebaseio.com/"
#define FIREBASE_AUTH "3T0S4CSX4wI3pn1aA3rcmtchvKUdxmY4jJqW7N1k"
#define WIFI_SSID "LadyKae"
#define WIFI_PASSWORD "kaekaekae"

//variables
const  int buttonPin = 15; //Button WIRE connected to GPIO PIN 15
int PulseWire = 39; // Pulse Sensor PURPLE WIRE connected to ANALOG PIN 0
int buttonState = 0; // Set a current state of the button
// holds the incoming raw data. Signal value can range from 0-1024
int Threshold = 550;  // Determine which Signal to "count as a beat", and which to ingore.
long time_now = 0;
float sensor_val;
unsigned long delayStart = 0; // the time the delay started
bool delayRunning = false; // true if still waiting for delay to finish

//Define FirebaseESP32 data object
FirebaseData firebaseData;
FirebaseJson json;

LiquidCrystal_I2C lcd(0x27,20,4);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
 while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(20);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  lcd.init();                      // initialize the lcd 
  lcd.init();
  pinMode(buttonPin, INPUT);      // initialize the button pin as a input:

     lcd.backlight();
     lcd.setCursor(8,1);
     lcd.print("Push");
     lcd.setCursor(7,2);
     lcd.print("Button");
}

void loop() {
  // put your main code here, to run repeatedly:
  String path = "/ECG_data";
  int i =0;
  volatile unsigned long time_t =0;
  
  buttonState = digitalRead(buttonPin); // read the pushbutton input pin:

  if(buttonState==HIGH){

     lcd.backlight();
     lcd.setCursor(8,1);
     lcd.print("Data");
     lcd.setCursor(4,2);
     lcd.print("is collecting");
     
      for(int i=0;i<100;i++){
        sensor_val = analogRead(PulseWire)*(3.3/4096.0);
        Serial.println(sensor_val);
        Firebase.setFloat(firebaseData, path + "/"+ (i + 1) +"/x_value",time_t=millis());
        Firebase.setFloat(firebaseData, path + "/"+ (i + 1) +"/Reading_val",sensor_val);
        delay(10);
    
        time_now++;
             
              if(time_now==100){ 
                   lcd.backlight();
                   lcd.setCursor(8,1);
                   lcd.print("Data");
                   lcd.setCursor(4,2);
                   lcd.print("was collected");     
                   Serial.println(".......");
                   buttonState = LOW;
                  
               
              }
      }
     lcd.clear();
     lcd.backlight();
     lcd.setCursor(8,1);
     lcd.print("Push");
     lcd.setCursor(7,2);
     lcd.print("Button");
   }
 
 
  
}
